package kz.zhanbolat.di.container;

import kz.zhanbolat.di.exception.BeanNotFoundException;
import kz.zhanbolat.di.exception.MultipleBeanException;

public interface DependencyInjectionContainer {
    Object getBean(String beanName) throws BeanNotFoundException;
    <T> T getBean(String beanName, Class<T> beanClass) throws BeanNotFoundException;
    <T> T getBean(Class<T> beanClass) throws MultipleBeanException;
}
