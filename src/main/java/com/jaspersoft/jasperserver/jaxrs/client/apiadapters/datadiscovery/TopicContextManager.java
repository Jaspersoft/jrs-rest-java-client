package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.domain.ResourceGroupElement;
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
public class TopicContextManager {
    private SessionStorage sessionStorage;
    public TopicContextManager(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ClientReportUnit> create(ClientReportUnit reportUnit) {
        return new ContextService(sessionStorage).
                context(ClientReportUnit.class,
                        ContextMediaTypes.REPORT_UNIT_JSON).
                create(reportUnit);
    }

    public OperationResult<ResourceGroupElement> fetchMetadataById(String id) {
        return new ContextService(sessionStorage).
                context(id,
                ResourceGroupElement.class,
                ContextMediaTypes.REPORT_UNIT_METADATA_JSON).
                metadata();
    }

    public OperationResult<ResourceGroupElement> fetchMetadataByContext(ClientReportUnit reportUnit) {
        return new ContextService(sessionStorage).
                context(ClientReportUnit.class,
                        ContextMediaTypes.REPORT_UNIT_JSON,
                        ResourceGroupElement.class,
                        ContextMediaTypes.REPORT_UNIT_METADATA_JSON).
                createAndGetMetadata(reportUnit);
    }
}
