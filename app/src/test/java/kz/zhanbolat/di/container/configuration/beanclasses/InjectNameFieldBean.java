package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@Bean(name = "injectNameFieldBean")
public class InjectNameFieldBean {

    @Inject(beanName = "bigDecimalBean")
    private BigDecimal bigDecimal;

    @Inject(beanName = "bigDecimalOneBean")
    private BigDecimal bigDecimalOne;

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public BigDecimal getBigDecimalOne() {
        return bigDecimalOne;
    }

    public void setBigDecimalOne(BigDecimal bigDecimalOne) {
        this.bigDecimalOne = bigDecimalOne;
    }
}
