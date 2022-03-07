package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.type.description.BeanDescription;

public interface BeanBuilderFactory {
    BeanBuilder getBeanBuilder(BeanDescription beanDescription);
}
