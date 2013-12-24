package com.jaspersoft.jasperserver.jaxrs.client.builder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PostPutBuilder<T> extends RequestBuilder<T> {

    public PostPutBuilder(){}

    public PostPutBuilder(Class responseClass) {
        super(responseClass);
    }

    public Response put(Object entity){
        return concreteTarget.request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    public Response post(Object entity){
        return concreteTarget.request().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

}
