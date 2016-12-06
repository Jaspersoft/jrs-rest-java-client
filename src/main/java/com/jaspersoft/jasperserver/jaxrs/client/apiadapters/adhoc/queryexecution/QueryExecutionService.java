package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_XML;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXES_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXES_DATA_XML;
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

    public QueryExecutionAdapter<ClientFlatQueryResultData> flatQuery() {
        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_MULTI_LEVEL_QUERY_JSON : EXECUTION_MULTI_LEVEL_QUERY_XML,
                ClientFlatQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        FLAT_DATA_JSON : FLAT_DATA_XML);
    }

    public QueryExecutionAdapter<ClientMultiLevelQueryResultData> multiLevelQuery() {
        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_MULTI_LEVEL_QUERY_JSON : EXECUTION_MULTI_LEVEL_QUERY_XML,
                ClientMultiLevelQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        MULTI_LEVEL_DATA_JSON : MULTI_LEVEL_DATA_XML);
    }

    public QueryExecutionAdapter<ClientMultiAxesQueryResultData> multiAxesQuery() {
        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_MULTI_AXES_QUERY_JSON : EXECUTION_MULTI_AXES_QUERY_XML,
                ClientMultiAxesQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        MULTI_AXES_DATA_JSON : MULTI_AXES_DATA_XML);
    }


    public QueryExecutionAdapter<ClientQueryResultData> providedQuery() {

        return this.adapter(sessionStorage,
                (sessionStorage.getConfiguration().getContentMimeType().equals(MimeType.JSON)) ?
                        EXECUTION_PROVIDED_QUERY_JSON : EXECUTION_PROVIDED_QUERY_XML,
                ClientQueryResultData.class,
                (sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON)) ?
                        new String[]{FLAT_DATA_JSON, MULTI_LEVEL_DATA_JSON, MULTI_AXES_DATA_JSON} :
                        new String[]{FLAT_DATA_XML, MULTI_LEVEL_DATA_XML, MULTI_AXES_DATA_XML});
    }

    protected <P> QueryExecutionAdapter<P> adapter(SessionStorage sessionStorage,
                                                   String contentType,
                                                   Class<P> clazz,
                                                   String... acceptType) {
        return new QueryExecutionAdapter<P>(sessionStorage, contentType, clazz, acceptType);
    }

}
