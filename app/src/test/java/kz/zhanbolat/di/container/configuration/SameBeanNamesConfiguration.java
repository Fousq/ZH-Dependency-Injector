package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;

import java.math.BigDecimal;

@BeanConfiguration
public class SameBeanNamesConfiguration {

    @Bean(name = "bigDecimalBean")
    public BigDecimal bigDecimalOne() {
        return BigDecimal.ONE;
    }

    @Bean(name = "bigDecimalBean")
    public BigDecimal bigDecimalZero() {
        return BigDecimal.ZERO;
    }
}
