package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.GetDeleteBuilder;
public class UsersGetDeleteBuilder<T> extends GetDeleteBuilder<T> {

    private static final String PATH = "attributes";

    public UsersGetDeleteBuilder(Class responseClass) {
        super(responseClass);
    }

    public UsersGetDeleteBuilder<T> attributes(){
        setPath(PATH);
        return this;
    }

    public UsersGetDeleteBuilder<T> attribute(String name){
        setPath(PATH);
        return this;
    }
}
