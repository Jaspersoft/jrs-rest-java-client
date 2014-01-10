package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public class SinglePermissionRecipientRequestAdapter {

    private final JerseyRequestBuilder<RepositoryPermission> builder;
    private AuthenticationCredentials credentials;


    public SinglePermissionRecipientRequestAdapter(AuthenticationCredentials credentials,
                                                   String resourceUri, String recipient) {
        this.credentials = credentials;
        this.builder = new JerseyRequestBuilder<RepositoryPermission>(credentials, RepositoryPermission.class);
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
