package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
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
public class QueryExecutionAdapter<T> extends AbstractAdapter {

    private String serviceURI = "queryExecutions";
    private Class<T> dataSetClass;
    private String contentType;
    private String acceptType;

    public QueryExecutionAdapter(SessionStorage sessionStorage, String contentType, String acceptType, Class<T> dataSetClass) {
        super(sessionStorage);
        this.contentType = contentType;
        this.acceptType = acceptType;
        this.dataSetClass = dataSetClass;
    }

    public OperationResult<T> execute(AbstractClientExecution query){
        if (query == null) {
            throw new MandatoryParameterNotFoundException("Query must be specified");
        }
        JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage,
                dataSetClass,
                new String[]{serviceURI},
                new DefaultErrorHandler());
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), contentType));
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), acceptType));
        return request.
                post(query);
    }
}
