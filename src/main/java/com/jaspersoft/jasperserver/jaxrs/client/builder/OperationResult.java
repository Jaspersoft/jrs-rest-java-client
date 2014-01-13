package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

import javax.ws.rs.core.Response;

public class OperationResult<T> {

    private Response response;
    private Class<T> entityClass;
    private ErrorDescriptor error;

    public OperationResult(Response response, Class<T> entityClass){
        this.response = response;
        this.entityClass = entityClass;
        if (response.getStatus() == 500)
            error = response.readEntity(ErrorDescriptor.class);
    }

    public T getEntity(){
        if (response.getStatus() == 404)
            return null;

        try {
            return response.readEntity(entityClass);
        } catch (Exception e) {
            return null;
        }
    }

    public Response getResponse(){
        return response;
    }

    public ErrorDescriptor getError() {
        return error;
    }

    public String getSessionId(){
        return response.getCookies().get("JSESSIONID").getValue();
    }

}
