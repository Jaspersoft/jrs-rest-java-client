package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.resources.SqlExecutionRequest;
import com.jaspersoft.jasperserver.dto.resources.domain.PresentationGroupElement;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DerivedTableContextManager extends AbstractAdapter {

    public DerivedTableContextManager(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<PresentationGroupElement> execute(SqlExecutionRequest sqlExecutionRequest) {
        return new ContextService(sessionStorage).
                context(SqlExecutionRequest.class,
                        ContextMediaTypes.SQL_EXECUTION_JSON,
                        PresentationGroupElement.class,
                        ContextMediaTypes.DATASET_METADATA_JSON).
                createAndGetMetadata(sqlExecutionRequest);
    }

}
