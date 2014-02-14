package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;


import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;

import javax.ws.rs.core.Response;

public interface OperationResultFactory {

    public <T> OperationResult<T> getOperationResult(Response response, Class<T> responseClass) throws JSClientWebException;

}
