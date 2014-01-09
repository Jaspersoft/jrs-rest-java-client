package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.client.WebTarget;

public class PermissionsRequestBuilder<ResponseType>
        extends JerseyRequestBuilder<ResponseType> {

    private static final String URI = "/permissions";

    public PermissionsRequestBuilder(AuthenticationCredentials credentials, Class<ResponseType> responseClass) {
        super(credentials, responseClass);
        this.setPath(URI);
    }

    private PermissionsRequestBuilder(WebTarget webTarget, Class<ResponseType> responseClass) {
        super(credentials, responseClass);
        this.setTarget(webTarget);
    }

    public JerseyRequestBuilder<RepositoryPermission> permissionRecipient(PermissionRecipient recipient, String name) {
        String protocol = recipient.getProtocol();
        String uri = protocol + ":%2F" + name;
        this.addMatrixParam("recipient", uri);
        return new PermissionsRequestBuilder<RepositoryPermission>(this.getPath(), RepositoryPermission.class);
    }

    @Override
    public <RequestType> OperationResult<ResponseType> put(RequestType entity) {
        if (entity instanceof RepositoryPermissionListWrapper)
            setContentType("application/collection+json");
        return super.put(entity);
    }

    @Override
    public <RequestType> OperationResult<ResponseType> post(RequestType entity) {
        if (entity instanceof RepositoryPermissionListWrapper)
            setContentType("application/collection+json");
        return super.post(entity);
    }
}
