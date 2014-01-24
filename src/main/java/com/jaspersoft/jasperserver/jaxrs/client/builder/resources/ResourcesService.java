package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class ResourcesService {

    private final SessionStorage sessionStorage;

    public ResourcesService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public BatchResourcesAdapter resources(){
        return new BatchResourcesAdapter(sessionStorage);
    }

    public SingleResourceAdapter resource(String uri){
        return new SingleResourceAdapter(sessionStorage, uri);
    }



}
