package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;

import java.math.BigDecimal;

@Bean(name = "injectSameClassContractParamBean")
public class InjectSameClassContractParamBean {
    private BigDecimal bigDecimal;
    private BigDecimal bigDecimalOne;

    @BeanConstructor
    public InjectSameClassContractParamBean(BigDecimal bigDecimal, BigDecimal bigDecimalOne) {
        this.bigDecimal = bigDecimal;
        this.bigDecimalOne = bigDecimalOne;
    }
}
