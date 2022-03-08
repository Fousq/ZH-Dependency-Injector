package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.BeanType;

import java.math.BigDecimal;

@BeanConfiguration
public class BeanTypeConfiguration {

    @Bean(name = "singletonObjectBean")
    public Object singletonObject() {
        return new Object();
    }

    @Bean(name = "prototypeObjectBean", type = BeanType.PROTOTYPE)
    public Object prototypeObject() {
        return new Object();
    }
}
