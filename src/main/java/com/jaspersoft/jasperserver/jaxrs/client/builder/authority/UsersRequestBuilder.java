package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

public class UsersRequestBuilder<T> extends RequestBuilder<T> {

    private static final String URI = "/users";

    public UsersRequestBuilder(Class responseClass) {
        super(responseClass);
        this.setPath(URI);
    }

    public UserAttributesRequestBuilder<UserAttributesListWrapper> attributes() {
        return new UserAttributesRequestBuilder<UserAttributesListWrapper>(
                this.getPath(), UserAttributesListWrapper.class);
    }

    public UserAttributesRequestBuilder<ClientUserAttribute> attribute(String name) {
        UserAttributesRequestBuilder<ClientUserAttribute> attributesRequestBuilder =
                new UserAttributesRequestBuilder<ClientUserAttribute>(
                        this.getPath(), ClientUserAttribute.class);
        attributesRequestBuilder.setPath(name);

        return attributesRequestBuilder;
    }

}
