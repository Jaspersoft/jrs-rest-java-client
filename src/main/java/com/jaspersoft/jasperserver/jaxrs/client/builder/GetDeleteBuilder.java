package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;

public class GetDeleteBuilder<T> implements GetDeleteRequest<T> {

    private WebTarget concreteTarget;
    private Class<T> responseClass;

    private MultivaluedMap<String, Object> headers;
    private String mime;


    public GetDeleteBuilder(WebTarget concreteTarget, Class<T> responseClass){
        this.concreteTarget = concreteTarget;
        this.responseClass = responseClass;
        this.mime = MediaType.APPLICATION_JSON;
        headers = new MultivaluedHashMap<String, Object>();
    }

    @Override
    public OperationResult<T> get() {
        try {
            Invocation.Builder request = concreteTarget.request(mime);
            request.headers(headers);
            Response response = request.get();
            return new OperationResult<T>(response, responseClass);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public OperationResult<T> delete() {
        Invocation.Builder request = concreteTarget.request();
        request.headers(headers);
        Response response = request.delete();
        return new OperationResult<T>(response, responseClass);
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

    @Override
    public void addHeader(String name, String value) {
        headers.putSingle(name, value);
    }

    @Override
    public void setContentType(String mime) {
        this.mime = mime;
    }

}