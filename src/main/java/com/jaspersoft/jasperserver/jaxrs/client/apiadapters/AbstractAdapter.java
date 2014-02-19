package com.jaspersoft.jasperserver.jaxrs.client.apiadapters;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

public abstract class AbstractAdapter {

    protected final SessionStorage sessionStorage;

    public AbstractAdapter(SessionStorage sessionStorage){
        this.sessionStorage = sessionStorage;
    }

}
