package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;

import java.math.BigDecimal;

@BeanConfiguration
public class NoInjectOnParameterConfiguration {

    @Bean(name = "bigDecimalBean")
    public BigDecimal bigDecimalBean(Integer integer) {
        return BigDecimal.valueOf(integer);
    }

    @Bean(name = "integerBean")
    public Integer integerBean() {
        return Integer.MAX_VALUE;
    }
}
