package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class RolesRequestBuilder<T> extends RequestBuilder<T> {

    public RolesRequestBuilder(Class responseClass) {
        super(responseClass);
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
    public RolesGetDeleteBuilder<T> addParam(String name, String... values) {
        concreteTarget = concreteTarget.queryParam(name, values);
        return new RolesGetDeleteBuilder<T>(this);
    }

    @Override
    public RolesGetDeleteBuilder<T> addParams(Map<String, String[]> params) {
        for (Map.Entry<String, String[]> entry : params.entrySet()){
            concreteTarget = concreteTarget.queryParam(entry.getKey(), entry.getValue());
        }
        return new RolesGetDeleteBuilder<T>(this);
    }

    @Override
    public Response put(T entity) {
        return concreteTarget.request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    @Override
    public Response post(T entity) {
        return concreteTarget.request().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }
}
