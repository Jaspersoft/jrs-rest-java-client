package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public interface PutPostRequest<RequestType, ResponseType> extends Request{

    OperationResult<ResponseType> put(RequestType entity);
    OperationResult<ResponseType> post(RequestType entity);

}
