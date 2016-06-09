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
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class BatchResourcesAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "resources";
    private MultivaluedMap<String, String> params;

    public BatchResourcesAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<String, String>();
    }

    public BatchResourcesAdapter parameter(ResourceSearchParameter param, String value){
        params.add(param.getName(), value);
        return this;
    }

    public OperationResult<ClientResourceListWrapper> search(){
        return getBuilder(ClientResourceListWrapper.class).get();
    }

    public <R> RequestExecution asyncSearch(final Callback<OperationResult<ClientResourceListWrapper>, R> callback) {
        final JerseyRequest<ClientResourceListWrapper> request = getBuilder(ClientResourceListWrapper.class);

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
        JerseyRequest<T> request = buildRequest(sessionStorage, responseClass, new String[]{SERVICE_URI}, new DefaultErrorHandler());
        request.addParams(params);
        return request;
    }

}
