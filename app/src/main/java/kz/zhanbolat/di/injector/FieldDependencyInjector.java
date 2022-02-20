package kz.zhanbolat.di.injector;

import kz.zhanbolat.di.exception.InjectionException;
import kz.zhanbolat.di.injector.util.DependencyInjectionUtil;
import kz.zhanbolat.di.injector.util.DependencyInjectionUtilImpl;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldDependencyInjector implements DependencyInjector {
    private DependencyInjectionUtil dependencyInjectionUtil;

    public FieldDependencyInjector() {
        dependencyInjectionUtil = new DependencyInjectionUtilImpl();
    }

    public FieldDependencyInjector(DependencyInjectionUtil dependencyInjectionUtil) {
        this.dependencyInjectionUtil = dependencyInjectionUtil;
    }

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
            if (dependencyInjectionUtil.isDependencyClassMatch(field.getType(), dependency.getClass())) {
                field.setAccessible(true);
                field.set(object, dependency);
            }
        } catch (IllegalAccessException e) {
            throw new InjectionException(e.getMessage(), e);
        }
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
