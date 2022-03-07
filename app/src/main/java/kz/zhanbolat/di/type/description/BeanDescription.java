package kz.zhanbolat.di.type.description;

import java.util.List;
import java.util.Objects;

public abstract class BeanDescription {
    private String beanName;
    private Class<?> beanClass;
    private List<DependencyDescription> dependencies;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public List<DependencyDescription> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyDescription> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanDescription that = (BeanDescription) o;
        return Objects.equals(beanName, that.beanName) && Objects.equals(beanClass, that.beanClass) && Objects.equals(dependencies, that.dependencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, beanClass, dependencies);
    }
}
