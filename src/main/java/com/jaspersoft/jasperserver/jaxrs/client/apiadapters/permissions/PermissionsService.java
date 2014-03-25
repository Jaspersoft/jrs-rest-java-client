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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.CommonExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class PermissionsService extends AbstractAdapter {

    public PermissionsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public PermissionResourceRequestAdapter resource(String resourceUri) {
        if ("".equals(resourceUri))
            throw new  IllegalArgumentException("'resourceUri' mustn't be an empty string");
        return new PermissionResourceRequestAdapter(sessionStorage, resourceUri);
    }

    public OperationResult createNew(RepositoryPermission permission){
        return buildRequest(sessionStorage, Object.class, new String[]{"/permissions"}, new CommonExceptionHandler())
                .post(permission);
    }

    public OperationResult createNew(RepositoryPermissionListWrapper permissions) {
        JerseyRequestBuilder builder = buildRequest(sessionStorage, Object.class, new String[]{"/permissions"}, new CommonExceptionHandler());
        builder.setContentType("application/collection+json");
        return builder.post(permissions);
    }

}
