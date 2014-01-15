package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class PermissionResourceRequestAdapter {

    private SessionStorage sessionStorage;
    private String resourceUri;
    private MultivaluedMap<String, String> params;
    private final JerseyRequestBuilder<RepositoryPermissionListWrapper> builder;


    public PermissionResourceRequestAdapter(SessionStorage sessionStorage, String resourceUri) {
        this.sessionStorage = sessionStorage;
        this.resourceUri = resourceUri;
        params = new MultivaluedHashMap<String, String>();

        builder = new JerseyRequestBuilder<RepositoryPermissionListWrapper>(
                sessionStorage, RepositoryPermissionListWrapper.class);
        builder
                .setPath("/permissions")
                .setPath(resourceUri);
    }

    public SinglePermissionRecipientRequestAdapter permissionRecipient(PermissionRecipient recipient, String name) {
        String protocol = recipient.getProtocol();
        String uri = protocol + ":%2F" + name;
        return new SinglePermissionRecipientRequestAdapter(sessionStorage, resourceUri, uri);
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
