package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType;
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
public class QueryExecutionAdapter extends AbstractAdapter {


    private String serviceURI = "queryExecutions";
    private List<String> uri;
    private Class dataSetClass;
    private String contentType;
    private String[] acceptType;
    private MimeType responseFormat = sessionStorage.getConfiguration().getAcceptMimeType();
    private MultivaluedHashMap<String, String> params;

    public QueryExecutionAdapter(SessionStorage sessionStorage, String contentType, Class dataSetClass, String... acceptType) {
        super(sessionStorage);
        this.contentType = contentType;
        this.acceptType = acceptType;
        this.dataSetClass = dataSetClass;
        params = new MultivaluedHashMap<String, String>();
        uri = new ArrayList<String>();
        uri.add(serviceURI);
    }

    public QueryExecutionAdapter asXml() {
        responseFormat = MimeType.XML;
        return this;
    }

    public QueryExecutionAdapter asJson() {
        responseFormat = MimeType.JSON;
        return this;
    }

    public QueryExecutionAdapter asResultDataSet(String resultMimeType) {
        acceptType = new String[]{resultMimeType};
        dataSetClass = QueryResultDataMediaType.getResultDataType(resultMimeType);
        return this;
    }

    public QueryExecutionAdapter offset(Integer offset) {
        params.add("offset", offset.toString());
        return this;
    }

    public QueryExecutionAdapter pageSize(Integer pageSize) {
        params.add("pageSize", pageSize.toString());
        return this;
    }

    public <T> OperationResult<T> retrieveData(String executionId) {
        uri.add(executionId);
        uri.add("data");
        JerseyRequest<T> request = buildRequest(dataSetClass);
        if (params.size() > 0) {
            request.addParams(params);
        }

        request.setAccept(StringUtils.join(acceptType, ","));
        return request.get();
    }

    public <T> OperationResult<T> deleteExecution(String executionId) {
        uri.add(executionId);
        JerseyRequest<T> request = buildRequest(dataSetClass);
        return request.delete();
    }

    public <T> OperationResult<T> execute(AbstractClientExecution query) {
        if (query == null) {
            throw new MandatoryParameterNotFoundException("Query must be specified");
        }
        JerseyRequest<T> request = buildRequest(dataSetClass);
        request.setContentType(contentType);
        if (!(sessionStorage.getConfiguration().getAcceptMimeType() == responseFormat)) {
            for (int i = 0; i < acceptType.length; i++) {
                String type = acceptType[i];
                acceptType[i] = type.replace(type.substring(type.lastIndexOf("+") + 1), responseFormat.toString().toLowerCase());
            }
        }
        request.setAccept(StringUtils.join(acceptType, ","));
        return request.
                post(query);
    }

    protected <T> JerseyRequest<T> buildRequest(Class<T> dataSetClass) {

        JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage,
                dataSetClass,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());

        return request;
    }

}
