package kz.zhanbolat.di.container.converter.dependency;

import kz.zhanbolat.di.type.description.DependencyDescription;

public interface DependencyConverter<T> {
    DependencyDescription convert(T dependencyDefinition);
}
