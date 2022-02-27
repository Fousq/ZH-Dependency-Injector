package kz.zhanbolat.di.container;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.Inject;
import kz.zhanbolat.di.exception.BeanInitializationException;
import kz.zhanbolat.di.exception.MultipleBeanException;
import kz.zhanbolat.di.util.PairGeneric;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassConfigurationDependencyInjectionContainer implements DependencyInjectionContainer {
    
    private final Map<PairGeneric<String, Class<?>>, Object> beanMap;
    
    public ClassConfigurationDependencyInjectionContainer(Class<?> configurationClass) {
        beanMap = new ConcurrentHashMap<>();
        initializeBeanMap(configurationClass);
    }
    
    @Override
    public Object getBean(String beanName) {
        return beanMap.entrySet().stream()
                .filter(entry -> entry.getKey().getKey().equals(beanName))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        Object bean = getBean(beanName);
        return Objects.nonNull(bean) ? beanClass.cast(bean) : null;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) throws MultipleBeanException {
        List<Object> beans = beanMap.entrySet().stream()
                .filter(entry -> entry.getKey().getValue().equals(beanClass))
                .map(Map.Entry::getValue).collect(Collectors.toList());
        if (beans.size() > 1) {
            throw new MultipleBeanException("For class " + beanClass + " multiple beans have been found. " +
                    "Please, specify bean name to get the required one.");
        }
        return beans.isEmpty() ? null : beanClass.cast(beans.get(0));
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

    private void initializeTargetedBean(Object configurationInstance, Class<?> beanClass) {
        Class<?> configurationClass = configurationInstance.getClass();
        List<Method> injectBeanInitMethods = Arrays.stream(configurationClass.getMethods())
                .filter(beanMethod -> {
                    Bean beanAnnotation = beanMethod.getAnnotation(Bean.class);
                    return Objects.nonNull(beanAnnotation) && beanMethod.getReturnType().equals(beanClass);
                }).collect(Collectors.toList());
        if (injectBeanInitMethods.isEmpty()) {
            throw new BeanInitializationException("There's no bean init method for class " + beanClass.getName());
        }
        if (injectBeanInitMethods.size() > 1) {
            throw new BeanInitializationException("Cannot inject with several beans match the class " + beanClass.getName() +
                    ". Please, specify the required bean by its name");
        }
        initializeBean(configurationInstance, injectBeanInitMethods.get(0));
    }

    private void initializeBean(Object configurationInstance, Method method) {
        if (Objects.isNull(method.getAnnotation(Bean.class))) {
            throw new IllegalArgumentException("Method " + method.getName() + " is not annotated with annotation @Bean");
        }
        Bean beanAnnotation = method.getAnnotation(Bean.class);
        if (Objects.nonNull(getBean(beanAnnotation.name()))) {
            return;
        }
        try {
            Object bean;
            Parameter[] parameters = method.getParameters();
            if (parameters.length > 0) {
                Object[] injectBeans = Arrays.stream(parameters)
                        .map(parameter -> getInjectBean(configurationInstance, parameter))
                        .toArray();
                bean = method.invoke(configurationInstance, injectBeans);
            } else {
                bean = method.invoke(configurationInstance);
            }
            beanMap.put(new PairGeneric<>(beanAnnotation.name(), method.getReturnType()), bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInitializationException("Bean cannot be initialized", e);
        }
    }

    private Object getInjectBean(Object configurationInstance, Parameter parameter) {
        Inject injectAnnotation = parameter.getAnnotation(Inject.class);
        if (Objects.nonNull(injectAnnotation) && !injectAnnotation.beanName().isEmpty()) {
            String injectBeanName = injectAnnotation.beanName();
            if (Objects.isNull(getBean(injectBeanName))) {
                initializeTargetedBean(configurationInstance, injectBeanName);
            }
            return getBean(injectBeanName);
        } else {
            Class<?> injectClass = parameter.getType();
            try {
                if (Objects.isNull(getBean(injectClass))) {
                    initializeTargetedBean(configurationInstance, injectClass);
                }
                return getBean(injectClass);
            } catch (MultipleBeanException e) {
                throw new BeanInitializationException("Cannot inject with several beans match the class " + injectClass.getName() +
                        ". Please, specify the required bean by its name");
            }
        }
    }
}
