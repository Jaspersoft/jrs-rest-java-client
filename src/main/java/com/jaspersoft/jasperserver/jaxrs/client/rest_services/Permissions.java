package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionsRequestBuilder;

import javax.ws.rs.core.Response;

public class Permissions {

    public static PermissionsRequestBuilder<RepositoryPermissionListWrapper> resource(String name) {
        PermissionsRequestBuilder<RepositoryPermissionListWrapper> builder =
                new PermissionsRequestBuilder<RepositoryPermissionListWrapper>(RepositoryPermissionListWrapper.class);
        builder.setPath(name);
        return builder;
    }

    public static Response createPermissions(RepositoryPermission permission) {
        PermissionsRequestBuilder<RepositoryPermission> builder =
                new PermissionsRequestBuilder<RepositoryPermission>(RepositoryPermission.class);
        return builder.post(permission);
    }

    public static Response createPermissions(RepositoryPermissionListWrapper permission) {
        PermissionsRequestBuilder<RepositoryPermissionListWrapper> builder =
                new PermissionsRequestBuilder<RepositoryPermissionListWrapper>(RepositoryPermissionListWrapper.class);
        return builder.post(permission);
    }

}
