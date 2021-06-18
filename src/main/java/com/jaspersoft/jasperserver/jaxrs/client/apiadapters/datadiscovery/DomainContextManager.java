package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainContextManager {
    private SessionStorage sessionStorage;
    public DomainContextManager(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ClientDomain> create(ClientDomain domain) {
        return new ContextService(sessionStorage).
                context(ClientDomain.class, ContextMediaTypes.DOMAIN_JSON).
                create(domain);
    }

    public OperationResult<PresentationGroupElement> fetchMetadataById(String id) {
        return new ContextService(sessionStorage).context(id,
                PresentationGroupElement.class,
                ContextMediaTypes.DOMAIN_METADATA_JSON).
                metadata();
    }

    public OperationResult<PresentationGroupElement> fetchMetadataByContext(ClientDomain domain) {
        return new ContextService(sessionStorage).
                context(ClientDomain.class,
                        ContextMediaTypes.DOMAIN_JSON,
                        PresentationGroupElement.class,
                        ContextMediaTypes.DOMAIN_METADATA_JSON).
                createAndGetMetadata(domain);
    }
}
