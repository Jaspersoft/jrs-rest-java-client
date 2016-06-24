package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain;

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "resources";
    private List<String> path = new ArrayList<String>();

    public DomainAdapter(SessionStorage sessionStorage, String uri) {
        super(sessionStorage);
        this.path.add(SERVICE_URI);
        this.path.addAll(asList(uri.split("/")));
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
                path.toArray(new String[path.size()]),
                new DefaultErrorHandler()
        );
        return jerseyRequest;
    }


}
