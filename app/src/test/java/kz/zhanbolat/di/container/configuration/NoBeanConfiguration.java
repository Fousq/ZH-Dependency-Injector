package kz.zhanbolat.di.container.configuration;

import kz.zhanbolat.di.annotations.BeanConfiguration;

@BeanConfiguration
public class NoBeanConfiguration {

    public Object method() {
        return new Object();
    }
}
