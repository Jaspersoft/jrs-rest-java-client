package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.ContextService;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.IllegalParameterValueException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleDomainContextAdapter<C> extends AbstractAdapter {

    private C context;
    private Class<C> contextClass;
    private String contextMimeType;
    private ContextService service;

    public SingleDomainContextAdapter(SessionStorage sessionStorage,
                                      C context,
                                      Class<C> contextClass,
                                      String contextMimeType) {
        super(sessionStorage);
        this.context = context;
        if (!isContextTypeValid(context)) throw new IllegalParameterValueException();
        this.contextClass = contextClass;
        this.contextMimeType = contextMimeType;
        service = new ContextService(sessionStorage);
    }
    public DomainContextOperationResult<C> create() {
        OperationResult<C> operationResult = service.context(contextClass, contextMimeType).create(this.context);
        return new DomainContextOperationResult<C>(operationResult.getResponse(),
                operationResult.getEntity().getClass(), service);
    }

    protected <T> Boolean isContextTypeValid(T context) {
        if (context == null) {
            throw new MandatoryParameterNotFoundException("context is null");
        }
        return (context instanceof ClientResourceLookup ||
                context instanceof ClientDomain ||
                context instanceof ClientSemanticLayerDataSource);
    }
}
