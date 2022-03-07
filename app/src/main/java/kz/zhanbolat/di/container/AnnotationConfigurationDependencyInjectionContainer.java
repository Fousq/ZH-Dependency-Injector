package kz.zhanbolat.di.container;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.BeanImport;
import kz.zhanbolat.di.container.builder.BeanBuilderFactory;
import kz.zhanbolat.di.container.converter.BeanConverterFactory;
import kz.zhanbolat.di.exception.*;
import kz.zhanbolat.di.type.BeanSet;
import kz.zhanbolat.di.type.description.BeanDescription;
import kz.zhanbolat.di.type.description.DependencyDescription;
import kz.zhanbolat.di.type.description.MethodBeanDescription;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationConfigurationDependencyInjectionContainer implements DependencyInjectionContainer {

    private BeanSet beanSet;
    private BeanBuilderFactory beanBuilderFactory;

    public AnnotationConfigurationDependencyInjectionContainer(Class<?> configurationClass,
                                                               BeanConverterFactory beanConverterFactory,
                                                               BeanBuilderFactory beanBuilderFactory) {
        this.beanBuilderFactory = beanBuilderFactory;
        beanSet = new BeanSet();
        BeanConfiguration beanConfiguration = configurationClass.getAnnotation(BeanConfiguration.class);
        if (Objects.isNull(beanConfiguration)) {
            throw new IllegalArgumentException("The configuration class is not annotated with @BeanConfiguration");
        }
        processImportBeans(configurationClass, beanConverterFactory);
        processMethodBeans(configurationClass, beanConverterFactory);
    }

    @Override
    public Object getBean(String beanName) throws BeanNotFoundException {
        BeanDescription beanDescription = beanSet.stream()
                .filter(description -> description.getBeanName().equals(beanName))
                .findFirst()
                .orElseThrow(() -> new BeanNotFoundException("The bean with name " + beanName + " is not found"));
        return buildBean(beanDescription);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) throws BeanNotFoundException {
        BeanDescription beanDescription = beanSet.stream()
                .filter(description -> Objects.equals(description.getBeanName(), beanName) && description.getBeanClass().isAssignableFrom(beanClass))
                .findFirst()
                .orElseThrow(() -> new BeanNotFoundException("The bean with name " + beanName + " is not found"));
        return beanClass.cast(buildBean(beanDescription));
    }

    @Override
    public <T> T getBean(Class<T> beanClass) throws MultipleBeanException {
        List<BeanDescription> beanDescriptionsByClass = this.beanSet.stream()
                .filter(description -> description.getBeanClass().isAssignableFrom(beanClass))
                .collect(Collectors.toList());
        if (beanDescriptionsByClass.size() > 1) {
            throw new MultipleBeanException("There's several beans for class " + beanClass.getSimpleName());
        }
        return beanClass.cast(buildBean(beanDescriptionsByClass.get(0)));
    }

    private Object buildBean(BeanDescription beanDescription) {
        final Map<String, Object> dependencies = new HashMap<>();
        if (Objects.nonNull(beanDescription.getDependencies()) && !beanDescription.getDependencies().isEmpty()) {
            for (DependencyDescription dependencyDescription : beanDescription.getDependencies()) {
                try {
                    String beanName = dependencyDescription.getBeanName();
                    Object bean;
                    if (Objects.nonNull(beanName) && !beanName.isEmpty()) {
                        bean = getBean(beanName, dependencyDescription.getDependencyClass());
                    } else {
                        bean = getBean(dependencyDescription.getDependencyClass());
                    }
                    dependencies.put(dependencyDescription.getFieldName(), bean);
                } catch (BeanNotFoundException | MultipleBeanException e) {
                    throw new BeanInitializationException(e.getMessage(), e);
                }
            }
        }
        return beanBuilderFactory.getBeanBuilder(beanDescription).buildBean(beanDescription, dependencies);
    }

    private void processImportBeans(Class<?> configurationClass, BeanConverterFactory beanConverterFactory) {
        BeanImport beanImport = configurationClass.getAnnotation(BeanImport.class);
        if (Objects.nonNull(beanImport)) {
            boolean beansWithoutBeanAnnotation = Arrays.stream(beanImport.beanClasses())
                    .anyMatch(beanClass -> Objects.isNull(beanClass.getAnnotation(Bean.class)));
            if (beansWithoutBeanAnnotation) {
                throw new BeanImportException("There's a bean class without @Bean");
            }
            Arrays.stream(beanImport.beanClasses())
                    .map(beanClass -> beanConverterFactory.getConverter(Class.class).convert(beanClass))
                    .forEach(beanDescription -> {
                        try {
                            beanSet.addBean(beanDescription);
                        } catch (UniqueBeanNameException e) {
                            throw new IllegalStateException(e.getMessage(), e);
                        }
                    });
        }
    }

    private void processMethodBeans(Class<?> configurationClass, BeanConverterFactory beanConverterFactory) {
        Method[] methods = configurationClass.getMethods();
        List<BeanDescription> methodBeanDescriptions = Arrays.stream(methods)
                .filter(method -> Objects.nonNull(method.getAnnotation(Bean.class)))
                .map(method -> beanConverterFactory.getConverter(Method.class).convert(method))
                .collect(Collectors.toList());
        if (!methodBeanDescriptions.isEmpty()) {
            try {
                Object configurationInstance = configurationClass.getConstructor().newInstance();
                methodBeanDescriptions.stream().map(beanDescription -> {
                    final MethodBeanDescription methodBeanDescription = (MethodBeanDescription) beanDescription;
                    methodBeanDescription.setInvoker(configurationInstance);
                    return methodBeanDescription;
                }).forEach(methodBeanDescription -> {
                    try {
                        beanSet.addBean(methodBeanDescription);
                    } catch (UniqueBeanNameException e) {
                        throw new IllegalStateException(e.getMessage(), e);
                    }
                });
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalStateException("The configuration class doesn't have a public empty constructor");
            }
        }
    }
}
