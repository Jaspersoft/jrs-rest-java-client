package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;

public class RolesRequestBuilder<ResponseType>
        extends JerseyRequestBuilder<ResponseType> {

    private static final String URI = "/roles";

    public RolesRequestBuilder(AuthenticationCredentials credentials, Class<ResponseType> responseClass) {
        super(credentials, responseClass);
        this.setPath(URI);
    }

}
