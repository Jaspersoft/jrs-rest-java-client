package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;

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
