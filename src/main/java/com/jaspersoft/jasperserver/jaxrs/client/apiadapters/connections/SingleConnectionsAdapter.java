package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.query.SingleQueryAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
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
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.SingleContextAdapter}
 */
public class SingleConnectionsAdapter<C, M> extends AbstractAdapter {

    public static final String SERVICE_URI = "connections";

    private String uuId;
    private Class<C> connectionClass;
    private String connectionMimeType;
    private Class<M> metadataClass;
    private String metadataMimeType;

    public SingleConnectionsAdapter(SessionStorage sessionStorage, Class<C> connectionClass,
                                    String connectionMimeType,
                                    Class<M> metadataClass,
                                    String metadataMimeType,
                                    String uuId) {
        super(sessionStorage);
        this.uuId = uuId;
        this.connectionClass = connectionClass;
        this.connectionMimeType = connectionMimeType;
        this.metadataClass = metadataClass;
        this.metadataMimeType = metadataMimeType;
    }

    public SingleConnectionsAdapter(SessionStorage sessionStorage, Class<C> connectionClass,
                                    String connectionMimeType) {
        this(sessionStorage, connectionClass, connectionMimeType, null, null, null);
    }

    public SingleConnectionsAdapter(SessionStorage sessionStorage, Class<C> connectionClass,
                                    String connectionMimeType, String uuId) {
        this(sessionStorage, connectionClass, connectionMimeType, null, null, uuId);
    }

    public SingleConnectionsAdapter(SessionStorage sessionStorage, String uuId, Class<M> metadataClass,
                                    String metadataMimeType) {
        this(sessionStorage, null, null, metadataClass, metadataMimeType, uuId);
    }

    public SingleConnectionsAdapter(SessionStorage sessionStorage, Class<C> connectionClass,
                                    String connectionMimeType,
                                    Class<M> metadataClass,
                                    String metadataMimeType) {
        this(sessionStorage, connectionClass, connectionMimeType, metadataClass, metadataMimeType, null);
    }

    public SingleConnectionsAdapter(SessionStorage sessionStorage, String uuId) {
        this(sessionStorage, (Class<C>) Object.class, null, null, null, uuId);
    }

    @SuppressWarnings("unchecked")
    public OperationResult<C> create(C connection) {
        if (connection == null) {
            throw new MandatoryParameterNotFoundException("Connection is null");
        }
        JerseyRequest<C> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , connectionClass
                , new String[]{SERVICE_URI}
                , new DefaultErrorHandler());
        if (connectionMimeType != null) {
            jerseyRequest
                    .setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(),
                            connectionMimeType + "+{mime}"));
        }
        return jerseyRequest.post(connection);
    }

    public OperationResult<C> get() {
        JerseyRequest<C> jerseyRequest = buildRequest();
        return jerseyRequest.get();
    }

    public OperationResult<C> update(C connection) {
        if (connection == null) {
            throw new MandatoryParameterNotFoundException("Connection is null");
        }
        JerseyRequest<C> jerseyRequest = buildRequest();
        if (connectionMimeType != null) {
            jerseyRequest
                    .setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration()
                            , connectionMimeType + "+{mime}"));
        }
        return jerseyRequest.put(connection);
    }

    public OperationResult delete() {
        return buildRequest().delete();
    }

    protected JerseyRequest<C> buildRequest() {
        if (uuId == null || uuId.isEmpty()) {
            throw new MandatoryParameterNotFoundException("Uuid of the connection must be specified");
        }
        return JerseyRequest.buildRequest(this.sessionStorage
                , connectionClass
                , new String[]{SERVICE_URI, uuId}
                , new DefaultErrorHandler());
    }

    public OperationResult<M> metadata() {
        if (uuId == null || uuId.isEmpty()) {
            throw new MandatoryParameterNotFoundException("Uuid of the connection must be specified");
        }
        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                metadataClass,
                new String[]{SERVICE_URI, uuId, "metadata"},
                new DefaultErrorHandler()
        );
        jerseyRequest.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration()
                , metadataMimeType + "+{mime}"));
        return jerseyRequest.get();
    }

    public OperationResult<M> createAndGetMetadata(C connection) {
        if (connection == null) {
            throw new MandatoryParameterNotFoundException("Connection is null");
        }
        JerseyRequest<M> jerseyRequest = JerseyRequest.buildRequest(this.sessionStorage
                , metadataClass
                , new String[]{SERVICE_URI}
                , new DefaultErrorHandler());
        jerseyRequest
                .setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration()
                        , connectionMimeType + "+{mime}"));
        jerseyRequest
                .setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration()
                        , metadataMimeType + "+{mime}"));
        return jerseyRequest.post(connection);
    }


    public <T> SingleQueryAdapter<T> query(String query, Class<T> queryResponseClass) {
        return new SingleQueryAdapter<T>(sessionStorage, uuId, query, queryResponseClass);
    }
}
