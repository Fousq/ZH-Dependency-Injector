package kz.zhanbolat.di.container.converter;

import java.lang.reflect.Method;
import java.util.Map;

public class BeanConverterFactoryImpl implements BeanConverterFactory {

    private static final Map<Class<?>, BeanConverter<?>> BEAN_CONVERTER_MAP =
            Map.of(Method.class, new MethodBeanConverter(), Class.class, new ClassBeanConverter());

    @Override
    public <T> BeanConverter<T> getConverter(Class<T> converterClass) {
        if (!BEAN_CONVERTER_MAP.containsKey(converterClass)) {
            throw new IllegalArgumentException("");
        }
        return (BeanConverter<T>) BEAN_CONVERTER_MAP.get(converterClass);
    }
}
