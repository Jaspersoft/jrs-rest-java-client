package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.PutPostRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class PutPostBuilder<RequestType, ResponseType> implements PutPostRequest<RequestType, ResponseType> {

    private WebTarget concreteTarget;
    private Class<ResponseType> responseClass;

    private MultivaluedMap<String, Object> headers;


    public PutPostBuilder(WebTarget concreteTarget, Class<ResponseType> responseClass){
        this.concreteTarget = concreteTarget;
        this.responseClass = responseClass;
        headers = new MultivaluedHashMap<String, Object>();
    }

    @Override
    public OperationResult<ResponseType> put(RequestType entity) {
        Invocation.Builder request = concreteTarget.request();
        request.headers(headers);
        Response response = request.put(Entity.entity(entity, MediaType.APPLICATION_JSON));
        return new OperationResult<ResponseType>(response, responseClass);
    }

    @Override
    public OperationResult<ResponseType> post(RequestType entity) {
        Invocation.Builder request = concreteTarget.request();
        request.headers(headers);
        Response response = request.post(Entity.entity(entity, MediaType.APPLICATION_JSON));
        return new OperationResult<ResponseType>(response, responseClass);
    }

    @Override
    public Request setPath(String path) {
        concreteTarget = concreteTarget.path(path);
        return this;
    }

    @Override
    public WebTarget getPath() {
        return concreteTarget;
    }

    @Override
    public Request setTarget(WebTarget webTarget) {
        this.concreteTarget = webTarget;
        return this;
    }

    @Override
    public void addHeader(String name, String value) {
        headers.putSingle(name, value);
    }
}
