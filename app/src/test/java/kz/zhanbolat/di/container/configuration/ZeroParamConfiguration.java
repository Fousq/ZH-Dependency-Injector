package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;
import kz.zhanbolat.di.annotations.BeanConfiguration;

@BeanConfiguration
public class ZeroParamConfiguration {

    @Bean(name = "noParamsBean")
    public Object bean() {
        return new Object();
    }
}
