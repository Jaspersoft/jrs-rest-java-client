package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class BatchRolesRequestAdapter {

    private final JerseyRequestBuilder<RolesListWrapper> builder;
    private AuthenticationCredentials credentials;

    private MultivaluedMap<String, String> params;


    public BatchRolesRequestAdapter(AuthenticationCredentials credentials) {
        this.credentials = credentials;
        this.builder = new JerseyRequestBuilder<RolesListWrapper>(credentials, RolesListWrapper.class);
        this.builder.setPath("roles");
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchRolesRequestAdapter param(RolesParameter rolesParam, String value){
        params.add(rolesParam.getParamName(), value);
        return this;
    }

    public OperationResult<RolesListWrapper> get(){
        builder.addParams(params);
        return builder.get();
    }

}
