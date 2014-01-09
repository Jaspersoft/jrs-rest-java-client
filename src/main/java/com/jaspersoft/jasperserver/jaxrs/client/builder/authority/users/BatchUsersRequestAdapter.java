package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users;

import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class BatchUsersRequestAdapter {

    private final JerseyRequestBuilder<UsersListWrapper> builder;
    private AuthenticationCredentials credentials;

    private MultivaluedMap<String, String> params;


    public BatchUsersRequestAdapter(AuthenticationCredentials credentials) {
        this.credentials = credentials;
        this.builder = new JerseyRequestBuilder<UsersListWrapper>(credentials, UsersListWrapper.class);
        this.builder.setPath("users");
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchUsersRequestAdapter param(UsersParameter userParam, String value){
        params.add(userParam.getParamName(), value);
        return this;
    }

    public OperationResult<UsersListWrapper> get(){
        builder.addParams(params);
        return builder.get();
    }

}
