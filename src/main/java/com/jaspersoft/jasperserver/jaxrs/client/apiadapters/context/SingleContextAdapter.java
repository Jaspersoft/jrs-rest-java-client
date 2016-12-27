package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context;

import com.jaspersoft.jasperserver.dto.connection.LfsConnection;
import com.jaspersoft.jasperserver.dto.domain.DomElExpressionContext;
import com.jaspersoft.jasperserver.dto.resources.ClientCustomDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientJndiJdbcDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

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
    private String contextMimeType;
    private Class<M> metadataClass;
    private String metadataMimeType;

    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextMimeType,
                                Class<M> metadataClass,
                                String metadataMimeType,
                                String uuId) {
        super(sessionStorage);
        this.uuId = uuId;
        this.contextClass = contextClass;
        this.contextMimeType = contextMimeType;
        this.metadataClass = metadataClass;
        this.metadataMimeType = metadataMimeType;
    }
    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextMimeType) {
        this(sessionStorage, contextClass, contextMimeType, null, null, null);
    }

    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextMimeType, String uuId) {
        this(sessionStorage, contextClass, contextMimeType, null, null, uuId);
    }

    public SingleContextAdapter(SessionStorage sessionStorage, String uuId, Class<M> metadataClass,
                                String metadataMimeType) {
        this(sessionStorage, null, null, metadataClass, metadataMimeType, uuId);
    }
    public SingleContextAdapter(SessionStorage sessionStorage, Class<C> contextClass,
                                String contextMimeType,
                                Class<M> metadataClass,
                                String metadataMimeType) {
        this(sessionStorage, contextClass, contextMimeType, metadataClass, metadataMimeType, null);
    }
    public SingleContextAdapter(SessionStorage sessionStorage, String uuId) {
        this(sessionStorage, (Class<C>)Object.class, null, null, null, uuId);
    }
    @SuppressWarnings("unchecked")
    public OperationResult<C> create(C context) {
        if (!isContextTypeValid(context)) {
            throw new IllegalArgumentException("Unsupported contextClass type");
        }
        JerseyRequest<C> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , contextClass
                , new String[]{SERVICE_URI}
                , new DefaultErrorHandler());
        if (contextMimeType != null) {
            jerseyRequest
                    .setContentType(contextMimeType);
        }
        return jerseyRequest.post(context);
    }

    public OperationResult<C> get() {
        JerseyRequest<C> jerseyRequest = buildRequest();
        return jerseyRequest.get();
    }

    public OperationResult<C> update(C context) {
        if (!isContextTypeValid(context)) {
            throw new IllegalArgumentException("Unsupported contextClass type");
        }
        JerseyRequest<C> jerseyRequest = buildRequest();
        if (contextMimeType != null) {
            jerseyRequest
                    .setContentType(contextMimeType + "+{mime}");
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
        return jerseyRequest.get();
    }

    public OperationResult<M> createAndGetMetadata(C context) {
        if (!isContextTypeValid(context)) {
            throw new IllegalArgumentException("Unsupported contextClass type");
        }
        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , metadataClass
                , new String[]{SERVICE_URI}
                , new DefaultErrorHandler());
        jerseyRequest
                .setContentType(contextMimeType);
        jerseyRequest
                .setAccept(metadataMimeType);
        return jerseyRequest.post(context);
    }


    protected <T> Boolean isContextTypeValid(T context) {
        if (context == null) {
            throw new MandatoryParameterNotFoundException("context is null");
        }
        return (context instanceof LfsConnection ||
                context instanceof ClientDomain ||
                context instanceof ClientSemanticLayerDataSource ||
                context instanceof ClientCustomDataSource ||
                context instanceof ClientJndiJdbcDataSource ||
                context instanceof ClientJdbcDataSource ||
                context instanceof ClientReportUnit ||
                context instanceof DomElExpressionContext);
    }
}
