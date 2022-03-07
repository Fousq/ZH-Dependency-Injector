package kz.zhanbolat.di.container.configuration.beanclasses;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConstructor;

@Bean(name = "emptyConstructParamBean")
public class EmptyConstructParamBean {

    @BeanConstructor
    public EmptyConstructParamBean() {
    }
}
