package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;


import javax.ws.rs.core.Response;

public class NullEntityOperationResult<T> extends OperationResult<T> {

    public NullEntityOperationResult(Response response, Class entityClass) {
        super(response, entityClass);
    }

    @Override
    public T getEntity() {
        return null;
    }

}
