package kz.zhanbolat.di.container.converter.dependency;

import kz.zhanbolat.di.annotations.Inject;
import kz.zhanbolat.di.type.description.DependencyDescription;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldDependencyConverter implements DependencyConverter<Field> {

    @Override
    public DependencyDescription convert(Field dependencyDefinition) {
        final DependencyDescription dependencyDescription = new DependencyDescription();
        Inject injectAnnotation = dependencyDefinition.getAnnotation(Inject.class);
        if (Objects.nonNull(injectAnnotation) && !injectAnnotation.beanName().isEmpty()) {
            dependencyDescription.setBeanName(injectAnnotation.beanName());
        }
        dependencyDescription.setDependencyClass(dependencyDefinition.getType());
        dependencyDescription.setFieldName(dependencyDefinition.getName());
        return dependencyDescription;
    }
}
