package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

import javax.ws.rs.core.Response;
import java.util.Map;

public class UserAttributesRequestBuilder<T>  {

    private static final String URI = "/attributes";

    private UsersRequestBuilder<T> usersRequestBuilder;

    public UserAttributesRequestBuilder(UsersRequestBuilder<T> usersRequestBuilder){
        this.usersRequestBuilder = usersRequestBuilder;
        usersRequestBuilder.setPath(URI);
    }

    public T get() {
        return usersRequestBuilder.get();
    }

    public Response delete() {
        return usersRequestBuilder.delete();
    }

    public UsersGetDeleteBuilder<T> addParam(String name, String... values) {
        return usersRequestBuilder.addParam(name, values);
    }

    public UsersGetDeleteBuilder<T> addParams(Map<String, String[]> params) {
        return usersRequestBuilder.addParams(params);
    }

    public Response put(T entity) {
        return usersRequestBuilder.put(entity);
    }

    public Response post(T entity) {
        return usersRequestBuilder.post(entity);
    }

    public RequestBuilder<T> setPath(String path) {
        return usersRequestBuilder.setPath(path);
    }

}
