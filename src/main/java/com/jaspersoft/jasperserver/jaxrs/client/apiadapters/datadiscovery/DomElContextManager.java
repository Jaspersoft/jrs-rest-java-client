package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.dto.domain.DomElExpressionCollectionContext;
import com.jaspersoft.jasperserver.dto.domain.DomElExpressionContext;
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
public class DomElContextManager extends AbstractAdapter {

    public DomElContextManager(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<DomElExpressionContext> create(DomElExpressionContext expressionContext) {
        return new ContextService(sessionStorage).
                context(DomElExpressionContext.class, ContextMediaTypes.DOM_EL_CONTEXT_JSON).
                create(expressionContext);
    }

    public OperationResult<DomElExpressionCollectionContext> create(DomElExpressionCollectionContext expressionCollectionContext) {
        return new ContextService(sessionStorage).
                context(DomElExpressionCollectionContext.class,
                        ContextMediaTypes.DOM_EL_COLLECTION_CONTEXT_JSON).
                create(expressionCollectionContext);
    }
}
