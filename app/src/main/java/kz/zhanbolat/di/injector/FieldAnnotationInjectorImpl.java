package kz.zhanbolat.di.injector;

import kz.zhanbolat.di.exception.InjectionException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class FieldAnnotationInjectorImpl implements FieldAnnotationInjector {
    @Override
    public void injectDependency(String dependencyName, Object object, Object dependency) throws InjectionException {
        if (Objects.isNull(object) || Objects.isNull(dependency)) {
            throw new IllegalArgumentException("Objects cannot be null");
        }
        if (Objects.isNull(dependencyName) || dependencyName.isEmpty()) {
            throw new IllegalArgumentException("Empty dependency name");
        }
        Class<?> injectedClass = object.getClass();
        Field field = getField(injectedClass, dependencyName);
        if (Objects.isNull(field)) {
            throw new InjectionException("No dependency " + dependencyName + " exists in " + injectedClass.getName());
        }
        try {
            if (field.getType().equals(dependency.getClass()) ||
                    isInterfacesMatch(dependency.getClass().getInterfaces(), field.getType()) ||
                    isSuperclassMatch(field.getType(), dependency.getClass())) {
                field.setAccessible(true);
                field.set(object, dependency);
            }
        } catch (IllegalAccessException e) {
            throw new InjectionException(e.getMessage(), e);
        }
    }

    private boolean isInterfacesMatch(Class<?>[] interfaces, Class<?> fieldInterface) {
        if (Objects.isNull(interfaces) || interfaces.length == 0) {
            return false;
        }
        return Arrays.asList(interfaces).contains(fieldInterface);
    }

    private boolean isSuperclassMatch(Class<?> fieldClass, Class<?> dependencyClass) {
        Class<?> dependencyClassSuperclass = dependencyClass.getSuperclass();
        while (Objects.nonNull(dependencyClassSuperclass)) {
            if (fieldClass.equals(dependencyClassSuperclass)) {
                return true;
            } else {
                dependencyClassSuperclass = dependencyClassSuperclass.getSuperclass();
            }
        }
        return false;
    }

    private Field getField(Class<?> injectClass, String dependencyName) {
        try {
            return injectClass.getDeclaredField(dependencyName);
        } catch (NoSuchFieldException e) {
            Class<?> injectClassSuperclass = injectClass.getSuperclass();
            while (Objects.nonNull(injectClassSuperclass)) {
                try {
                    return injectClassSuperclass.getDeclaredField(dependencyName);
                } catch (NoSuchFieldException ex) {
                    injectClassSuperclass = injectClassSuperclass.getSuperclass();
                }
            }
        }
        return null;
    }
}
