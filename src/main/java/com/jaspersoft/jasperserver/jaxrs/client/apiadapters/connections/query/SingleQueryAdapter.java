package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.query;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
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
public class SingleQueryAdapter<T> extends AbstractAdapter {

    private String query;
    private String uuId;
    private Class<T> queryResponseClass;

    public SingleQueryAdapter(SessionStorage sessionStorage,
                              String uuId,
                              String query,
                              Class queryResponceClass) {
        super(sessionStorage);
        this.query = query;
        this.uuId = uuId;
        this.queryResponseClass = queryResponceClass;
    }


    public <T> OperationResult<T> execute() {
        return (OperationResult<T>) buildRequest().setContentType("text/plain").post(query);
    }

    @SuppressWarnings("unchecked")
    public <T> OperationResult<T> resultSetMetadata() {
        return (OperationResult<T>) buildRequest().
                setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration()
                        , "application/dataset.metadata+{mime}")).
                post(query);
    }

    @SuppressWarnings("unchecked")
    protected <T> JerseyRequest<T> buildRequest() {
        if (this.uuId == null) {
            throw new MandatoryParameterNotFoundException("Uuid of the connection must be specified");
        }
        return (JerseyRequest<T>) JerseyRequest.buildRequest(sessionStorage,
                queryResponseClass,
                new String[]{"/connections", uuId, "data"},
                new DefaultErrorHandler());
    }

}
