package kz.zhanbolat.di.container.validator;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.exception.BeanInitializationException;

import java.lang.reflect.Method;

public class MethodBeanDefinitionValidator implements BeanDefinitionValidator<Method> {

    @Override
    public void validate(Method beanDefinition) {
        Bean beanAnnotation = beanDefinition.getAnnotation(Bean.class);
        if (beanAnnotation.name().isEmpty()) {
            throw new BeanInitializationException("The bean name is empty");
        }
        Class<?> returnType = beanDefinition.getReturnType();
        if (returnType.equals(Void.class)) {
            throw new BeanInitializationException("The bean method should return the class");
        }
    }
}
