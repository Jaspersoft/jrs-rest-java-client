package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttribute;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
public class ServerSingleAttributeAdapter extends AbstractAdapter {

    private String attributeName;

    public ServerSingleAttributeAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public ServerSingleAttributeAdapter(SessionStorage sessionStorage, String attributeName) {
        super(sessionStorage);
        this.attributeName = attributeName;
    }

    public OperationResult<ServerAttribute> get() {
        return request().get();
    }

    public OperationResult<ServerAttribute> delete() {
        return request().delete();
    }

    public OperationResult<ServerAttribute> put(ServerAttribute attribute) {
        attributeName = attribute.getName();
        return request().put(attribute);
    }

    private JerseyRequest<ServerAttribute> request() {
        return JerseyRequest.buildRequest(sessionStorage, ServerAttribute.class,
                new String[]{"/attributes/" + attributeName}, new DefaultErrorHandler());
    }
}
