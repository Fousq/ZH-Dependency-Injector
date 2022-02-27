package kz.zhanbolat.di.container;

import kz.zhanbolat.di.container.configuration.*;
import kz.zhanbolat.di.exception.BeanInitializationException;
import org.junit.jupiter.api.Test;

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
    void givenConfigurationWithoutInjectOnParameter_whenCreate_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ClassConfigurationDependencyInjectionContainer(NoInjectOnParameterConfiguration.class));
        assertEquals("Method's parameters are not annotated with annotation @Inject", exception.getMessage());
    }

    @Test
    void givenConfigurationWithoutBeanInitMethodForInjectParameter_whenCreate_thenThrowException() {
        BeanInitializationException exception = assertThrows(BeanInitializationException.class,
                () -> new ClassConfigurationDependencyInjectionContainer(NoBeanMethodForInjectParameterConfiguration.class));
        assertTrue(exception.getMessage().contains("There's no bean init method for name"));
    }
}
