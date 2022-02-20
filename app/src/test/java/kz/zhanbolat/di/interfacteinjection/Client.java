package kz.zhanbolat.di.interfacteinjection;

import kz.zhanbolat.di.annotations.Inject;

public class Client {

    @Inject
    private Service service;

    public Service getService() {
        return service;
    }
}
