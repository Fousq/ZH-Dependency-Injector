package kz.zhanbolat.di.type.description;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodBeanDescription extends BeanDescription {
    private Method method;
    private Object invoker;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getInvoker() {
        return invoker;
    }

    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MethodBeanDescription that = (MethodBeanDescription) o;
        return Objects.equals(method, that.method) && Objects.equals(invoker, that.invoker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), method, invoker);
    }
}
