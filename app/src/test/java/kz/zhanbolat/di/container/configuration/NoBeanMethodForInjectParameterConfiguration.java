package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@BeanConfiguration
public class NoBeanMethodForInjectParameterConfiguration {

    @Bean(name = "bigDecimalBean")
    public BigDecimal bigDecimalBean(@Inject(beanName = "integerBean") Integer integer) {
        return BigDecimal.valueOf(integer);
    }

    public Integer integerBean() {
        return Integer.MAX_VALUE;
    }
}
