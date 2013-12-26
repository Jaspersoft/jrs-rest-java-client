package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.PutPostRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PutPostBuilder<T> implements PutPostRequest<T> {

    private WebTarget concreteTarget;


    public PutPostBuilder(WebTarget concreteTarget){
        this.concreteTarget = concreteTarget;
    }

    @Override
    public Response put(T entity) {
        return concreteTarget.request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    @Override
    public Response post(T entity) {
        return concreteTarget.request().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    @Override
    public Request setPath(String path) {
        concreteTarget = concreteTarget.path(path);
        return this;
    }

    @Override
    public WebTarget getPath() {
        return concreteTarget;
    }

    @Override
    public Request setTarget(WebTarget webTarget) {
        this.concreteTarget = webTarget;
        return this;
    }
}
