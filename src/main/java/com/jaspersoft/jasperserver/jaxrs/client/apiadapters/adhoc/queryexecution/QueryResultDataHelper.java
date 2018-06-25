package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxisQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;

import java.util.HashMap;
import java.util.Map;

import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.FLAT_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_AXIS_DATA_JSON;
import static com.jaspersoft.jasperserver.dto.executions.QueryResultDataMediaType.MULTI_LEVEL_DATA_JSON;

public class QueryResultDataHelper {

    private static Map<String, Class<? extends ClientQueryResultData>> mediaTypeWithResultDataClassInfo =
            new HashMap<String, Class<? extends ClientQueryResultData>>(){{
                put(FLAT_DATA_JSON, ClientFlatQueryResultData.class);
                put(MULTI_LEVEL_DATA_JSON, ClientMultiLevelQueryResultData.class);
                put(MULTI_AXIS_DATA_JSON, ClientMultiAxisQueryResultData.class);
            }};

    public static Class<? extends ClientQueryResultData> getResultDataType(String access) {
        for (Map.Entry<String, Class<? extends ClientQueryResultData>> entry :
                mediaTypeWithResultDataClassInfo.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(access)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
