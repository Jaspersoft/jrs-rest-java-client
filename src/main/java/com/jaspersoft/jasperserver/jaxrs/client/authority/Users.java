package com.jaspersoft.jasperserver.jaxrs.client.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.GetDeleteBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.PostPutBuilder;

import javax.ws.rs.core.Response;

public class Users {

    private static final String URI = "/users";

    public static GetDeleteBuilder<ClientUser> username(String username) {
        GetDeleteBuilder<ClientUser> builder = new GetDeleteBuilder<ClientUser>(ClientUser.class);
        builder.setPath(URI).setPath(username);
        return builder;
    }

    public static GetDeleteBuilder<UsersListWrapper> allUsers() {
        GetDeleteBuilder<UsersListWrapper> builder = new GetDeleteBuilder<UsersListWrapper>(UsersListWrapper.class);
        builder.setPath(URI);
        return builder;
    }

    public static Response addOrUpdateUser(ClientUser user) {
        PostPutBuilder builder = new PostPutBuilder();
        builder.setPath(URI).setPath(user.getUsername());
        return builder.put(user);
    }

}
