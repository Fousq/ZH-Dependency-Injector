package kz.zhanbolat.di.injector.interfacteinjection;

import kz.zhanbolat.di.annotations.Inject;

public class Client {

    @Inject(beanName = "")
    private Service service;

    public Service getService() {
        return service;
    }
}
