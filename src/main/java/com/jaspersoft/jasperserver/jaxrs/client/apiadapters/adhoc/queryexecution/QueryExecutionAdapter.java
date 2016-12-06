package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedHashMap;
import org.apache.commons.lang3.StringUtils;

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
    private List<String> uri;
    private Class<T> dataSetClass;
    private String contentType;
    private String[] acceptType;
    private MultivaluedHashMap<String, String> params;

    public QueryExecutionAdapter(SessionStorage sessionStorage, String contentType, Class<T> dataSetClass, String... acceptType) {
        super(sessionStorage);
        this.contentType = contentType;
        this.acceptType = acceptType;
        this.dataSetClass = dataSetClass;
        params = new MultivaluedHashMap<String, String>();
        uri = new ArrayList<String>();
        uri.add(serviceURI);
    }

    public QueryExecutionAdapter<T> asXml() {
        if (!sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.XML)) {
            for (int i = 0; i < acceptType.length; i++) {
                acceptType[i] = acceptType[i].replace("json", "xml");
            }
        }
        return this;
    }

    public QueryExecutionAdapter<T> asJson() {
        if (!sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) {
            for (int i = 0; i < acceptType.length; i++) {
                acceptType[i] = acceptType[i].replace("xml", "json");
            }
        }
        return this;
    }

    public QueryExecutionAdapter<T> offset(Integer offset) {
        params.add("offset", offset.toString());
        return this;
    }

    public QueryExecutionAdapter<T> pageSize(Integer pageSize) {
        params.add("pageSize", pageSize.toString());
        return this;
    }

    public OperationResult<T> retrieveData(String executionId) {
        uri.add(executionId);
        uri.add("data");
        JerseyRequest<T> request = buildRequest();
        if (params.size() > 0) {
            request.addParams(params);
        }
        request.setAccept(StringUtils.join(acceptType, ","));
        return request.get();
    }

    public OperationResult<T> deleteExecution(String executionId) {
        uri.add(executionId);
        JerseyRequest<T> request = buildRequest();
        return request.delete();
    }

    public OperationResult<T> execute(AbstractClientExecution query) {
        if (query == null) {
            throw new MandatoryParameterNotFoundException("Query must be specified");
        }
        JerseyRequest<T> request = buildRequest();
        request.setContentType(contentType);
        request.setAccept(StringUtils.join(acceptType, ","));
        return request.
                post(query);
    }

    protected JerseyRequest<T> buildRequest() {

        JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage,
                dataSetClass,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());

        return request;
    }
}
