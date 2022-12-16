package ioc.di.ioc;

import ioc.di.annotation.Autowired;
import ioc.di.annotation.Repository;
import ioc.di.annotation.Service;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public class ClassScanner {

    public static Set<Class<?>> getAllClassesInPackage(final String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        Reflections reflections = new Reflections(packageName);

        final Set<Class<?>> repositoryClasses = reflections.getTypesAnnotatedWith(Repository.class);
        final Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
        final Set<Class<?>> injectClasses = reflections.getTypesAnnotatedWith(Autowired.class);

        classes.addAll(repositoryClasses);
        classes.addAll(serviceClasses);
        classes.addAll(injectClasses);

        return classes;
    }
}
