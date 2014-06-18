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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.QueryResult;

/**
 * This class is used for executing queries and retrieving a result of execution
 * as QueryResult entity.
 *
 * @author Krasnyanksiy.Alexander
 */
public class QueryExecutorAdapter extends AbstractAdapter {
    private final StringBuilder uri;
    private final Query query;

    public QueryExecutorAdapter(SessionStorage sessionStorage, Query query, String uri) {
        super(sessionStorage);
        this.uri = new StringBuilder(uri);
        this.query = query;
    }

    /**
     * Support method for building Jersey request
     *
     * @return JerseyRequest instance
     */
    private JerseyRequest<QueryResult> buildRequest() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                QueryResult.class,
                new String[]{
                        uri.insert(0, "/queryExecutor").toString()
                },
                new DefaultErrorHandler()
        );
    }

    /**
     * The contentType parameter must be "application/xml" only according to
     * JasperReports Server Web Services Guide (3.6 The v2/queryExecutor Service).
     *
     * @return OperationResult
     */
    public OperationResult<QueryResult> execute() {
        JerseyRequest<QueryResult> req = buildRequest();

        /**
         * For now JSON format is unsupported on v2/queryExecutor Service as ContentType
         * therefore ContentType was hardcoded to XML.
         */
        req.setContentType("application/xml");

        // Just uncomment code below when JSON ContentType will be available on v2/queryExecutor Service
        // request handler.

        //String contentType = MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/{mime}");

        return req.post(query);
    }
}
