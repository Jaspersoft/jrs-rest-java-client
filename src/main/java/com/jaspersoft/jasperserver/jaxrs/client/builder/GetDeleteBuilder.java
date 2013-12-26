package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class GetDeleteBuilder<T> implements GetDeleteRequest<T> {

    private WebTarget concreteTarget;
    private Class responseClass;


    public GetDeleteBuilder(WebTarget concreteTarget, Class responseClass){
        this.concreteTarget = concreteTarget;
        this.responseClass = responseClass;
    }

    @Override
    public T get() {
        try {
            return (T) concreteTarget.request(MediaType.APPLICATION_JSON).get(responseClass);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Response delete() {
        return concreteTarget.request().delete();
    }

    @Override
    public GetDeleteBuilder<T> addParam(String name, String... values) {
        concreteTarget = concreteTarget.queryParam(name, values);
        return this;
    }

    @Override
    public GetDeleteBuilder<T> addParams(Map<String, String[]> params) {
        for (Map.Entry<String, String[]> entry : params.entrySet()){
            concreteTarget = concreteTarget.queryParam(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public GetDeleteRequest<T> addMatrixParam(String name, String... values) {
        concreteTarget = concreteTarget.matrixParam(name, values);
        return this;
    }

    @Override
    public GetDeleteRequest<T> addMatrixParams(Map<String, String[]> params) {
        for (Map.Entry<String, String[]> entry : params.entrySet()){
            concreteTarget = concreteTarget.matrixParam(entry.getKey(), entry.getValue());
        }
        return this;
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
}