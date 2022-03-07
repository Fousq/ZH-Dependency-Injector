package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@Bean(name = "skipInjectFieldBean")
public class SkipInjectFieldBean {

    private Integer integer;

    private BigDecimal bigDecimal;

    @Inject(beanName = "bigDecimalOneBean")
    private BigDecimal bigDecimalOne;

    @BeanConstructor
    public SkipInjectFieldBean(@Inject(beanName = "bigDecimalBean") BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

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
