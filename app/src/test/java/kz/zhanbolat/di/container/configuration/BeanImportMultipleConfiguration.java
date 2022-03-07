package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.BeanImport;
import kz.zhanbolat.di.container.configuration.beanclasses.InjectNameConstructParamBean;
import kz.zhanbolat.di.container.configuration.beanclasses.InjectSameClassContractParamBean;

import java.math.BigDecimal;

@BeanConfiguration
@BeanImport(beanClasses = {InjectNameConstructParamBean.class,
        InjectSameClassContractParamBean.class})
public class BeanImportMultipleConfiguration {

    @Bean(name = "bigDecimalBean")
    public BigDecimal bigDecimal() {
        return BigDecimal.ZERO;
    }

    @Bean(name = "bigDecimalOneBean")
    public BigDecimal bigDecimalOne() {
        return BigDecimal.ONE;
    }
}
