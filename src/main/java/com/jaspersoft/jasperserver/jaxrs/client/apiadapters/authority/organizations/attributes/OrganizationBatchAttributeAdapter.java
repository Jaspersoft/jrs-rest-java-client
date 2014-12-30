package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.TenantAttributesListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
public class OrganizationBatchAttributeAdapter extends AbstractAdapter {

    private String organizationId;
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();

    public OrganizationBatchAttributeAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        this.organizationId = organizationId;
    }

    public OrganizationBatchAttributeAdapter(SessionStorage sessionStorage,
                                             String organizationId,
                                             Collection<String> attributesNames) {
        this(sessionStorage, organizationId);
        for (String attributeName : attributesNames) {
            this.params.add("name", attributeName);
        }
    }

    public OrganizationBatchAttributeAdapter(SessionStorage sessionStorage,
                                             String organizationId,
                                             String... attributesNames) {
        this(sessionStorage, organizationId);
        for (String attributeName : attributesNames) {
            this.params.add("name", attributeName);
        }
    }

    public OperationResult<TenantAttributesListWrapper> get() {
        return request().addParams(params).get();
    }

    public OperationResult<TenantAttributesListWrapper> createOrUpdate(TenantAttributesListWrapper entity) {
        return request().put(entity);
    }

    public OperationResult<TenantAttributesListWrapper> delete() {
        return request().addParams(params).delete();
    }

    private JerseyRequest<TenantAttributesListWrapper> request() {
        return JerseyRequest.buildRequest(sessionStorage, TenantAttributesListWrapper.class,
                new String[]{"/organizations/" + organizationId + "/attributes"},
                new DefaultErrorHandler());
    }
}
