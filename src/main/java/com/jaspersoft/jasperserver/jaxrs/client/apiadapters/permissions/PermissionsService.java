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
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class PermissionsService extends AbstractAdapter {

    public static final String SERVICE_URI = "/permissions";
    public String resourceUri;

    public PermissionsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public PermissionsService forResource(String resourceUri) {
        if (resourceUri.isEmpty()) {
            throw new IllegalArgumentException("'resourceUri' mustn't be an empty string");
        }
        this.resourceUri = resourceUri;
        return this;
    }

    /**
     * @deprecated Use forResource(resourceUri).permission(permission) API
     */
    @Deprecated
    public PermissionResourceRequestAdapter resource(String resourceUri) {
        if ("".equals(resourceUri)) {
            throw new IllegalArgumentException("'resourceUri' mustn't be an empty string");
        }
        return new PermissionResourceRequestAdapter(sessionStorage, resourceUri);
    }

    public SinglePermissionsAdapter permission() {

        return new SinglePermissionsAdapter(sessionStorage, resourceUri);
    }

    public BatchPermissionsAdapter permissions() {

        return new BatchPermissionsAdapter(sessionStorage, resourceUri);
    }

    public SinglePermissionsAdapter permission(RepositoryPermission permission) {
        return new SinglePermissionsAdapter(sessionStorage, permission);
    }


    public BatchPermissionsAdapter permissions(RepositoryPermissionListWrapper permissions) {
        return new BatchPermissionsAdapter(sessionStorage, permissions);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public OperationResult createNew(RepositoryPermission permission) {
        return buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI}, new DefaultErrorHandler()).post(permission);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public <R> RequestExecution asyncCreateNew(final RepositoryPermission permission, final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(permission));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public OperationResult createNew(RepositoryPermissionListWrapper permissions) {
        JerseyRequest request = buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI});
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        return request.post(permissions);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public <R> RequestExecution asyncCreateNew(final RepositoryPermissionListWrapper permissions, final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(sessionStorage, Object.class, new String[]{SERVICE_URI});
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(permissions));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
