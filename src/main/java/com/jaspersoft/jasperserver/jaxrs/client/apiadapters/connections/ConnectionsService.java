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
 */
public class ConnectionsService extends AbstractAdapter {

    public ConnectionsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleConnectionsAdapter connection(String uuId) {
        return this.connection(Object.class, null, Object.class, null, uuId);
    }

    public SingleConnectionsAdapter connection(Class connectionClass, String connectionMimeType, String uuId) {
        return this.connection(connectionClass, connectionMimeType, Object.class, null, uuId);
    }

    public SingleConnectionsAdapter connection(Class connectionClass, String connectionMimeType) {
        return this.connection(connectionClass, connectionMimeType, Object.class, null, null);
    }

    public <C, M> SingleConnectionsAdapter<C, M> connection(Class<C> connection,
                                                           String connectionMimeType,
                                                           Class<M> metadata,
                                                           String metadataMimeType,
                                                           String uuId) {


        return new SingleConnectionsAdapter<C, M>(sessionStorage, connection,
                connectionMimeType,
                metadata,
                metadataMimeType,
                uuId);
    }

}
