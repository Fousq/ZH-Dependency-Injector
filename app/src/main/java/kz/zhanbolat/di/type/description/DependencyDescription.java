package kz.zhanbolat.di.type.description;

public class DependencyDescription {
    private Class<?> dependencyClass;
    private String beanName;
    private String fieldName;

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
