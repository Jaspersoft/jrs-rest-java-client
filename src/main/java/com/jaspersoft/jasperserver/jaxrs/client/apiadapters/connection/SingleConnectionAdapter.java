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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connection;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class SingleConnectionAdapter extends AbstractAdapter {

    private final String uuid;

    public SingleConnectionAdapter(SessionStorage sessionStorage, String uuid) {
        super(sessionStorage);
        this.uuid = uuid;
    }

    public <ConnectionType> OperationResult<ConnectionType> get(Class<ConnectionType> connectionClass){
        return buildRequest(sessionStorage, connectionClass, new String[]{"/connections", uuid}).get();
    }

    public <ConnectionType> OperationResult<ConnectionType> update(Class<ConnectionType> connectionClass,
                                                                   ConnectionType connection){
        JerseyRequestBuilder<ConnectionType> builder =
                buildRequest(sessionStorage, connectionClass, new String[]{"/connections", uuid});
        builder.setContentType(ConnectionTypeResolverUtil.getMimeType(connectionClass));

        return builder.put(connection);
    }

    public OperationResult delete(){
        return buildRequest(sessionStorage, Object.class, new String[]{"/connections", uuid}).delete();
    }
}
