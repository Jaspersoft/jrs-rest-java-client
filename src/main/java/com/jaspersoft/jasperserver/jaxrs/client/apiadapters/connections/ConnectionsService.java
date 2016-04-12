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

    public <C> SingleConnectionAdapter<C, Object> connection(Class<C> connectionClass, String connectionMimeType) {
        return new SingleConnectionAdapter<C, Object>(sessionStorage, connectionClass, connectionMimeType);
    }

    public <C> SingleConnectionAdapter<C, Object> connection(Class<C> connectionClass, String connectionMimeType, String uuId) {
        return new SingleConnectionAdapter<C, Object>(sessionStorage, connectionClass, connectionMimeType, uuId);
    }

    public <M> SingleConnectionAdapter<Object, M> connection(String uuId, Class<M> metadataClass, String metadataMimeType) {
        return new SingleConnectionAdapter<Object, M>(sessionStorage, uuId, metadataClass, metadataMimeType);
    }

   public <C, M> SingleConnectionAdapter<C, M> connection(Class<C> connection, String connectionMimeType,
                                                           Class<M> metadataClass,
                                                           String metadataMimeType) {
       return new SingleConnectionAdapter<C, M>(sessionStorage, connection, connectionMimeType, metadataClass, metadataMimeType);
   }

   public SingleConnectionAdapter<Object, Object> connection(String uuId) {
       return new SingleConnectionAdapter<Object, Object>(sessionStorage, uuId);
   }


}
