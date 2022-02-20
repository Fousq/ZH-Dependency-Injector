package kz.zhanbolat.di.parentinjeciton;

import kz.zhanbolat.di.annotations.Inject;

public abstract class AbstractClient {

    @Inject
    private AbstractService service;

    public AbstractService getService() {
        return service;
    }
}
