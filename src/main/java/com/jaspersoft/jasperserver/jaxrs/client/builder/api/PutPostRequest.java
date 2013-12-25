package com.jaspersoft.jasperserver.jaxrs.client.builder.api;

import javax.ws.rs.core.Response;

public interface PutPostRequest<T> extends Request{

    Response put(T entity);
    Response post(T entity);

}
