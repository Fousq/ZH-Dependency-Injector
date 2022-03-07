package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.ClassBeanDescription;
import kz.zhanbolat.di.type.description.MethodBeanDescription;

public class BeanBuilderFactoryImpl implements BeanBuilderFactory {

    @Override
    public BeanBuilder getBeanBuilder(BeanDescription beanDescription) {
        if (beanDescription instanceof MethodBeanDescription) {
            return new MethodBeanBuilder();
        } else if (beanDescription instanceof ClassBeanDescription) {
            return new ClassBeanBuilder();
        }
        throw new IllegalArgumentException("There isn't any builder for bean description " + beanDescription.getClass());
    }
}
