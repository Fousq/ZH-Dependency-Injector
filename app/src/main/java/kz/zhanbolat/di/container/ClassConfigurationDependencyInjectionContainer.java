package kz.zhanbolat.di.container;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.Inject;
import kz.zhanbolat.di.exception.BeanInitializationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ClassConfigurationDependencyInjectionContainer implements DependencyInjectionContainer {
    
    private final Map<String, Object> beanMap;
    
    public ClassConfigurationDependencyInjectionContainer(Class<?> configurationClass) {
        beanMap = new ConcurrentHashMap<>();
        initializeBeanMap(configurationClass);
    }
    
    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
    
    private void initializeBeanMap(Class<?> configurationClass) {
        BeanConfiguration annotation = configurationClass.getAnnotation(BeanConfiguration.class);
        if (Objects.isNull(annotation)) {
            throw new IllegalArgumentException("Provided class is not a configuration class");
        }
        try {
            Object configurationClassInstance = configurationClass.getConstructor().newInstance();
            Method[] methods = configurationClass.getMethods();
            Stream.of(methods).filter(method -> Objects.nonNull(method.getAnnotation(Bean.class)))
                    .forEach(method -> initializeBean(configurationClassInstance, method));
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            throw new BeanInitializationException("Configuration cannot be initialized", e);
        }
    }

    private void initializeTargetedBean(Object configurationInstance, String beanName) {
        Class<?> configurationClass = configurationInstance.getClass();
        Method injectBeanInitMethod = Arrays.stream(configurationClass.getMethods())
                .filter(beanMethod -> {
                    Bean beanAnnotation = beanMethod.getAnnotation(Bean.class);
                    return Objects.nonNull(beanAnnotation) && Objects.equals(beanAnnotation.name(), beanName);
                })
                .findFirst()
                .orElseThrow(() -> new BeanInitializationException("There's no bean init method for name " + beanName));
        initializeBean(configurationInstance, injectBeanInitMethod);
    }

    private void initializeBean(Object configurationInstance, Method method) {
        if (Objects.isNull(method.getAnnotation(Bean.class))) {
            throw new IllegalArgumentException("Method " + method.getName() + " is not annotated with annotation @Bean");
        }
        Bean beanAnnotation = method.getAnnotation(Bean.class);
        if (beanMap.containsKey(beanAnnotation.name())) {
            return;
        }

        Parameter[] parameters = method.getParameters();
        boolean noParameterInjection = Arrays.stream(parameters)
                .anyMatch(parameter -> Objects.isNull(parameter.getAnnotation(Inject.class)));
        if (noParameterInjection) {
            throw new IllegalArgumentException("Method's parameters are not annotated with annotation @Inject");
        }
        try {
            Object bean;
            if (parameters.length > 0) {
                Object[] injectBeans = Arrays.stream(parameters).map(parameter -> {
                    Inject injectAnnotation = parameter.getAnnotation(Inject.class);
                    if (!beanMap.containsKey(injectAnnotation.beanName())) {
                        initializeTargetedBean(configurationInstance, injectAnnotation.beanName());
                    }
                    return beanMap.get(injectAnnotation.beanName());
                }).toArray();
                bean = method.invoke(configurationInstance, injectBeans);
            } else {
                bean = method.invoke(configurationInstance);
            }
            beanMap.put(beanAnnotation.name(), bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInitializationException("Bean cannot be initialized", e);
        }
    }
}