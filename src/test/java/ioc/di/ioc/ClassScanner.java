package ioc.di.ioc;

import ioc.di.annotation.Autowired;
import ioc.di.annotation.Repository;
import ioc.di.annotation.Service;
import org.reflections.Reflections;

import java.lang.ref.Reference;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {

    public static Set<Class<?>> getAllClassesInPackage(final String packagePrefix) {
        Set<Class<?>> classes = new HashSet<>();
        Reflections reflections = new Reflections(packagePrefix);

        final Set<Class<?>> repositoryClasses = reflections.getTypesAnnotatedWith(Repository.class);
        final Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);

        classes.addAll(repositoryClasses);
        classes.addAll(serviceClasses);

        return classes;
    }
}
