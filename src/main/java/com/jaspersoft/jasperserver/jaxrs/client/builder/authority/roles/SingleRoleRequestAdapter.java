package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public class SingleRoleRequestAdapter {

    private final JerseyRequestBuilder<ClientRole> builder;
    private AuthenticationCredentials credentials;

    private String rolename;


    public SingleRoleRequestAdapter(AuthenticationCredentials credentials, String rolename) {
        this.credentials = credentials;
        this.builder = new JerseyRequestBuilder<ClientRole>(credentials, ClientRole.class);
        this.builder
                .setPath("/roles")
                .setPath(rolename);
        this.rolename = rolename;
    }

    public OperationResult<ClientRole> get(){
        JerseyRequestBuilder<ClientRole> builder =
                new JerseyRequestBuilder<ClientRole>(credentials, ClientRole.class);
        builder
                .setPath("/roles")
                .setPath(rolename);
        return builder.get();
    }

    public OperationResult<ClientRole> addOrUpdate(ClientRole user){
        JerseyRequestBuilder<ClientRole> builder =
                new JerseyRequestBuilder<ClientRole>(credentials, ClientRole.class);
        builder
                .setPath("/roles")
                .setPath(rolename);
        return builder.put(user);
    }

    public OperationResult<ClientRole> delete(){
        JerseyRequestBuilder<ClientRole> builder =
                new JerseyRequestBuilder<ClientRole>(credentials, ClientRole.class);
        builder
                .setPath("/roles")
                .setPath(rolename);
        return builder.delete();
    }

}
