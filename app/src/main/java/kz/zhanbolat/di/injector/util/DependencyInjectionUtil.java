package kz.zhanbolat.di.injector.util;

public interface DependencyInjectionUtil {
    boolean isDependencyClassMatch(Class<?> injectionClass, Class<?> dependencyClass);
}
