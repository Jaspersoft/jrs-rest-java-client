package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class BatchResourcesAdapter {

    private final JerseyRequestBuilder<ClientResourceListWrapper> builder;

    public BatchResourcesAdapter(SessionStorage sessionStorage) {
        builder = new JerseyRequestBuilder<ClientResourceListWrapper>(sessionStorage, ClientResourceListWrapper.class);
        builder.setPath("resources");
    }

    public BatchResourcesAdapter parameter(ResourceSearchParameter param, String value){
        builder.addParam(param.getName(), value);
        return this;
    }

    public OperationResult<ClientResourceListWrapper> search(){
        return builder.get();
    }

    public OperationResult delete(){
        return builder.delete();
    }

}
