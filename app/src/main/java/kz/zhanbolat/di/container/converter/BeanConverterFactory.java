package kz.zhanbolat.di.container.converter;

public interface BeanConverterFactory {
    <T> BeanConverter<T> getConverter(Class<T> converterClass);
}
