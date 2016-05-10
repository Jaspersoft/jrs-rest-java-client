package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.apache.commons.lang3.StringUtils;

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
                toCorrectContentMime(EXECUTION_MULTI_LEVEL_QUERY_TYPE),
                toCorrectAcceptMime(FLAT_DATA_TYPE),
                ClientFlatQueryResultData.class);
    }

    public QueryExecutionAdapter<ClientMultiLevelQueryResultData> multiLevelQuery() {
        return this.adapter(sessionStorage,
                toCorrectContentMime(EXECUTION_MULTI_LEVEL_QUERY_TYPE),
                toCorrectAcceptMime(MULTI_LEVEL_DATA_TYPE),
                ClientMultiLevelQueryResultData.class);
    }

    public QueryExecutionAdapter<ClientMultiAxesQueryResultData> multiAxesQuery() {
        return this.adapter(sessionStorage,
                toCorrectContentMime(EXECUTION_MULTI_AXES_QUERY_TYPE),
                toCorrectAcceptMime(MULTI_AXES_DATA_TYPE),
                ClientMultiAxesQueryResultData.class);
    }


    public QueryExecutionAdapter<ClientQueryResultData> providedQuery() {

        return this.adapter(sessionStorage,
                toCorrectContentMime(EXECUTION_PROVIDED_QUERY_TYPE),
                StringUtils.join(new String[]{
                        toCorrectAcceptMime(FLAT_DATA_TYPE),
                        toCorrectAcceptMime(MULTI_LEVEL_DATA_TYPE),
                        toCorrectAcceptMime(MULTI_AXES_DATA_TYPE)}, ", "),
                ClientQueryResultData.class);
    }

    protected <P> QueryExecutionAdapter<P> adapter(SessionStorage sessionStorage,
                                                   String contentType,
                                                   String acceptType,
                                                   Class<P> clazz) {
        return new QueryExecutionAdapter<P>(sessionStorage, contentType, acceptType, clazz);
    }

    protected String toCorrectContentMime(String rawType) {
        return MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), rawType);
    }

    protected String toCorrectAcceptMime(String rawType) {
        return MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), rawType);
    }

}
