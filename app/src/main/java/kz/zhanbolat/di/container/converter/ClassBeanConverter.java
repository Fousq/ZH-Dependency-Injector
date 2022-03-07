package kz.zhanbolat.di.container.converter;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;
import kz.zhanbolat.di.container.converter.dependency.DependencyConverter;
import kz.zhanbolat.di.container.converter.dependency.ParameterDependencyConverter;
import kz.zhanbolat.di.container.validator.BeanDefinitionValidator;
import kz.zhanbolat.di.container.validator.ClassBeanDefinitionValidator;
import kz.zhanbolat.di.exception.BeanInitializationException;
import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.ClassBeanDescription;
import kz.zhanbolat.di.type.description.DependencyDescription;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassBeanConverter implements BeanConverter<Class<?>> {
    private BeanDefinitionValidator<Class<?>> classBeanDefinitionValidator;
    private DependencyConverter<Parameter> parameterDependencyConverter;

    public ClassBeanConverter() {
        classBeanDefinitionValidator = new ClassBeanDefinitionValidator();
        parameterDependencyConverter = new ParameterDependencyConverter();
    }

    @Override
    public BeanDescription convert(Class<?> beanDefinition) {
        classBeanDefinitionValidator.validate(beanDefinition);
        final ClassBeanDescription beanDescription = new ClassBeanDescription();
        Bean beanAnnotation = beanDefinition.getAnnotation(Bean.class);
        beanDescription.setBeanName(beanAnnotation.name());
        beanDescription.setBeanClass(beanDefinition);
        Optional<Constructor<?>> beanConstructor = Arrays.stream(beanDefinition.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(BeanConstructor.class))
                .findFirst();
        Constructor<?> constructor;
        if (beanConstructor.isEmpty()) {
            try {
                constructor = beanDefinition.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new BeanInitializationException("There is no default constructor for class " + beanDefinition.getSimpleName());
            }
        } else {
            constructor = beanConstructor.get();
            Parameter[] parameters = constructor.getParameters();
            if (parameters.length > 0) {
                List<DependencyDescription> dependencies = Arrays.stream(parameters)
                        .map(parameterDependencyConverter::convert)
                        .collect(Collectors.toList());
                beanDescription.setDependencies(dependencies);
            }
        }
        beanDescription.setBeanConstructor(constructor);
        return beanDescription;
    }
}
