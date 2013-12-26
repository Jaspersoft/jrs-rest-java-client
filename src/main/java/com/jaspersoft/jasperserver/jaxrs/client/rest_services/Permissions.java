package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionRecipient;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionsRequestBuilder;

import javax.ws.rs.core.Response;

public class Permissions {

    public static PermissionsRequestBuilder<RepositoryPermissionListWrapper> resource(String name) {
        PermissionsRequestBuilder<RepositoryPermissionListWrapper> builder =
                new PermissionsRequestBuilder<RepositoryPermissionListWrapper>(RepositoryPermissionListWrapper.class);
        builder.setPath(name);
        return builder;
    }

    public static Response createPermission(RepositoryPermission permission) {
        PermissionsRequestBuilder<RepositoryPermission> builder =
                new PermissionsRequestBuilder<RepositoryPermission>(RepositoryPermission.class);
        return builder.post(permission);
    }

    public static void main(String[] args) {
        RepositoryPermission permission =
                Permissions.resource("datasources").permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER")
                        .addParam("effectivePermissions", "true").get();
        System.out.println(permission);
        System.out.println();

        RepositoryPermissionListWrapper permissionListWrapper =
                Permissions.resource("datasources").get();
        System.out.println(permissionListWrapper);
    }

}
