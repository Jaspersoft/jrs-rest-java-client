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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttribute;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
public class ServerSingleAttributeAdapter extends AbstractAdapter {

    private String attributeName;

    public ServerSingleAttributeAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public ServerSingleAttributeAdapter(SessionStorage sessionStorage, String attributeName) {
        super(sessionStorage);
        this.attributeName = attributeName;
    }

    public OperationResult<ServerAttribute> get() {
        return request().get();
    }

    public OperationResult<ServerAttribute> delete() {
        return request().delete();
    }

    public OperationResult<ServerAttribute> createOrUpdate(ServerAttribute attribute) {
        attributeName = attribute.getName();
        return request().put(attribute);
    }

    private JerseyRequest<ServerAttribute> request() {
        return JerseyRequest.buildRequest(sessionStorage, ServerAttribute.class,
                new String[]{"/attributes/" + attributeName}, new DefaultErrorHandler());
    }
}
