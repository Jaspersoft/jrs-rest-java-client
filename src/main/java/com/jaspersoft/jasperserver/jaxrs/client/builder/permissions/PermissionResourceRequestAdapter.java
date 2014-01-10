package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class PermissionResourceRequestAdapter {

    private AuthenticationCredentials credentials;
    private String resourceUri;
    private MultivaluedMap<String, String> params;
    private final JerseyRequestBuilder<RepositoryPermissionListWrapper> builder;


    public PermissionResourceRequestAdapter(AuthenticationCredentials credentials, String resourceUri) {
        this.credentials = credentials;
        this.resourceUri = resourceUri;
        params = new MultivaluedHashMap<String, String>();

        builder = new JerseyRequestBuilder<RepositoryPermissionListWrapper>(
                credentials, RepositoryPermissionListWrapper.class);
        builder
                .setPath("/permissions")
                .setPath(resourceUri);
    }

    public SinglePermissionRecipientRequestAdapter permissionRecipient(PermissionRecipient recipient, String name) {
        String protocol = recipient.getProtocol();
        String uri = protocol + ":%2F" + name;
        return new SinglePermissionRecipientRequestAdapter(credentials, resourceUri, uri);
    }

    public OperationResult createOrUpdate(RepositoryPermissionListWrapper permissions) {
        builder.setContentType("application/collection+json");
        return builder.put(permissions);
    }

    public PermissionResourceRequestAdapter param(PermissionResourceParameter resourceParam, String value) {
        params.add(resourceParam.getParamName(), value);
        return this;
    }

    public OperationResult<RepositoryPermissionListWrapper> get(){
        builder.addParams(params);
        return builder.get();
    }

    public OperationResult delete(){
        return builder.delete();
    }

}
