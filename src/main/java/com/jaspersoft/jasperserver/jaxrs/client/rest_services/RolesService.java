package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles.BatchRolesRequestAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles.SingleRoleRequestAdapter;

public class RolesService {

    private final AuthenticationCredentials credentials;

    public RolesService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public SingleRoleRequestAdapter rolename(String rolename) {
        return new SingleRoleRequestAdapter(credentials, rolename);
    }

    public BatchRolesRequestAdapter allRoles() {
        return new BatchRolesRequestAdapter(credentials);
    }

}
