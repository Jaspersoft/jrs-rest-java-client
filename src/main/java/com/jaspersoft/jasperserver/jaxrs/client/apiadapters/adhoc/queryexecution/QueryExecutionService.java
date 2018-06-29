package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientExecutionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXIS_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXIS_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXIS_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXIS_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_XML;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class QueryExecutionService extends AbstractAdapter {

    public QueryExecutionService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public QueryExecutionAdapter flatQuery() {
        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_MULTI_LEVEL_QUERY_JSON : EXECUTION_MULTI_LEVEL_QUERY_XML,
                ClientFlatQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        FLAT_DATA_JSON : FLAT_DATA_XML);
    }

    public QueryExecutionAdapter multiLevelQuery() {
        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_MULTI_LEVEL_QUERY_JSON : EXECUTION_MULTI_LEVEL_QUERY_XML,
                ClientMultiLevelQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        MULTI_LEVEL_DATA_JSON : MULTI_LEVEL_DATA_XML);
    }

    public QueryExecutionAdapter multiAxisQuery() {
        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_MULTI_AXIS_QUERY_JSON : EXECUTION_MULTI_AXIS_QUERY_XML,
                ClientMultiAxisQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        MULTI_AXIS_DATA_JSON : MULTI_AXIS_DATA_XML);
    }


    public QueryExecutionAdapter providedQuery() {

        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_PROVIDED_QUERY_JSON : EXECUTION_PROVIDED_QUERY_XML,
                ClientQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        new String[]{FLAT_DATA_JSON, MULTI_LEVEL_DATA_JSON, MULTI_AXIS_DATA_JSON} :
                        new String[]{FLAT_DATA_XML, MULTI_LEVEL_DATA_XML, MULTI_AXIS_DATA_XML});
    }

    public OperationResult<ClientExecutionListWrapper> get() {
        return new QueryExecutionAdapter(sessionStorage).getExecutions();
    }

    public QueryExecutionAdapter execution(String uuIUd) {
        return new QueryExecutionAdapter(sessionStorage, uuIUd);
    }


    protected QueryExecutionAdapter adapter(SessionStorage sessionStorage,
                                                   String contentType,
                                                   Class clazz,
                                                   String... acceptType) {
        return new QueryExecutionAdapter(sessionStorage, contentType, clazz, acceptType);
    }

}
