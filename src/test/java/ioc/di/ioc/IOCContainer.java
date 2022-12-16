package ioc.di.ioc;

import ioc.di.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class IOCContainer {

    private final Set<Object> beans;

    public IOCContainer(final Set<Class<?>> classes) {
        this.beans = createBeans(classes);
        this.beans.forEach(this::setFields);
    }

    public static IOCContainer createContainerForPackage(final String rootPackagePrefix) {
        final Set<Class<?>> allClassesInPackage = ClassScanner.getAllClassesInPackage(rootPackagePrefix);
        return new IOCContainer(allClassesInPackage);
    }

    private Set<Object> createBeans(final Set<Class<?>> classes) {
        return classes.stream()
                .map(IOCContainer::createInstance)
                .collect(Collectors.toSet());
    }

    private static Object createInstance(final Class<?> clazz) {
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException();
        }
    }

    private void setFields(final Object bean) {
        final Field[] fields = bean.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .forEach(field -> setBeanField(bean, field));
    }

    private void setBeanField(final Object bean, final Field field) {
        try {
            injectFieldWithBean(bean, field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void injectFieldWithBean(final Object bean, final Field field) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(bean, getBean(field.getType()));
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<T> clazz) {
        return (T) beans.stream()
                .filter(bean -> clazz.isAssignableFrom(bean.getClass()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
