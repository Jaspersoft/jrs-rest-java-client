package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

public class RolesRequestBuilder<RequestType, ResponseType>
        extends RequestBuilder<RequestType, ResponseType> {

    private static final String URI = "/roles";

    public RolesRequestBuilder(Class<ResponseType> responseClass) {
        super(responseClass);
        this.setPath(URI);
    }

}
