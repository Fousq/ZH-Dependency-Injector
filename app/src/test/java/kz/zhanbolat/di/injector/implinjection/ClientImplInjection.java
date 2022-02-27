package kz.zhanbolat.di.injector.implinjection;

import kz.zhanbolat.di.annotations.Inject;

public class ClientImplInjection {

    @Inject
    private ServiceImplInjection service;

    public ServiceImplInjection getService() {
        return service;
    }
}
