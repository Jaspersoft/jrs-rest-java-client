package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 *
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService}
 */
public class ConnectionsService extends AbstractAdapter {

    public ConnectionsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }
    public <C> SingleConnectionsAdapter<C, Object> connection(Class<C> connectionClass, String connectionMimeType) {
        return new SingleConnectionsAdapter<C, Object>(sessionStorage, connectionClass, connectionMimeType);
    }

    public <C> SingleConnectionsAdapter<C, Object> connection(Class<C> connectionClass, String connectionMimeType, String uuId) {
        return new SingleConnectionsAdapter<C, Object>(sessionStorage, connectionClass, connectionMimeType, uuId);
    }

    public <M> SingleConnectionsAdapter<Object, M> connection(String uuId, Class<M> metadataClass, String metadataMimeType) {
        return new SingleConnectionsAdapter<Object, M>(sessionStorage, uuId, metadataClass, metadataMimeType);
    }

    public <C, M> SingleConnectionsAdapter<C, M> connection(Class<C> connection, String connectionMimeType,
                                                           Class<M> metadataClass,
                                                           String metadataMimeType) {
        return new SingleConnectionsAdapter<C, M>(sessionStorage, connection, connectionMimeType, metadataClass, metadataMimeType);
    }

    public SingleConnectionsAdapter connection(String uuId) {
        return new SingleConnectionsAdapter(sessionStorage, uuId);
    }

}