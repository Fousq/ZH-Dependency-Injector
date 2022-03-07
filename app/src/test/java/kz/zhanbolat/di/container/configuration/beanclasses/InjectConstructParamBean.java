package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@Bean(name = "injectConstructParamBean")
public class InjectConstructParamBean {

    private BigDecimal bigDecimal;

    @BeanConstructor
    public InjectConstructParamBean(@Inject BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }
}
