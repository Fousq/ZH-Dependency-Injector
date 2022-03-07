package kz.zhanbolat.di.container.validator;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;
import kz.zhanbolat.di.exception.BeanInitializationException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassBeanDefinitionValidator implements BeanDefinitionValidator<Class<?>> {
    @Override
    public void validate(Class<?> beanDefinition) {
        Bean beanAnnotation = beanDefinition.getAnnotation(Bean.class);
        if (Objects.isNull(beanAnnotation)) {
            throw new BeanInitializationException("There's no annotation @Bean on class " + beanDefinition.getSimpleName());
        }
        if (beanAnnotation.name().isEmpty()) {
            throw new BeanInitializationException("The bean name is empty");
        }
        List<Constructor<?>> beanConstructors = Arrays.stream(beanDefinition.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(BeanConstructor.class))
                .collect(Collectors.toList());
        if (beanConstructors.size() > 1) {
            throw new BeanInitializationException("There are several bean constructors for class " + beanDefinition.getSimpleName());
        }
    }
}
