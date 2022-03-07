package kz.zhanbolat.di.type.description;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class ClassBeanDescription extends BeanDescription {
    private Constructor<?> beanConstructor;

    public Constructor<?> getBeanConstructor() {
        return beanConstructor;
    }

    public void setBeanConstructor(Constructor<?> beanConstructor) {
        this.beanConstructor = beanConstructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassBeanDescription that = (ClassBeanDescription) o;
        return Objects.equals(beanConstructor, that.beanConstructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), beanConstructor);
    }
}
