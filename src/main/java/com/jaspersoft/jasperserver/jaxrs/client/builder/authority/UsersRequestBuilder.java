package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.GetDeleteRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.PutPostRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class UsersRequestBuilder<T> extends RequestBuilder<T> {

    private GetDeleteRequest<T> getDeleteRequest;
    private PutPostRequest<T> putPostRequest;

    public UsersRequestBuilder(Class responseClass){
        super(responseClass);
    }

    private UsersRequestBuilder(WebTarget target, Class responseClass){
        super(responseClass);
        this.concreteTarget = target;
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
    public UsersGetDeleteBuilder<T> addParam(String name, String... values) {
        concreteTarget = concreteTarget.queryParam(name, values);
        return new UsersGetDeleteBuilder<T>(this);
    }

    @Override
    public UsersGetDeleteBuilder<T> addParams(Map<String, String[]> params) {
        for (Map.Entry<String, String[]> entry : params.entrySet()){
            concreteTarget = concreteTarget.queryParam(entry.getKey(), entry.getValue());
        }
        return new UsersGetDeleteBuilder<T>(this);
    }

    @Override
    public Response put(T entity) {
        return concreteTarget.request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    @Override
    public Response post(T entity) {
        return concreteTarget.request().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
    }

    public UserAttributesRequestBuilder<UserAttributesListWrapper> attributes(){
        return new UserAttributesRequestBuilder<UserAttributesListWrapper>(
                new UsersRequestBuilder<UserAttributesListWrapper>(concreteTarget, UserAttributesListWrapper.class));
    }

    public UserAttributesRequestBuilder<ClientUserAttribute> attribute(String name){
        UserAttributesRequestBuilder<ClientUserAttribute> attributesRequestBuilder =
                new UserAttributesRequestBuilder<ClientUserAttribute>(
                new UsersRequestBuilder<ClientUserAttribute>(concreteTarget, ClientUserAttribute.class));
        attributesRequestBuilder.setPath(name);
        return attributesRequestBuilder;
    }
}
