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

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.CommonExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class PermissionResourceRequestAdapter extends AbstractAdapter {

    private final String resourceUri;
    private final MultivaluedMap<String, String> params;


    public PermissionResourceRequestAdapter(SessionStorage sessionStorage, String resourceUri) {
        super(sessionStorage);
        this.resourceUri = resourceUri;
        params = new MultivaluedHashMap<String, String>();
    }

    public SinglePermissionRecipientRequestAdapter permissionRecipient(PermissionRecipient recipient, String name) {
        String protocol = recipient.getProtocol();
        String uri = protocol + ":%2F" + name;
        return new SinglePermissionRecipientRequestAdapter(sessionStorage, resourceUri, uri);
    }

    public OperationResult<RepositoryPermissionListWrapper> createOrUpdate(RepositoryPermissionListWrapper permissions) {
        JerseyRequestBuilder<RepositoryPermissionListWrapper> builder =
                buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri}, new CommonExceptionHandler());
        builder.setContentType("application/collection+json");
        return builder.put(permissions);
    }

    public PermissionResourceRequestAdapter param(PermissionResourceParameter resourceParam, String value) {
        params.add(resourceParam.getParamName(), value);
        return this;
    }

    public OperationResult<RepositoryPermissionListWrapper> get(){
        JerseyRequestBuilder<RepositoryPermissionListWrapper> builder =
                buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri}, new CommonExceptionHandler());
        builder.addParams(params);
        return builder.get();
    }

    public OperationResult delete(){
        return buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri}, new CommonExceptionHandler())
                .delete();
    }

}
