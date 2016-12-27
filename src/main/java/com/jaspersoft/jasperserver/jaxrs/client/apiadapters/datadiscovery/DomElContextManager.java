package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.domain.DomElExpressionContext;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomElContextManager extends AbstractAdapter {

    public DomElContextManager(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<DomElExpressionContext> create(DomElExpressionContext expression) {
        return new ContextService(sessionStorage).
                context(DomElExpressionContext.class, "application/contexts.domElExpressionContext+json").
                create(expression);
    }
}
