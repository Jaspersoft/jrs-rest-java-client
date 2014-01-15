package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class RolesService {

    private final SessionStorage sessionStorage;

    public RolesService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public SingleRoleRequestAdapter rolename(String rolename) {
        return new SingleRoleRequestAdapter(sessionStorage, rolename);
    }

    public BatchRolesRequestAdapter allRoles() {
        return new BatchRolesRequestAdapter(sessionStorage);
    }

}
