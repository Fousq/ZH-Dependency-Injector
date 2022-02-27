package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;

import java.math.BigDecimal;

@BeanConfiguration
public class SameInjectClassConfiguration {

    @Bean(name = "bigDecimal")
    public BigDecimal bigDecimal(Integer integer) {
        return BigDecimal.valueOf(integer);
    }

    @Bean(name = "integerMax")
    public Integer integerMax() {
        return Integer.MAX_VALUE;
    }

    @Bean(name = "integerMin")
    public Integer integerMin() {
        return Integer.MIN_VALUE;
    }
}
