package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.HashMap;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.Response;

import static java.util.Arrays.asList;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainContextOperationResult<T> extends OperationResult<T> {

    private String uuId;
    private T context;
    private Class<T> contextClass;
    private String contextMimeType;
    private Class<PresentationGroupElement> metadataClass = PresentationGroupElement.class;
    private String metadataMimeType;
    private MultivaluedHashMap<String, String> params = new MultivaluedHashMap<>();
    private ContextService service;
    private static HashMap<Class, String> mimeTypes = new HashMap<>();

    static {
        mimeTypes.put(ClientResourceLookup.class, ContextMediaTypes.RESOURCE_LOOKUP_JSON);
        mimeTypes.put(ClientDomain.class, ContextMediaTypes.DOMAIN_JSON);
        mimeTypes.put(ClientSemanticLayerDataSource.class, ContextMediaTypes.DOMAIN_DATA_SOURCE_JSON);
    }


    public DomainContextOperationResult(Response response,
                                        Class<?> entityClass,
                                        ContextService service) {
        super(response, (Class<? extends T>) entityClass);
        this.contextClass = (Class<T>) entityClass;
        this.context = super.getEntity();
        contextMimeType = mimeTypes.get(contextClass);
        this.service = service;
        extractUuid();
    }

    public T getEntity() {
        return super.getEntity();
    }

    public DomainContextOperationResult<T> addParam(String key, String value) {
        params.add(key, value);
        return this;
    }

    public DomainContextOperationResult<T> addParam(String key, String... value) {
        params.addAll(key, asList(value));
        return this;
    }

    protected void extractUuid() {
        String location = response.getHeaderString("Location");
        uuId = location.substring(location.lastIndexOf("/") + 1);
    }

    protected String completeMetadataMimeType(String contextMimeType) {
        return contextMimeType.replace("+", ".metadata+");
    }

    public OperationResult<ClientMultiLevelQueryResultData> executeQuery(ClientMultiLevelQuery query) {

        OperationResult<ClientMultiLevelQueryResultData> queryOperationResult;
        try {
            queryOperationResult = service
                    .context(uuId)
                    .executeQuery(query);
        } catch (ResourceNotFoundException e) {
            response = service.context(contextClass, contextMimeType).create(context).getResponse();
            extractUuid();
            queryOperationResult = service
                    .context(uuId)
                    .executeQuery(query);
        }
        return queryOperationResult;
    }


    public OperationResult<PresentationGroupElement> getMetadata() {
        metadataMimeType = completeMetadataMimeType(contextMimeType);
        OperationResult<PresentationGroupElement> metadata;
        try {
            metadata = service
                    .context(uuId, metadataClass, metadataMimeType).addParameters(params)
                    .metadata();
        } catch (ResourceNotFoundException e) {
            response = service.context(contextClass, contextMimeType).create(this.context).getResponse();
            extractUuid();
            metadata = service
                    .context(uuId, metadataClass, metadataMimeType).addParameters(params).metadata();
        }
        return metadata;
    }

}
