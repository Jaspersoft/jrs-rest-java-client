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
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;

/**
 * Part of the Report Services API. Exposes queries that you can run on the
 * client side.
 *
 * @author Alexander Krasnyanskiy
 */
public class QueryExecutorService extends AbstractAdapter {

    public QueryExecutorService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    /**
     * to pass 'resourceURI' param use a simple string with first slash (e.g. '/PathToResource'
     * but not 'PathToResource' or 'PathToResource/')
     */
    public QueryExecutorAdapter query(Query query, String resourceURI) {
        return new QueryExecutorAdapter(sessionStorage, query, resourceURI);
    }
}
