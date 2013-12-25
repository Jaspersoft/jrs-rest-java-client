package com.jaspersoft.jasperserver.jaxrs.client.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.UsersRequestBuilder;

public class Users {

    private static final String URI = "/users";

    public static UsersRequestBuilder<ClientUser> username(String username) {
        UsersRequestBuilder<ClientUser> builder = new UsersRequestBuilder<ClientUser>(ClientUser.class);
        builder.setPath(URI).setPath(username);
        return builder;
    }

    public static UsersRequestBuilder<UsersListWrapper> allUsers() {
        UsersRequestBuilder<UsersListWrapper> builder = new UsersRequestBuilder<UsersListWrapper>(UsersListWrapper.class);
        builder.setPath(URI);
        return builder;
    }

}
