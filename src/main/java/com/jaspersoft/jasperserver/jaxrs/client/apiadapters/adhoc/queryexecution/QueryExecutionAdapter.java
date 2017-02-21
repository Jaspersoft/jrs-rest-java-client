package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
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
public class QueryExecutionAdapter<T extends ClientQueryResultData> extends AbstractAdapter {

    private String serviceURI = "queryExecutions";
    private List<String> uri;
    private Class<T> dataSetClass;
    private String contentType;
    private String[] acceptType;
    private MimeType responseFormat = sessionStorage.getConfiguration().getAcceptMimeType();
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
        responseFormat = MimeType.XML;
        return this;
    }


    public QueryExecutionAdapter<T> asJson() {
        responseFormat = MimeType.JSON;
        return this;
    }

    public QueryExecutionAdapter<T> asResultDataSet(String resultMimeType) {
        acceptType = new String[]{resultMimeType};
        dataSetClass = (Class<T>) QueryResultDataMediaType.getResultDataType(resultMimeType);
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

    protected JerseyRequest<T> buildRequest() {

        JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage,
                dataSetClass,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());

        return request;
    }

}
