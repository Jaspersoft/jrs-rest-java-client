package com.jaspersoft.jasperserver.jaxrs.client.builder.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

public class UsersRequestBuilder<RequestType, ResponseType>
        extends RequestBuilder<RequestType, ResponseType> {

    private static final String URI = "/users";

    public UsersRequestBuilder(Class<ResponseType> responseClass) {
        super(responseClass);
        this.setPath(URI);
    }

    public UserAttributesRequestBuilder<UserAttributesListWrapper, UserAttributesListWrapper> attributes() {
        return new UserAttributesRequestBuilder<UserAttributesListWrapper, UserAttributesListWrapper>(
                this.getPath(), UserAttributesListWrapper.class);
    }

    public UserAttributesRequestBuilder<ClientUserAttribute, ClientUserAttribute> attribute(String name) {
        UserAttributesRequestBuilder<ClientUserAttribute, ClientUserAttribute> attributesRequestBuilder =
                new UserAttributesRequestBuilder<ClientUserAttribute, ClientUserAttribute>(
                        this.getPath(), ClientUserAttribute.class);
        attributesRequestBuilder.setPath(name);

        return attributesRequestBuilder;
    }

}
