package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class SingleRoleRequestAdapter {

    private final JerseyRequestBuilder<ClientRole> builder;


    public SingleRoleRequestAdapter(SessionStorage sessionStorage, String rolename) {
        this.builder = new JerseyRequestBuilder<ClientRole>(sessionStorage, ClientRole.class);
        this.builder
                .setPath("/roles")
                .setPath(rolename);
    }

    public OperationResult<ClientRole> get(){
        return builder.get();
    }

    public OperationResult<ClientRole> createOrUpdate(ClientRole user){
        return builder.put(user);
    }

    public OperationResult<ClientRole> delete(){
        return builder.delete();
    }

}
