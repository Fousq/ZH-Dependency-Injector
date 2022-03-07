package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.type.description.BeanDescription;

public interface BeanBuilder {
    Object buildBean(BeanDescription beanDescription, Object... injectBeans);
}
