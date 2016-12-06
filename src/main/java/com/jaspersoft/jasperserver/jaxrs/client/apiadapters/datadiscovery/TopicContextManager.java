package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
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
public class TopicContextManager {
    private SessionStorage sessionStorage;
    public TopicContextManager(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ClientReportUnit> create(ClientReportUnit reportUnit) {
        return new ConnectionsService(sessionStorage).
                connection(ClientReportUnit.class,
                        ConnectionMediaType.REPORT_UNIT_TYPE).
                create(reportUnit);
    }

    public OperationResult<ResourceGroupElement> fetchMetadataById(String id) {
        return new ConnectionsService(sessionStorage).connection(id,
                ResourceGroupElement.class,
                ConnectionMediaType.REPORT_UNIT_METADATA_TYPE).
                metadata();
    }

    public OperationResult<ResourceGroupElement> fetchMetadataByContext(ClientReportUnit reportUnit) {
        return new ConnectionsService(sessionStorage).
                connection(ClientReportUnit.class,
                        ConnectionMediaType.REPORT_UNIT_TYPE,
                        ResourceGroupElement.class,
                        ConnectionMediaType.REPORT_UNIT_METADATA_TYPE).
                createAndGetMetadata(reportUnit);
    }
}
