package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain;

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainAdapter extends AbstractAdapter {

    private String uri;

    public DomainAdapter(SessionStorage sessionStorage, String uri) {
        super(sessionStorage);
        this.uri = uri;
    }


    public OperationResult<ClientDomain> create(ClientDomain domain) {
        JerseyRequest<ClientDomain> request = buildRequest();
        request.setContentType(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domain+{mime}"));
        return request.post(domain);
    }


    public OperationResult<ClientDomain> get() {
        JerseyRequest<ClientDomain> request = buildRequest();
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domain+{mime}"));
        return request.get();
    }


    public OperationResult<ClientDomain> update(ClientDomain domain) {
        JerseyRequest<ClientDomain> request = buildRequest();
        request.setContentType(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domain+{mime}"));
        return request.put(domain);
    }

    public OperationResult<ClientDomain> delete() {
        return buildRequest().delete();
    }


    protected JerseyRequest<ClientDomain> buildRequest() {
        JerseyRequest<ClientDomain> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                ClientDomain.class,
                new String[]{"resources", uri},
                new DefaultErrorHandler()
        );
        return jerseyRequest;
    }


}
