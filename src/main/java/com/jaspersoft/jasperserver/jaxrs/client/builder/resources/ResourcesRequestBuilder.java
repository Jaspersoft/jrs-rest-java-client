package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class ResourcesRequestBuilder extends JerseyRequestBuilder<ClientResource> {

    public ResourcesRequestBuilder(SessionStorage sessionStorage, Class<? extends ClientResource> responseClass) {
        super(sessionStorage, responseClass);
        this.operationResultType = ResourcesOperationResult.class.getName();
    }

}
