package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@Bean(name = "injectNameConstructParamBean")
public class InjectNameConstructParamBean {

    private BigDecimal bigDecimal;
    private BigDecimal bigDecimalOne;

    @BeanConstructor
    public InjectNameConstructParamBean(@Inject(beanName = "bigDecimalBean") BigDecimal bigDecimal,
                                        @Inject(beanName = "bigDecimalOneBean") BigDecimal bigDecimalOne) {
        this.bigDecimal = bigDecimal;
        this.bigDecimalOne = bigDecimalOne;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public BigDecimal getBigDecimalOne() {
        return bigDecimalOne;
    }
}
