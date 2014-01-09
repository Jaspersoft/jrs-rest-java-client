package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.core.MultivaluedMap;

public interface GetDeleteRequest<ResponseType> extends Request{

    OperationResult<ResponseType> get();
    OperationResult<ResponseType> delete();
    GetDeleteRequest<ResponseType> addParam(String name, String... values);
    GetDeleteRequest<ResponseType> addParams(MultivaluedMap<String, String> params);
    GetDeleteRequest<ResponseType> addMatrixParam(String name, String... values);
    GetDeleteRequest<ResponseType> addMatrixParams(MultivaluedMap<String, String> params);

}
