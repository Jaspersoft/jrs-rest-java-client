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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceSearchParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

public class BatchResourcesAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "resources";
    private MultivaluedMap<String, String> params;

    public BatchResourcesAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<>();
    }

    public BatchResourcesAdapter parameter(ResourceSearchParameter param, String value) {
        params.add(param.getName(), UrlUtils.encode(value));
        return this;
    }

    public BatchResourcesAdapter parameter(String name, String value) {
        params.add(name, UrlUtils.encode(value));
        return this;
    }

    public OperationResult<ClientResourceListWrapper> search() {
        return buildRequest(ClientResourceListWrapper.class).get();
    }

    public <R> RequestExecution asyncSearch(final Callback<OperationResult<ClientResourceListWrapper>, R> callback) {
        final JerseyRequest<ClientResourceListWrapper> request = buildRequest(ClientResourceListWrapper.class);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        return buildRequest(Object.class).delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(Object.class);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private <T> JerseyRequest<T> buildRequest(Class<T> responseClass) {
        JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage, responseClass, new String[]{SERVICE_URI}, new DefaultErrorHandler());
        request.addParams(params);
        return request;
    }

}
