package kz.zhanbolat.di.container;

import kz.zhanbolat.di.container.builder.BeanBuilderFactory;
import kz.zhanbolat.di.container.builder.BeanBuilderFactoryImpl;
import kz.zhanbolat.di.container.configuration.*;
import kz.zhanbolat.di.container.configuration.beanclasses.*;
import kz.zhanbolat.di.container.converter.BeanConverterFactory;
import kz.zhanbolat.di.container.converter.BeanConverterFactoryImpl;
import kz.zhanbolat.di.exception.BeanInitializationException;
import kz.zhanbolat.di.exception.BeanNotFoundException;
import kz.zhanbolat.di.exception.MultipleBeanException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectionContainerTest {
    private static BeanBuilderFactory beanBuilderFactory;
    private static BeanConverterFactory beanConverterFactory;

    private AnnotationConfigurationDependencyInjectionContainer dependencyInjectionContainer;

    @BeforeAll
    public static void init() {
        beanBuilderFactory = new BeanBuilderFactoryImpl();
        beanConverterFactory = new BeanConverterFactoryImpl();
    }

    @Test
    void createBeanWithoutParameters() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(ZeroParamConfiguration.class,
                beanConverterFactory, beanBuilderFactory);
        Object bean = dependencyInjectionContainer.getBean("noParamsBean");
        assertNotNull(bean);
    }

    @Test
    void createBeanWithParameters() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(ParameterConfiguration.class,
                beanConverterFactory, beanBuilderFactory);
        Object bean = dependencyInjectionContainer.getBean("bigDecimalBean");
        assertNotNull(bean);
    }

    @Test
    void givenConfigurationWithoutBeanConfigurationAnnotation_whenCreate_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new AnnotationConfigurationDependencyInjectionContainer(NoBeanConfigurationAnnotationConfig.class,
                        beanConverterFactory, beanBuilderFactory));
        assertEquals("The configuration class is not annotated with @BeanConfiguration", exception.getMessage());
    }

    @Test
    void givenConfigurationWithoutAnyBeans_whenGetBean_thenDoesNotThrowException() {
        assertDoesNotThrow(() -> new AnnotationConfigurationDependencyInjectionContainer(NoBeanConfiguration.class,
                beanConverterFactory, beanBuilderFactory));
    }

    @Test
    void givenConfigurationWithoutInjectOnParameter_whenGetBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(NoInjectOnParameterConfiguration.class,
                beanConverterFactory, beanBuilderFactory);
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);
        assertNotNull(bigDecimalBean);
    }

    @Test
    void givenConfigurationWithoutBeanInitMethodForInjectParameter_whenGetBean_thenThrowException() {
         dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(NoBeanMethodForInjectParameterConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> dependencyInjectionContainer.getBean("bigDecimalBeanByName"));
        assertTrue(exception.getMessage().contains("integerBean"));
    }

    @Test
    void givenConfigurationWithSeveralBeanMethods_whenGetBean_thenReturnBeanByClass() throws MultipleBeanException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(ParameterConfiguration.class,
                beanConverterFactory, beanBuilderFactory);
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean(BigDecimal.class);
        Integer integerBean = dependencyInjectionContainer.getBean(Integer.class);

        assertNotNull(bigDecimalBean);
        assertNotNull(integerBean);
    }

    @Test
    void givenConfigurationWithSameInjectClass_whenGetBean_thenThrowException() {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(SameInjectClassConfiguration.class,
                beanConverterFactory, beanBuilderFactory);
        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> dependencyInjectionContainer.getBean("bigDecimal"));
        assertTrue(exception.getMessage().contains("There's several beans for class"));
    }

    @Test
    void givenConfigurationWithSameInjectClassOnInjectAnnotation_whenGetBean_thenThrowException() {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(SameInjectClassOnInjectAnnotationConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> dependencyInjectionContainer.getBean("bigDecimalInject"));
        assertTrue(exception.getMessage().contains("There's several beans for class"));
    }

    @Test
    void givenConfigurationWithSameBeanNames_whenCreate_thenThrowException() {
        assertThrows(IllegalStateException.class, () -> new AnnotationConfigurationDependencyInjectionContainer(SameBeanNamesConfiguration.class,
                beanConverterFactory, beanBuilderFactory));
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_EmptyBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("emptyBean");
        assertNotNull(bean);
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_EmptyConstructParamBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("emptyConstructParamBean");
        assertNotNull(bean);
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_InjectNameConstructParamBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportMultipleConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("injectNameConstructParamBean");
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);
        BigDecimal bigDecimalOneBean = dependencyInjectionContainer.getBean("bigDecimalOneBean", BigDecimal.class);

        assertNotNull(bean);
        assertEquals(bigDecimalBean, ((InjectNameConstructParamBean) bean).getBigDecimal());
        assertEquals(bigDecimalOneBean, ((InjectNameConstructParamBean) bean).getBigDecimalOne());
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_InjectConstructParamBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("injectConstructParamBean");
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);

        assertNotNull(bean);
        assertEquals(bigDecimalBean, ((InjectConstructParamBean) bean).getBigDecimal());
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_ConstructParamBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("constructParamBean");
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);

        assertNotNull(bean);
        assertEquals(bigDecimalBean, ((ConstructParamBean) bean).getBigDecimal());
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_InjectSameClassConstructParamBean_thenReturnBean() {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportMultipleConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> dependencyInjectionContainer.getBean("injectSameClassContractParamBean"));
        assertTrue(exception.getMessage().contains("There's several beans for class"));
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_InjectFieldBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("injectFieldBean");
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);

        assertNotNull(bean);
        assertEquals(bigDecimalBean, ((InjectFieldBean) bean).getBigDecimal());
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_InjectNameFieldBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportMultipleConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("injectNameFieldBean");
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);
        BigDecimal bigDecimalOneBean = dependencyInjectionContainer.getBean("bigDecimalOneBean", BigDecimal.class);

        assertNotNull(bean);
        assertEquals(bigDecimalBean, ((InjectNameFieldBean) bean).getBigDecimal());
        assertEquals(bigDecimalOneBean, ((InjectNameFieldBean) bean).getBigDecimalOne());
    }

    @Test
    void givenBeanImportConfiguration_whenGetBean_SkipInjectFieldBean_thenReturnBean() throws BeanNotFoundException {
        dependencyInjectionContainer = new AnnotationConfigurationDependencyInjectionContainer(BeanImportMultipleConfiguration.class,
                beanConverterFactory, beanBuilderFactory);

        Object bean = dependencyInjectionContainer.getBean("skipInjectFieldBean");
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);

        assertNotNull(bean);
        assertEquals(bigDecimalBean, ((SkipInjectFieldBean) bean).getBigDecimal());
        assertNull(((SkipInjectFieldBean) bean).getInteger());
        assertNull(((SkipInjectFieldBean) bean).getBigDecimalOne());
    }
}
