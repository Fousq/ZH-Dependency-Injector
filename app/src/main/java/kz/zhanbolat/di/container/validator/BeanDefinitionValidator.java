package kz.zhanbolat.di.container.validator;

public interface BeanDefinitionValidator<T> {
    void validate(T beanDefinition);
}
