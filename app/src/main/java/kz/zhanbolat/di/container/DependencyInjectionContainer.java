package kz.zhanbolat.di.container;

public interface DependencyInjectionContainer {
    Object getBean(String beanName);
    <T> T getBean(String beanName, Class<T> beanClass);
    <T> T getBean(Class<T> beanClass);
}
