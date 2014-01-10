package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;

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
