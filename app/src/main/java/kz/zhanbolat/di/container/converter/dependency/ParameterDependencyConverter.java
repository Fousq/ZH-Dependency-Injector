package kz.zhanbolat.di.container.converter.dependency;

import kz.zhanbolat.di.annotations.Inject;
import kz.zhanbolat.di.type.description.DependencyDescription;

import java.lang.reflect.Parameter;
import java.util.Objects;

public class ParameterDependencyConverter implements DependencyConverter<Parameter> {
    @Override
    public DependencyDescription convert(Parameter dependencyDefinition) {
        final DependencyDescription dependencyDescription = new DependencyDescription();
        Inject injectAnnotation = dependencyDefinition.getAnnotation(Inject.class);
        if (Objects.nonNull(injectAnnotation) && !injectAnnotation.beanName().isEmpty()) {
            dependencyDescription.setBeanName(injectAnnotation.beanName());
        }
        dependencyDescription.setDependencyClass(dependencyDefinition.getType());
        return dependencyDescription;
    }
}
