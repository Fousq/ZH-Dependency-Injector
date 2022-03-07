package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.exception.BeanInitializationException;
import kz.zhanbolat.di.exception.InjectionException;
import kz.zhanbolat.di.injector.DependencyInjector;
import kz.zhanbolat.di.injector.FieldDependencyInjector;
import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.ClassBeanDescription;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class ClassBeanBuilder implements BeanBuilder {

    private DependencyInjector fieldDependencyInjector;

    public ClassBeanBuilder() {
        fieldDependencyInjector = new FieldDependencyInjector();
    }

    @Override
    public Object buildBean(BeanDescription beanDescription, Map<String, Object> injectBeans) {
        final ClassBeanDescription classBeanDescription = (ClassBeanDescription) beanDescription;
        Constructor<?> beanConstructor = classBeanDescription.getBeanConstructor();
        if (Objects.isNull(beanConstructor)) {
            throw new IllegalArgumentException("The constructor of bean is null.");
        }
        try {
            if (beanConstructor.getParameterCount() > 0) {
                Object[] beans = Arrays.stream(beanConstructor.getParameters())
                        .map(parameter -> injectBeans.get(parameter.getName()))
                        .toArray(Object[]::new);
                return beanConstructor.newInstance(beans);
            } else {
                Object bean = beanConstructor.newInstance();
                if (!injectBeans.isEmpty()) {
                    injectBeans.forEach((dependencyName, dependency) -> {
                        try {
                            fieldDependencyInjector.injectDependency(dependencyName, bean, dependency);
                        } catch (InjectionException e) {
                            throw new BeanInitializationException(e.getMessage(), e);
                        }
                    });
                }
                return bean;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
    }
}
