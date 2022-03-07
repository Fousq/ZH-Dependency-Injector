package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;
import kz.zhanbolat.di.annotations.BeanImport;
import kz.zhanbolat.di.container.configuration.beanclasses.*;

import java.math.BigDecimal;

@BeanConfiguration
@BeanImport(beanClasses = {
        EmptyBean.class,
        EmptyConstructParamBean.class,
        InjectConstructParamBean.class,
        ConstructParamBean.class
})
public class BeanImportConfiguration {

    @Bean(name = "bigDecimalBean")
    public BigDecimal bigDecimal() {
        return BigDecimal.ZERO;
    }

}
