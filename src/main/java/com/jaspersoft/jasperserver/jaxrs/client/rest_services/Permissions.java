package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionsRequestBuilder;

public class Permissions {

    public static <RequestType> PermissionsRequestBuilder<RequestType,
            RepositoryPermissionListWrapper> resource(String name) {

        PermissionsRequestBuilder<RequestType, RepositoryPermissionListWrapper> builder =
                new PermissionsRequestBuilder<RequestType,
                        RepositoryPermissionListWrapper>(RepositoryPermissionListWrapper.class);
        builder.setPath(name);
        return builder;
    }

    public static OperationResult createPermissions(RepositoryPermission permission) {
        PermissionsRequestBuilder<RepositoryPermission, RepositoryPermission> builder =
                new PermissionsRequestBuilder<RepositoryPermission,
                        RepositoryPermission>(RepositoryPermission.class);
        return builder.post(permission);
    }

    public static OperationResult createPermissions(RepositoryPermissionListWrapper permission) {
        PermissionsRequestBuilder<RepositoryPermissionListWrapper, RepositoryPermissionListWrapper> builder =
                new PermissionsRequestBuilder<RepositoryPermissionListWrapper,
                        RepositoryPermissionListWrapper>(RepositoryPermissionListWrapper.class);
        return builder.post(permission);
    }

}
