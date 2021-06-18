package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context;

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
public class ContextService extends AbstractAdapter {

    public ContextService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }
    public <C> SingleContextAdapter<C, Object> context(Class<C> contextClass, String contextMimeType) {
        return new SingleContextAdapter<C, Object>(sessionStorage, contextClass, contextMimeType);
    }

    public <C> SingleContextAdapter<C, Object> context(Class<C> contextClass, String contextMimeType, String uuId) {
        return new SingleContextAdapter<C, Object>(sessionStorage, contextClass, contextMimeType, uuId);
    }

    public <M> SingleContextAdapter<Object, M> context(String uuId, Class<M> metadataClass, String metadataMimeType) {
        return new SingleContextAdapter<Object, M>(sessionStorage, uuId, metadataClass, metadataMimeType);
    }

    public <C, M> SingleContextAdapter<C, M> context(Class<C> context, String contextMimeType,
                                                        Class<M> metadataClass,
                                                        String metadataMimeType) {
        return new SingleContextAdapter<C, M>(sessionStorage, context, contextMimeType, metadataClass, metadataMimeType);
    }

    public SingleContextAdapter context(String uuId) {
        return new SingleContextAdapter(sessionStorage, uuId);
    }

}