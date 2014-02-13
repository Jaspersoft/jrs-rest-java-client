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

package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourcesTypeResolverUtil;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;

public class OperationResult<T> {

    private Response response;
    private Class<? extends T> entityClass;
    private ErrorDescriptor error;

    private T entity;

    public OperationResult(Response response, Class<? extends T> entityClass) {
        this.response = response;
        if (entityClass.isAssignableFrom(ClientResource.class)){
            entityClass =
                   (Class<? extends T>) ResourcesTypeResolverUtil.getClassForMime(response.getHeaderString("Content-Type"));
        }
        this.entityClass = entityClass;
        if (response.getStatus() == 500 || response.getStatus() == 400)
            error = response.readEntity(ErrorDescriptor.class);
    }

    public T getEntity() {
        if (response.getStatus() == 404)
            return null;

        try {
            if (entity == null)
                entity = response.readEntity(entityClass);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public Response getResponse() {
        return response;
    }

    public ErrorDescriptor getError() {
        return error;
    }

    public String getSessionId() {
        Map<String, NewCookie> cookies = response.getCookies();
        NewCookie jsessionid;

        if (cookies != null &&
                (jsessionid = cookies.get("JSESSIONID")) != null)
            return jsessionid.getValue();
        else
            return null;
    }



}
