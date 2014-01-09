package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.BatchUsersRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.SingleUserRequestAdapter;

public class UsersService {

    private final AuthenticationCredentials credentials;

    public UsersService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public SingleUserRequestAdapter username(String username) {
        return new SingleUserRequestAdapter(credentials, username);
    }


    public BatchUsersRequestAdapter allUsers(){
        return new BatchUsersRequestAdapter(credentials);
    }

}
