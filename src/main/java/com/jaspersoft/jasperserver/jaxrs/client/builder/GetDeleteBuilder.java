package com.jaspersoft.jasperserver.jaxrs.client.builder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class GetDeleteBuilder<T> extends RequestBuilder<T> {

    public GetDeleteBuilder(Class responseClass) {
        super(responseClass);
    }

    public GetDeleteBuilder<T> addParam(String name, String... values){
        concreteTarget = concreteTarget.queryParam(name, values);
        return this;
    }

    public GetDeleteBuilder<T> addParams(Map<String, String> params){
        for (Map.Entry<String, String> entry : params.entrySet()){
            concreteTarget = concreteTarget.queryParam(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public T get(){
        return (T) concreteTarget.request(MediaType.APPLICATION_JSON).get(responseClass);
    }

    public Response delete(){
        return concreteTarget.request().delete();
    }
}