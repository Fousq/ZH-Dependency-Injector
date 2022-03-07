package kz.zhanbolat.di.container.converter;

import kz.zhanbolat.di.type.description.BeanDescription;

public interface BeanConverter<T> {
    BeanDescription convert(T beanDefinition);
}
