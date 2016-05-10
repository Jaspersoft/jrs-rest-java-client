/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution;

import com.jaspersoft.jasperserver.dto.executions.ClientFlatQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiAxesQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientMultiLevelQueryResultData;
import com.jaspersoft.jasperserver.dto.executions.ClientQueryResultData;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.enums.QueryResultDataMediaType;
import java.util.HashMap;
import java.util.Map;

public class QueryResultDataTypeResolverUtil {

    private static final Map<String, Class<? extends ClientQueryResultData>> mimeToClassMap;
    public static final String RESOURCE_MIME_TYPE = "+{mime}";

    static {
        mimeToClassMap = new HashMap<String, Class<? extends ClientQueryResultData>>() {{
            put(QueryResultDataMediaType.FLAT_DATA_TYPE, ClientFlatQueryResultData.class);
            put(QueryResultDataMediaType.MULTI_AXES_DATA_TYPE, ClientMultiAxesQueryResultData.class);
            put(QueryResultDataMediaType.MULTI_LEVEL_DATA_TYPE, ClientMultiLevelQueryResultData.class);
        }};
    }

    public static Class<? extends ClientQueryResultData> getClassForMime(String mime) {
        return mimeToClassMap.get(mime.substring(0, mime.indexOf("+")) + RESOURCE_MIME_TYPE);
    }

}
