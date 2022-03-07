package kz.zhanbolat.di.container.converter;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.container.converter.dependency.DependencyConverter;
import kz.zhanbolat.di.container.converter.dependency.ParameterDependencyConverter;
import kz.zhanbolat.di.container.validator.BeanDefinitionValidator;
import kz.zhanbolat.di.container.validator.MethodBeanDefinitionValidator;
import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.DependencyDescription;
import kz.zhanbolat.di.type.description.MethodBeanDescription;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodBeanConverter implements BeanConverter<Method> {
    private BeanDefinitionValidator<Method> methodBeanDefinitionValidator;
    private DependencyConverter<Parameter> parameterDependencyConverter;

    public MethodBeanConverter() {
        methodBeanDefinitionValidator = new MethodBeanDefinitionValidator();
        parameterDependencyConverter = new ParameterDependencyConverter();
    }

    @Override
    public BeanDescription convert(Method beanDefinition) {
        methodBeanDefinitionValidator.validate(beanDefinition);
        MethodBeanDescription beanDescription = new MethodBeanDescription();
        Bean beanAnnotation = beanDefinition.getAnnotation(Bean.class);
        beanDescription.setBeanName(beanAnnotation.name());
        beanDescription.setBeanClass(beanDefinition.getReturnType());
        beanDescription.setMethod(beanDefinition);
        Parameter[] parameters = beanDefinition.getParameters();
        if (parameters.length > 0) {
            List<DependencyDescription> dependencyDescriptionList = Arrays.stream(parameters)
                    .map(parameterDependencyConverter::convert).collect(Collectors.toList());
            beanDescription.setDependencies(dependencyDescriptionList);
        }
        return beanDescription;
    }
}
