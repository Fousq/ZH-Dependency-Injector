package kz.zhanbolat.di.type.description;

public class DependencyDescription {
    private Class<?> dependencyClass;
    private String beanName;

    public Class<?> getDependencyClass() {
        return dependencyClass;
    }

    public void setDependencyClass(Class<?> dependencyClass) {
        this.dependencyClass = dependencyClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
