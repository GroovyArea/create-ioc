package ioc.di.ioc;

import ioc.di.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class IOCContainer {

    private final Set<Object> beans;

    public IOCContainer(final Set<Class<?>> classes) {
        this.beans = createBeans(classes);
        this.beans.forEach(this::setFields);
    }

    public static IOCContainer createContainerForPackage(final String rootPackageName) {
        final Set<Class<?>> allClassesInPackage = ClassScanner.getAllClassesInPackage(rootPackageName);
        return new IOCContainer(allClassesInPackage);
    }

    private Set<Object> createBeans(final Set<Class<?>> classes) {
        Set<Object> beans = new HashSet<>();

        for (Class<?> clazz : classes) {
            beans.add(createInstance(clazz));
        }

        return beans;
    }

    private static Object createInstance(final Class<?> clazz) {
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException();
        }
    }

    private void setFields(final Object bean) {
        final Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            setBeanField(bean, field);
        }
    }

    private void setBeanField(final Object bean, final Field field) {
        try {
            injectField(bean, field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void injectField(final Object bean, final Field field) throws IllegalAccessException {
        field.setAccessible(true);

        if (hasInjectAnnotation(field)) {
            field.set(bean, getBean(field.getType()));
        }
    }

    private boolean hasInjectAnnotation(final Field field) {
        return field.isAnnotationPresent(Autowired.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<T> aClass) {
        return (T) beans.stream()
                .filter(bean -> aClass.isAssignableFrom(bean.getClass()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
