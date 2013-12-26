package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

import javax.ws.rs.core.Response;
import java.util.Map;

public interface GetDeleteRequest<T> extends Request{

    T get();
    Response delete();
    GetDeleteRequest<T> addParam(String name, String... values);
    GetDeleteRequest<T> addParams(Map<String, String[]> params);
    GetDeleteRequest<T> addMatrixParam(String name, String... values);
    GetDeleteRequest<T> addMatrixParams(Map<String, String[]> params);

}
