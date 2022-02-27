package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.Bean;

public class NoBeanConfigurationAnnotationConfig {

    @Bean(name = "bean")
    public Object bean() {
        return new Object();
    }
}
