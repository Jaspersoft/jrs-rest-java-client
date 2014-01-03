package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.UsersRequestBuilder;

public class Users {

    public static UsersRequestBuilder<ClientUser, ClientUser> username(String username) {
        UsersRequestBuilder<ClientUser, ClientUser> builder =
                new UsersRequestBuilder<ClientUser, ClientUser>(ClientUser.class);
        builder.setPath(username);
        return builder;
    }

    public static UsersRequestBuilder<UsersListWrapper, UsersListWrapper> allUsers() {
        return new UsersRequestBuilder<UsersListWrapper, UsersListWrapper>(UsersListWrapper.class);
    }

    public static void main(String[] args) {
        Users.username("demo").delete();
        Users.username("Bobby").delete();
        Roles.rolename("ROLE_HELLO").delete();
    }

}
