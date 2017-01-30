package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainContextService extends AbstractAdapter {

    public DomainContextService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleDomainContextAdapter<ClientResourceLookup> context(String contextUri) {
        return new SingleDomainContextAdapter<>(sessionStorage,
                new ClientResourceLookup().setUri(contextUri),
                ClientResourceLookup.class,
                ContextMediaTypes.RESOURCE_LOOKUP_JSON);
    }

    public SingleDomainContextAdapter<ClientDomain> context(ClientDomain context) {
        return new SingleDomainContextAdapter<>(sessionStorage,
                context,
                ClientDomain.class,
                ContextMediaTypes.DOMAIN_JSON);
    }

    public SingleDomainContextAdapter<ClientSemanticLayerDataSource> context(ClientSemanticLayerDataSource context) {
        return new SingleDomainContextAdapter<ClientSemanticLayerDataSource>(sessionStorage,
                context,
                ClientSemanticLayerDataSource.class,
                ContextMediaTypes.DOMAIN_DATA_SOURCE_JSON);
    }


}