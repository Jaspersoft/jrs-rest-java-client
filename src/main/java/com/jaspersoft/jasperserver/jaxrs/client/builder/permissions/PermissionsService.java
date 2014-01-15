package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class PermissionsService {

    private final SessionStorage sessionStorage;

    public PermissionsService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public PermissionResourceRequestAdapter resource(String name) {
        return new PermissionResourceRequestAdapter(sessionStorage, name);
    }

    public OperationResult create(RepositoryPermission permission){
        JerseyRequestBuilder builder = new JerseyRequestBuilder(sessionStorage, Object.class);
        builder.setPath("permissions");
        return builder.post(permission);
    }

    public OperationResult create(RepositoryPermissionListWrapper permissions) {
        JerseyRequestBuilder builder = new JerseyRequestBuilder(sessionStorage, Object.class);
        builder.setPath("permissions");
        builder.setContentType("application/collection+json");
        return builder.post(permissions);
    }

}
