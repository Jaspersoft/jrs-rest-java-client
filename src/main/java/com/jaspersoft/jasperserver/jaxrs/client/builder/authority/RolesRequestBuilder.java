package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

public class RolesRequestBuilder<T> extends RequestBuilder<T> {

    private static final String URI = "/roles";

    public RolesRequestBuilder(Class responseClass) {
        super(responseClass);
        this.setPath(URI);
    }

}
