package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.AbstractClientExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryExecution;
import com.jaspersoft.jasperserver.dto.executions.ClientProvidedQueryExecution;

import java.util.HashMap;
import java.util.Map;

import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_LEVEL_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_MULTI_AXIS_QUERY_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryExecutionsMediaType.EXECUTION_PROVIDED_QUERY_JSON;

public class QueryExecutionHelper {

    private static Map<String, Class<? extends AbstractClientExecution>> mediaTypeWithExecutionClassInfo =
            new HashMap<String, Class<? extends AbstractClientExecution>>(){{
                put(EXECUTION_MULTI_LEVEL_QUERY_JSON, ClientMultiLevelQueryExecution.class);
                put(EXECUTION_MULTI_AXIS_QUERY_JSON, ClientMultiAxisQueryExecution.class);
                put(EXECUTION_PROVIDED_QUERY_JSON, ClientProvidedQueryExecution.class);
            }};

    public static Class<? extends AbstractClientExecution> getClassForMime(String mime) {
        for (Map.Entry<String, Class<? extends AbstractClientExecution>> entry :
                mediaTypeWithExecutionClassInfo.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(mime)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
