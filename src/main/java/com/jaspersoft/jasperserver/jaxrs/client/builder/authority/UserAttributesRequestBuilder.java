package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Map;

public class UserAttributesRequestBuilder<T> extends RequestBuilder<T> {

    private static final String URI = "/attributes";


    public UserAttributesRequestBuilder(WebTarget concreteTarget, Class responseClass){
        super(responseClass);
        this.setTarget(concreteTarget);
        this.setPath(URI);
    }

    public T get() {
        return getDeleteRequest.get();
    }

    public Response delete() {
        return getDeleteRequest.delete();
    }

    public GetDeleteRequest<T> addParam(String name, String... values) {
        return getDeleteRequest.addParam(name, values);
    }

    public GetDeleteRequest<T> addParams(Map<String, String[]> params) {
        return getDeleteRequest.addParams(params);
    }

    public Response put(T entity) {
        return putPostRequest.put(entity);
    }

    public Response post(T entity) {
        return putPostRequest.post(entity);
    }

}
