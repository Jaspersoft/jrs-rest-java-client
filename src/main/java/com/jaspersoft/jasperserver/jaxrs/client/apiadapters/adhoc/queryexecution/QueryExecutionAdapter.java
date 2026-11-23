package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientExecutionListWrapper;
import com.jaspersoft.jasperserver.dto.executions.ExecutionStatusObject;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.apache.commons.lang3.StringUtils;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

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
    private Class responseClass;
    private String contentType;
    private String[] acceptType;
    private MimeType responseFormat = sessionStorage.getConfiguration().getAcceptMimeType();
    private MultivaluedHashMap<String, String> params;

    public QueryExecutionAdapter(SessionStorage sessionStorage, String contentType, Class responseClass, String... acceptType) {
        super(sessionStorage);
        this.contentType = contentType;
        this.acceptType = acceptType;
        this.responseClass = responseClass;
        params = new MultivaluedHashMap<>();
        uri = new ArrayList<>();
        uri.add(serviceURI);
    }

    public QueryExecutionAdapter(SessionStorage sessionStorage, String executionId) {
        super(sessionStorage);
        uri = new ArrayList<>();
        uri.add(serviceURI);
        uri.add(executionId);
    }

    public QueryExecutionAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        uri = new ArrayList<>();
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
        responseClass = QueryResultDataHelper.getResultDataType(resultMimeType);
        return this;
    }

    public QueryExecutionAdapter asResultExecution(String resultMimeType) {
        acceptType = new String[]{resultMimeType};
        responseClass = QueryExecutionHelper.getClassForMime(resultMimeType);
        return this;
    }

    public QueryExecutionAdapter asDefaultAccept() {
        acceptType = new String[]{APPLICATION_JSON};
        responseClass = QueryExecutionHelper.getClassForMime(contentType);
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

    public QueryExecutionAdapter withQueryParameter(String name, String value) {
        params.add(name, value);
        return this;
    }

    public <T> OperationResult<T> retrieveData(String executionId) {
        uri.add(executionId);
        uri.add("data");
        JerseyRequest<T> request = buildRequest(responseClass);
        if (params.size() > 0) {
            request.addParams(params);
        }

        request.setAccept(StringUtils.join(acceptType, ","));
        return request.get();
    }

    public OperationResult delete() {
        JerseyRequest request = buildRequest(Object.class);
        return request.delete();
    }

    public OperationResult<ClientExecutionListWrapper> getExecutions() {
        JerseyRequest request = buildRequest(ClientExecutionListWrapper.class);
        return request.get();
    }

    public <T extends AbstractClientExecution> OperationResult<T> get() {
        JerseyRequest request = buildRequest(AbstractClientExecution.class);
        return request.get();
    }

    public OperationResult<ExecutionStatusObject> status() {
        uri.add("status");

        JerseyRequest request = buildRequest(ExecutionStatusObject.class);
        return request.get();
    }

    public <T> OperationResult<T> execute(AbstractClientExecution query) {
        if (query == null) {
            throw new MandatoryParameterNotFoundException("Query must be specified");
        }
        JerseyRequest<T> request = buildRequest(responseClass);
        request.setContentType(contentType);
        if (!(sessionStorage.getConfiguration().getAcceptMimeType() == responseFormat)) {
            for (int i = 0; i < acceptType.length; i++) {
                String type = acceptType[i];
                acceptType[i] = type.replace(type.substring(type.lastIndexOf("+") + 1), responseFormat.toString().toLowerCase());
            }
        }
        request.setAccept(StringUtils.join(acceptType, ","));
        request.addParams(params);
        return request.
                post(query);
    }

    protected <T> JerseyRequest<T> buildRequest(Class<T> responseType) {
        JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage,
                responseType,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());

        return request;
    }

}
