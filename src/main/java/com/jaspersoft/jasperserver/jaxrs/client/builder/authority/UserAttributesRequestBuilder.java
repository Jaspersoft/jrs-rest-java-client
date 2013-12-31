package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

import javax.ws.rs.client.WebTarget;

public class UserAttributesRequestBuilder<RequestType, ResponseType>
        extends RequestBuilder<RequestType, ResponseType> {

    private static final String URI = "/attributes";


    public UserAttributesRequestBuilder(WebTarget concreteTarget, Class responseClass){
        super(responseClass);
        this.setTarget(concreteTarget);
        this.setPath(URI);
    }

}
