package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import javax.ws.rs.core.Response;

public class WithEntityOperationResult<T> extends OperationResult<T> {

    public WithEntityOperationResult(Response response, Class<? extends T> entityClass) {
        super(response, entityClass);
    }

    @Override
    public T getEntity() {
        return super.getEntity();
    }

    @Override
    public Response getResponse() {
        return super.getResponse();
    }
}
