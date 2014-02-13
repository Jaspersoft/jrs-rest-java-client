package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;


import javax.ws.rs.core.Response;

public interface OperationResultFactory {

    public <T> OperationResult<? extends T> getOperationResult(Response response, Class<? extends T> responseClass);

}
