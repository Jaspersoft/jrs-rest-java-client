package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryExecutionsMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryType;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;

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
                QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                QueryResultDataMediaType.FLAT_DATA_TYPE,
                ClientFlatQueryResultData.class);
    }

    public QueryExecutionAdapter<ClientMultiLevelQueryResultData> multiLevelQuery() {
        return this.adapter(sessionStorage,
                QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
                ClientMultiLevelQueryResultData.class);
    }

    public QueryExecutionAdapter<ClientMultiAxesQueryResultData> multiAxesQuery() {
        return this.adapter(sessionStorage,
                QueryExecutionsMediaType.EXECUTION_MULTI_AXES_QUERY_TYPE,
                QueryResultDataMediaType.MULTI_AXES_DATA_TYPE,
                ClientMultiAxesQueryResultData.class);
    }


    public QueryExecutionAdapter<? extends ClientQueryResultData> providedQuery(QueryType queryType) {

        if (queryType == null) {
            throw new MandatoryParameterNotFoundException("Query type must be specified");
        }
        switch (queryType) {
            case FLAT_QUERY:
                return this.adapter(sessionStorage,
                        QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                        QueryResultDataMediaType.FLAT_DATA_TYPE,
                        ClientFlatQueryResultData.class);
            case MULTI_LEVEL_QUERY:
                return this.adapter(sessionStorage,
                        QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                        QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE,
                        ClientMultiLevelQueryResultData.class);

            default:
                return this.adapter(sessionStorage,
                    QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_TYPE,
                    QueryResultDataMediaType.MULTI_AXES_DATA_TYPE,
                    ClientMultiAxesQueryResultData.class);

        }
    }

    protected <P> QueryExecutionAdapter<P> adapter(SessionStorage sessionStorage,
                                                   String contentType,
                                                   String acceptType,
                                                   Class<P> clazz) {
        return new QueryExecutionAdapter<P>(sessionStorage, contentType, acceptType, clazz);
    }

}
