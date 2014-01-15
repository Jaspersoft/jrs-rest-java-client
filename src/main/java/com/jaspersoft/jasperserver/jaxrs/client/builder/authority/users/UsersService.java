package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users;

import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class UsersService {

    private final SessionStorage sessionStorage;

    public UsersService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public SingleUserRequestAdapter username(String username) {
        return new SingleUserRequestAdapter(sessionStorage, username);
    }


    public BatchUsersRequestAdapter allUsers(){
        return new BatchUsersRequestAdapter(sessionStorage);
    }

}
