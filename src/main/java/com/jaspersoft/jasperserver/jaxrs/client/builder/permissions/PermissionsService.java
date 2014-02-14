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

package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class PermissionsService {

    private final SessionStorage sessionStorage;

    public PermissionsService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public PermissionResourceRequestAdapter resource(String name) {
        return new PermissionResourceRequestAdapter(sessionStorage, name);
    }

    public OperationResult createNew(RepositoryPermission permission){
        return buildRequest(sessionStorage, Object.class, new String[]{"/permissions"})
                .post(permission);
    }

    public OperationResult createNew(RepositoryPermissionListWrapper permissions) {
        JerseyRequestBuilder builder = buildRequest(sessionStorage, Object.class, new String[]{"/permissions"});
        builder.setContentType("application/collection+json");
        return builder.post(permissions);
    }

}
