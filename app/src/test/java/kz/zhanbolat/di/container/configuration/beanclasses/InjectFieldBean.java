package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.Inject;

import java.math.BigDecimal;

@Bean(name = "injectFieldBean")
public class InjectFieldBean {

    @Inject
    private BigDecimal bigDecimal;

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }
}
