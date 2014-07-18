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
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

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
        JerseyRequest<RepositoryPermissionListWrapper> request = buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri});
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        return request.put(permissions);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final RepositoryPermissionListWrapper permissions, final Callback<OperationResult<RepositoryPermissionListWrapper>, R> callback) {
        final JerseyRequest<RepositoryPermissionListWrapper> request = buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri});
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(permissions));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public PermissionResourceRequestAdapter param(PermissionResourceParameter resourceParam, String value) {
        params.add(resourceParam.getParamName(), value);
        return this;
    }

    public OperationResult<RepositoryPermissionListWrapper> get(){
        JerseyRequest<RepositoryPermissionListWrapper> request = buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri});
        request.addParams(params);
        return request.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<RepositoryPermissionListWrapper>, R> callback) {
        final JerseyRequest<RepositoryPermissionListWrapper> request = buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri});
        request.addParams(params);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete(){
        return buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri}).delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<RepositoryPermissionListWrapper>, R> callback) {
        final JerseyRequest<RepositoryPermissionListWrapper> request = buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{"/permissions", resourceUri});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
