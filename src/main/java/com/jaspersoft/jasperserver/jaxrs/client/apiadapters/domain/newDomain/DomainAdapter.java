package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain;

import com.jaspersoft.jasperserver.dto.domain.ClientSimpleDomain;
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


    public OperationResult<ClientSimpleDomain> create(ClientSimpleDomain domain) {
        JerseyRequest<ClientSimpleDomain> request = buildRequest();
        request.setContentType(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domain+{mime}"));
        return request.post(domain);
    }


    public OperationResult<ClientSimpleDomain> get() {
        JerseyRequest<ClientSimpleDomain> request = buildRequest();
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domain+{mime}"));
        return request.get();
    }


    public OperationResult<ClientSimpleDomain> update(ClientSimpleDomain domain) {
        JerseyRequest<ClientSimpleDomain> request = buildRequest();
        request.setContentType(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/repository.domain+{mime}"));
        return request.put(domain);
    }

    public OperationResult<ClientSimpleDomain> delete() {
        return buildRequest().delete();
    }


    protected JerseyRequest<ClientSimpleDomain> buildRequest() {
        JerseyRequest<ClientSimpleDomain> jerseyRequest = JerseyRequest.buildRequest(
                sessionStorage,
                ClientSimpleDomain.class,
                new String[]{"/resources", uri},
                new DefaultErrorHandler()
        );
        return jerseyRequest;
    }


}
