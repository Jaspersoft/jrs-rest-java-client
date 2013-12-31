package com.jaspersoft.jasperserver.jaxrs.client.builder;

import javax.ws.rs.core.Response;

public class OperationResult<T> {

    private Response response;
    private Class<T> entityClass;

    public OperationResult(Response response, Class<T> entityClass){
        this.response = response;
        this.entityClass = entityClass;
    }

    public T getEntity(){
        try {
            return response.readEntity(entityClass);
        } catch (Exception e) {
            return null;
        }
    }

    public Response getResponse(){
        return response;
    }
}
