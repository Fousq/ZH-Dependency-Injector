package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.exception.BeanInitializationException;
import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.ClassBeanDescription;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;


public class ClassBeanBuilder implements BeanBuilder {

    @Override
    public Object buildBean(BeanDescription beanDescription, Object... injectBeans) {
        final ClassBeanDescription classBeanDescription = (ClassBeanDescription) beanDescription;
        Constructor<?> beanConstructor = classBeanDescription.getBeanConstructor();
        if (Objects.isNull(beanConstructor)) {
            throw new IllegalArgumentException("The constructor of bean is null.");
        }
        try {
            return injectBeans.length == 0 ? beanConstructor.newInstance() : beanConstructor.newInstance(injectBeans);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
    }
}
