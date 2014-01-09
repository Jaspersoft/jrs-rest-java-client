package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public interface PutPostRequest<ResponseType> extends Request{

    <RequestType> OperationResult<ResponseType> put(RequestType entity);
    <RequestType> OperationResult<ResponseType> post(RequestType entity);

}
