package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;

import java.math.BigDecimal;

@Bean(name = "constructParamBean")
public class ConstructParamBean {

    private BigDecimal bigDecimal;

    @BeanConstructor
    public ConstructParamBean(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }
}
