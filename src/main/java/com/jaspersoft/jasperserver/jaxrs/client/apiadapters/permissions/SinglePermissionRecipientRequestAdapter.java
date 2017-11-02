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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;

/**
 * @deprecated Replaced with @Link {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.BatchPermissionsAdapter}
 * and @Link {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.SinglePermissionsAdapter}
 */
public class SinglePermissionRecipientRequestAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "permissions";
    private final String recipient;
    private ArrayList<String> path = new ArrayList<String>();

    public SinglePermissionRecipientRequestAdapter(SessionStorage sessionStorage, String resourceUri, String recipient) {
        super(sessionStorage);
        this.path.add(SERVICE_URI);
        this.path.addAll(asList(resourceUri.split("/")));
        this.recipient = recipient;
    }

    public OperationResult<RepositoryPermission> get() {
        return getBuilder(RepositoryPermission.class).get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<RepositoryPermission>, R> callback) {
        final JerseyRequest<RepositoryPermission> request = getBuilder(RepositoryPermission.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<RepositoryPermission> createOrUpdate(RepositoryPermission permission) {
        return getBuilder(RepositoryPermission.class).put(permission);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final RepositoryPermission permission, final Callback<OperationResult<RepositoryPermission>, R> callback) {
        final JerseyRequest<RepositoryPermission> request = getBuilder(RepositoryPermission.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(permission));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        return getBuilder(Object.class).delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = getBuilder(Object.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private <T> JerseyRequest<T> getBuilder(Class<T> responseClass) {

        JerseyRequest<T> request = buildRequest(sessionStorage, responseClass, path.toArray(new String[path.size()]));
        request.addMatrixParam("recipient", recipient);
        return request;
    }
}