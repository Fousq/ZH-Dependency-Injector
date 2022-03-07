package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.type.description.BeanDescription;

import java.util.Map;

public interface BeanBuilder {
    Object buildBean(BeanDescription beanDescription, Map<String, Object> injectBeans);
}
