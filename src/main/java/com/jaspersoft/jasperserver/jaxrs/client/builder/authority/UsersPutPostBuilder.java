package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.api.PutPostRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.api.Request;

import javax.ws.rs.core.Response;

public class UsersPutPostBuilder<T> implements PutPostRequest<T> {

    private UsersRequestBuilder<T> wrappedBuilder;

    public UsersPutPostBuilder(UsersRequestBuilder<T> wrappedBuilder){
        this.wrappedBuilder = wrappedBuilder;
    }

    @Override
    public Response put(T entity) {
        return wrappedBuilder.put(entity);
    }

    @Override
    public Response post(T entity) {
        return wrappedBuilder.post(entity);
    }

    @Override
    public Request setPath(String path) {
        return wrappedBuilder.setPath(path);
    }
}
