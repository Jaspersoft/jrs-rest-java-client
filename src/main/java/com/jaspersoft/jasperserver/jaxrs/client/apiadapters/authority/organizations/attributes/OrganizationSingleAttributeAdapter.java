package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ClientTenantAttribute;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
public class OrganizationSingleAttributeAdapter extends AbstractAdapter {

    private final String organizationId;
    private String attributeName;

    public OrganizationSingleAttributeAdapter(SessionStorage sessionStorage, String organizationId) {
        this(sessionStorage, organizationId, null);
    }

    public OrganizationSingleAttributeAdapter(SessionStorage sessionStorage, String organizationId,
                                              String attributeName) {
        super(sessionStorage);
        this.organizationId = organizationId;
        this.attributeName = attributeName;
    }

    public OperationResult<ClientTenantAttribute> get() {
        return request().get();
    }

    public OperationResult<ClientTenantAttribute> createOrUpdate(ClientTenantAttribute attribute) {
        attributeName = attribute.getName();
        return request().put(attribute);
    }

    public OperationResult<ClientTenantAttribute> delete() {
        return request().delete();
    }

    private JerseyRequest<ClientTenantAttribute> request() {
        return JerseyRequest.buildRequest(sessionStorage, ClientTenantAttribute.class,
                new String[]{"/organizations/" + organizationId + "/attributes/" + attributeName},
                new DefaultErrorHandler());
    }
}
