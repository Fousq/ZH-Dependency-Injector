package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@BeanConfiguration
public class SameInjectClassOnInjectAnnotationConfiguration {

    @Bean(name = "bigDecimalInject")
    public BigDecimal bigDecimalInject(@Inject Long longNum) {
        return BigDecimal.valueOf(longNum);
    }

    @Bean(name = "longMax")
    public Long integerMax() {
        return Long.MAX_VALUE;
    }

    @Bean(name = "longMin")
    public Long integerMin() {
        return Long.MIN_VALUE;
    }
}
