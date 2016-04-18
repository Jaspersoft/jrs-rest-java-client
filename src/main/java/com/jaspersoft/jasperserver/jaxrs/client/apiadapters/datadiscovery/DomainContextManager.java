package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.DataIslandsContainer;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.ConnectionsService;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ConnectionMediaType;
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

    public OperationResult<ClientSemanticLayerDataSource> create(ClientSemanticLayerDataSource domain) {
        return new ConnectionsService(sessionStorage).
                connection(ClientSemanticLayerDataSource.class, ConnectionMediaType.DOMAIN_DATA_SOURCE_TYPE).
                create(domain);
    }

    public OperationResult<DataIslandsContainer> fetchMetadataById(String id) {
        return new ConnectionsService(sessionStorage).connection(id,
                DataIslandsContainer.class,
                ConnectionMediaType.DOMAIN_METADATA_TYPE).
                metadata();
    }

    public OperationResult<DataIslandsContainer> fetchMetadataByContext(ClientSemanticLayerDataSource domain) {
        return new ConnectionsService(sessionStorage).
                connection(ClientSemanticLayerDataSource.class,
                        ConnectionMediaType.DOMAIN_DATA_SOURCE_TYPE,
                        DataIslandsContainer.class,
                        ConnectionMediaType.DOMAIN_METADATA_TYPE).
                createAndGetMetadata(domain);
    }
}
