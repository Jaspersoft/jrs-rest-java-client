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

package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import javax.ws.rs.core.Response;

public abstract class OperationResult<T> {

    protected Response response;
    protected Class<? extends T> entityClass;

    protected T entity;
    protected String serializedContent;

    public OperationResult(Response response, Class<? extends T> entityClass) {
        this.response = response;
        response.bufferEntity();
        this.entityClass = entityClass;
    }

    public T getEntity() {
        try {
            if (entity == null) {
                entity = response.readEntity(entityClass);
            }
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public String getSerializedContent() {
        try {
            if (serializedContent == null)
                serializedContent = response.readEntity(String.class);
            return serializedContent;
        } catch (Exception e) {
            return null;
        }
    }

    public Response getResponse() {
        return response;
    }

    public Class<? extends T> getEntityClass() {
        return entityClass;
    }
}
