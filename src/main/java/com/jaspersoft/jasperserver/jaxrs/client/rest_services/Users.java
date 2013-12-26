package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.UsersRequestBuilder;

public class Users {

    public static UsersRequestBuilder<ClientUser> username(String username) {
        UsersRequestBuilder<ClientUser> builder = new UsersRequestBuilder<ClientUser>(ClientUser.class);
        builder.setPath(username);
        return builder;
    }

    public static UsersRequestBuilder<UsersListWrapper> allUsers() {
        return new UsersRequestBuilder<UsersListWrapper>(UsersListWrapper.class);
    }

}
