package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public class PermissionsService {

    private final AuthenticationCredentials credentials;

    public PermissionsService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public PermissionResourceRequestAdapter resource(String name) {
        return new PermissionResourceRequestAdapter(credentials, name);
    }

    public OperationResult create(RepositoryPermission permission){
        JerseyRequestBuilder builder = new JerseyRequestBuilder(credentials, Object.class);
        builder.setPath("permissions");
        return builder.post(permission);
    }

    public OperationResult create(RepositoryPermissionListWrapper permissions) {
        JerseyRequestBuilder builder = new JerseyRequestBuilder(credentials, Object.class);
        builder.setPath("permissions");
        builder.setContentType("application/collection+json");
        return builder.post(permissions);
    }

}
