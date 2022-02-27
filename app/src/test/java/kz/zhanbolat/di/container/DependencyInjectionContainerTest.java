package kz.zhanbolat.di.container;

import kz.zhanbolat.di.container.configuration.*;
import kz.zhanbolat.di.exception.BeanInitializationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectionContainerTest {
    private ClassConfigurationDependencyInjectionContainer dependencyInjectionContainer;

    @Test
    void createBeanWithoutParameters() {
        dependencyInjectionContainer = new ClassConfigurationDependencyInjectionContainer(ZeroParamConfiguration.class);
        Object bean = dependencyInjectionContainer.getBean("noParamsBean");
        assertNotNull(bean);
    }

    @Test
    void createBeanWithParameters() {
        dependencyInjectionContainer = new ClassConfigurationDependencyInjectionContainer(ParameterConfiguration.class);
        Object bean = dependencyInjectionContainer.getBean("bigDecimalBean");
        assertNotNull(bean);
    }

    @Test
    void givenConfigurationWithoutBeanConfigurationAnnotation_whenCreate_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ClassConfigurationDependencyInjectionContainer(NoBeanConfigurationAnnotationConfig.class));
        assertEquals("Provided class is not a configuration class", exception.getMessage());
    }

    @Test
    void givenConfigurationWithoutAnyBeans_whenGetBean_thenDoesNotThrowException() {
        assertDoesNotThrow(() -> new ClassConfigurationDependencyInjectionContainer(NoBeanConfiguration.class));
    }

    @Test
    void givenConfigurationWithoutInjectOnParameter_whenGetBean_thenReturnBean() {
        dependencyInjectionContainer = new ClassConfigurationDependencyInjectionContainer(NoInjectOnParameterConfiguration.class);
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean("bigDecimalBean", BigDecimal.class);
        assertNotNull(bigDecimalBean);
    }

    @Test
    void givenConfigurationWithoutBeanInitMethodForInjectParameter_whenCreate_thenThrowException() {
        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> new ClassConfigurationDependencyInjectionContainer(NoBeanMethodForInjectParameterConfiguration.class));
        assertTrue(exception.getMessage().contains("There's no bean init method for name"));
    }

    @Test
    void givenConfigurationWithSeveralBeanMethods_whenGetBean_thenReturnBeanByClass() {
        dependencyInjectionContainer = new ClassConfigurationDependencyInjectionContainer(ParameterConfiguration.class);
        BigDecimal bigDecimalBean = dependencyInjectionContainer.getBean(BigDecimal.class);
        Integer integerBean = dependencyInjectionContainer.getBean(Integer.class);

        assertNotNull(bigDecimalBean);
        assertNotNull(integerBean);
    }

    @Test
    void givenConfigurationWithSameInjectClass_whenCreate_thenThrowException() {
        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> new ClassConfigurationDependencyInjectionContainer(SameInjectClassConfiguration.class));
        assertEquals("Cannot inject with several class match beans. Please, specify the required bean by its name",
                exception.getMessage());
    }

    @Test
    @Disabled("Strange behaviour when running with test #givenConfigurationWithSameInjectClass_whenCreate_thenThrowException")
    void givenConfigurationWithSameInjectClassOnInjectAnnotation_whenCreate_thenThrowException() {
        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> new ClassConfigurationDependencyInjectionContainer(SameInjectClassOnInjectAnnotationConfiguration.class));
        assertEquals("Cannot inject with several class match beans. Please, specify the required bean by its name",
                exception.getMessage());
    }
}
