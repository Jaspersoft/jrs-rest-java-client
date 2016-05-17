package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType.FLAT_DATA_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType.MULTI_AXES_DATA_TYPE;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE;

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
                EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                ClientFlatQueryResultData.class,
                FLAT_DATA_TYPE);
    }

    public QueryExecutionAdapter<ClientMultiLevelQueryResultData> multiLevelQuery() {
        return this.adapter(sessionStorage,
                EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                ClientMultiLevelQueryResultData.class,
                MULTI_LEVEL_DATA_TYPE);
    }

    public QueryExecutionAdapter<ClientMultiAxesQueryResultData> multiAxesQuery() {
        return this.adapter(sessionStorage,
                EXECUTION_MULTI_AXES_QUERY_TYPE,
                ClientMultiAxesQueryResultData.class,
                MULTI_AXES_DATA_TYPE);
    }


    public QueryExecutionAdapter<ClientQueryResultData> providedQuery() {

        return this.adapter(sessionStorage,
                EXECUTION_PROVIDED_QUERY_TYPE,
                ClientQueryResultData.class,
                        FLAT_DATA_TYPE,
                        MULTI_LEVEL_DATA_TYPE,
                        MULTI_AXES_DATA_TYPE);
    }

    protected <P> QueryExecutionAdapter<P> adapter(SessionStorage sessionStorage,
                                                   String contentType,
                                                   Class<P> clazz,
                                                   String... acceptType) {
        return new QueryExecutionAdapter<P>(sessionStorage, contentType, clazz, acceptType);
    }

}
