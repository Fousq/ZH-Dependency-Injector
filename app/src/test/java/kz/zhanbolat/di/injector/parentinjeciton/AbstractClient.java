package kz.zhanbolat.di.injector.parentinjeciton;

import kz.zhanbolat.di.annotations.Inject;

public abstract class AbstractClient {

    @Inject
    private AbstractService service;

    public AbstractService getService() {
        return service;
    }
}
