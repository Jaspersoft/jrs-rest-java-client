package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.connection.metadata.PartialMetadataOptions;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleContextAdapter<C, M> extends AbstractAdapter {

    public static final String SERVICE_URI = "contexts";

    private String uuId;
    private Class<C> contextClass;
    private String contextContentMimeType;
    private Class<M> metadataClass;
    private String metadataMimeType;
    private MultivaluedHashMap<String, String> params;

    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextContentMimeType,
                                Class<M> metadataClass,
                                String metadataMimeType,
                                String uuId) {
        super(sessionStorage);
        this.uuId = uuId;
        this.contextClass = contextClass;
        this.contextContentMimeType = contextContentMimeType;
        this.metadataClass = metadataClass;
        this.metadataMimeType = metadataMimeType;
        params = new MultivaluedHashMap<>();
    }

    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextContentMimeType) {
        this(sessionStorage, contextClass, contextContentMimeType, null, null, null);
    }

    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextContentMimeType, String uuId) {
        this(sessionStorage, contextClass, contextContentMimeType, null, null, uuId);
    }

    public SingleContextAdapter(SessionStorage sessionStorage, String uuId, Class<M> metadataClass,
                                String metadataMimeType) {
        this(sessionStorage, null, null, metadataClass, metadataMimeType, uuId);
    }

    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextContentMimeType,
                                Class<M> metadataClass,
                                String metadataMimeType) {
        this(sessionStorage, contextClass, contextContentMimeType, metadataClass, metadataMimeType, null);
    }

    public SingleContextAdapter(SessionStorage sessionStorage, String uuId) {
        this(sessionStorage, (Class<C>) Object.class, null, null, null, uuId);
    }

    public SingleContextAdapter<C, M> addParameter(String key, String value) {
        params.add(key, UrlUtils.encode(value));
        return this;
    }

    public SingleContextAdapter<C, M> addParameter(String key, String... value) {
        params.addAll(key, UrlUtils.encode(Arrays.asList(value)));
        return this;
    }

    public SingleContextAdapter<C, M> addParameters(MultivaluedHashMap<String, String> values) {
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            params.addAll(entry.getKey(), UrlUtils.encode(entry.getValue()));
        }
        return this;
    }

    public SingleContextAdapter<C, M> addParameter(String key, List<String> value) {
        params.addAll(key, UrlUtils.encode(value));
        return this;
    }

    @SuppressWarnings("unchecked")
    public OperationResult<C> create(C context) {
        if (context == null) {
            throw new MandatoryParameterNotFoundException("Context is null");
        }
        JerseyRequest<C> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , contextClass
                , new String[]{SERVICE_URI}
                , new DefaultErrorHandler());
        if (contextContentMimeType != null) {
            jerseyRequest
                    .setContentType(contextContentMimeType);
        }
        return jerseyRequest.post(context);
    }

    public OperationResult<C> get() {
        JerseyRequest<C> jerseyRequest = buildRequest();
        return jerseyRequest.get();
    }

    public OperationResult<C> update(C context) {
        JerseyRequest<C> jerseyRequest = buildRequest();
        if (contextContentMimeType != null) {
            jerseyRequest
                    .setContentType(contextContentMimeType);
        }
        return jerseyRequest.put(context);
    }

    public OperationResult delete() {
        return buildRequest().delete();
    }

    protected JerseyRequest<C> buildRequest() {
        if (uuId == null || uuId.isEmpty()) {
            throw new MandatoryParameterNotFoundException("Uuid of the context must be specified");
        }
        return JerseyRequest.buildRequest(this.sessionStorage
                , contextClass
                , new String[]{SERVICE_URI, uuId}
                , new DefaultErrorHandler());
    }

    public OperationResult<M> metadata() {
        if (uuId == null || uuId.isEmpty()) {
            throw new MandatoryParameterNotFoundException("Uuid of the context must be specified");
        }
        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                metadataClass,
                new String[]{SERVICE_URI, uuId, "metadata"},
                new DefaultErrorHandler()
        );
        jerseyRequest.setAccept(metadataMimeType);
        if (!params.isEmpty()) jerseyRequest.addParams(params);
        return jerseyRequest.get();
    }

    public OperationResult<M> partialMetadata() {
        if (uuId == null || uuId.isEmpty()) {
            throw new MandatoryParameterNotFoundException("Uuid of the context must be specified");
        }

        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                metadataClass,
                new String[]{SERVICE_URI, uuId, "metadata"},
                new DefaultErrorHandler()
        );
        jerseyRequest.setContentType(metadataMimeType);
        return jerseyRequest.post(params);
    }

    public OperationResult<M> partialMetadata(PartialMetadataOptions options) {
        if (uuId == null || uuId.isEmpty()) {
            throw new MandatoryParameterNotFoundException("Uuid of the context must be specified");
        }

        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                metadataClass,
                new String[]{SERVICE_URI, uuId, "metadata"},
                new DefaultErrorHandler()
        );
        jerseyRequest.setContentType(metadataMimeType);
        return jerseyRequest.post(options);
    }

    public OperationResult<M> createAndGetMetadata(C context) {
        if (context == null) {
            throw new MandatoryParameterNotFoundException("Context is null");
        }
        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , metadataClass
                , new String[]{SERVICE_URI}
                , new DefaultErrorHandler());
        jerseyRequest
                .setContentType(contextContentMimeType);
        jerseyRequest
                .setAccept(metadataMimeType);
        if (!params.isEmpty()) jerseyRequest.addParams(params);
        return jerseyRequest.post(context);
    }

    public OperationResult<ClientMultiLevelQueryResultData> executeQuery(ClientMultiLevelQuery query) {

        JerseyRequest<ClientMultiLevelQueryResultData> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , ClientMultiLevelQueryResultData.class
                , new String[]{SERVICE_URI, uuId, "data"});
        jerseyRequest
                .setContentType("application/adhoc.multiLevelQuery+json");
        return jerseyRequest.post(query);
    }
}