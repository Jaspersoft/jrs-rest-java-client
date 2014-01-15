package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class SinglePermissionRecipientRequestAdapter {

    private final JerseyRequestBuilder<RepositoryPermission> builder;


    public SinglePermissionRecipientRequestAdapter(SessionStorage sessionStorage,
                                                   String resourceUri, String recipient) {

        this.builder = new JerseyRequestBuilder<RepositoryPermission>(sessionStorage, RepositoryPermission.class);
        this.builder
                .setPath("/permissions")
                .setPath(resourceUri)
                .addMatrixParam("recipient", recipient);
    }

    public OperationResult<RepositoryPermission> get(){
        return builder.get();
    }

    public OperationResult<RepositoryPermission> createOrUpdate(RepositoryPermission permission){
        return builder.put(permission);
    }

    public OperationResult<RepositoryPermission> delete(){
        return builder.delete();
    }

}
