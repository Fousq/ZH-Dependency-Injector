package kz.zhanbolat.di.injector;

import kz.zhanbolat.di.exception.InjectionException;

public interface FieldAnnotationInjector {
    void injectDependency(String dependencyName, Object object, Object dependency) throws InjectionException;
}
