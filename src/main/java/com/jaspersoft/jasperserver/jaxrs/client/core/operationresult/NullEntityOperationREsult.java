package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;


import javax.ws.rs.core.Response;

public class NullEntityOperationResult extends OperationResult<Object> {

    public NullEntityOperationResult(Response response, Class entityClass) {
        super(response, entityClass);
    }

    @Override
    public Object getEntity() {
        return null;
    }
}
