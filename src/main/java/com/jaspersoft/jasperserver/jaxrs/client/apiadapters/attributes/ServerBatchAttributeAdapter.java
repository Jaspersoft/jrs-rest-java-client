package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttributesListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * @author Alex Krasnyanskiy
 */
public class ServerBatchAttributeAdapter extends AbstractAdapter {

    private MultivaluedMap<String, String> params;

    public ServerBatchAttributeAdapter(SessionStorage sessionStorage, Collection<String> attributesNames) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<String, String>();
        for (String attributeName : attributesNames) {
            params.add("name", attributeName);
        }
    }

    public ServerBatchAttributeAdapter(SessionStorage sessionStorage) {
        this(sessionStorage, Collections.<String>emptyList());
    }

    public ServerBatchAttributeAdapter(SessionStorage sessionStorage, String... attributesNames) {
        this(sessionStorage, asList(attributesNames));
    }

    public OperationResult<ServerAttributesListWrapper> get(){
        return request().addParams(params).get();
    }

    public OperationResult<ServerAttributesListWrapper> delete(){
        return request().addParams(params).delete();
    }

    public OperationResult<ServerAttributesListWrapper> createOrUpdate(ServerAttributesListWrapper attributesListWrapper){
        return request().put(attributesListWrapper);
    }

    private JerseyRequest<ServerAttributesListWrapper> request() {
        return JerseyRequest.buildRequest(sessionStorage, ServerAttributesListWrapper.class,
                new String[]{"/attributes"}, new DefaultErrorHandler());
    }
}
