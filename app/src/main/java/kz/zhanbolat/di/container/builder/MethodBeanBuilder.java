package kz.zhanbolat.di.container.builder;

import kz.zhanbolat.di.exception.BeanInitializationException;
import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.MethodBeanDescription;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class MethodBeanBuilder implements BeanBuilder {

    @Override
    public Object buildBean(BeanDescription beanDescription, Map<String, Object> injectBeans) {
        final MethodBeanDescription methodBeanDescription = (MethodBeanDescription) beanDescription;
        Method method = methodBeanDescription.getMethod();
        Object invoker = methodBeanDescription.getInvoker();
        if (Objects.isNull(method)) {
            throw new IllegalArgumentException("The method for bean building is null");
        }
        if (Objects.isNull(invoker)) {
            throw new IllegalArgumentException("The invoker for bean building is null");
        }
        try {
            if (Objects.isNull(injectBeans) || injectBeans.isEmpty()) {
                return method.invoke(invoker);
            } else {
                Object[] beans = Arrays.stream(method.getParameters())
                        .map(parameter -> injectBeans.get(parameter.getName()))
                        .toArray(Object[]::new);
                return method.invoke(invoker, beans);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInitializationException(e.getMessage(), e);
        }
    }
}
