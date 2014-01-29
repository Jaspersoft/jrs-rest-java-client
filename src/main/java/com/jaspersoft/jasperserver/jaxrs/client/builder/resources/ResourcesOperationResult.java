package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.core.Response;

public class ResourcesOperationResult extends OperationResult<ClientResource> {

    public ResourcesOperationResult(Response response, Class<? extends ClientResource> entityClass) {
        super(response, entityClass);
        /*this.entityClass =
                ResourcesTypeResolverUtil.getClassForMime(response.getHeaderString("Content-Type"));*/
    }

}
