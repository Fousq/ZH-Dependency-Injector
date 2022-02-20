package kz.zhanbolat.di.injector.util;

import java.util.Arrays;
import java.util.Objects;

public class DependencyInjectionUtilImpl implements DependencyInjectionUtil {
    @Override
    public boolean isDependencyClassMatch(Class<?> injectionClass, Class<?> dependencyClass) {
        return (Objects.nonNull(injectionClass) && Objects.nonNull(dependencyClass))
                && (injectionClass.equals(dependencyClass)
                || isInterfacesMatch(injectionClass, dependencyClass)
                || isSuperclassMatch(injectionClass, dependencyClass));
    }

    private boolean isInterfacesMatch(Class<?> injectionClass, Class<?> dependencyClass) {
        Class<?>[] dependencyClassInterfaces = dependencyClass.getInterfaces();
        if (dependencyClassInterfaces.length == 0) {
            return false;
        }
        return Arrays.asList(dependencyClassInterfaces).contains(injectionClass);
    }

    private boolean isSuperclassMatch(Class<?> injectionClass, Class<?> dependencyClass) {
        Class<?> dependencyClassSuperclass = dependencyClass.getSuperclass();
        while (Objects.nonNull(dependencyClassSuperclass)) {
            if (injectionClass.equals(dependencyClassSuperclass)) {
                return true;
            } else {
                dependencyClassSuperclass = dependencyClassSuperclass.getSuperclass();
            }
        }
        return false;
    }
}
