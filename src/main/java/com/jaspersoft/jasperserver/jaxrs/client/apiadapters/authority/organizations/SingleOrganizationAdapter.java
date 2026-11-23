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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedHashMap;


public class SingleOrganizationAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "organizations";
    private final ClientTenant clientTenant;
    private final MultivaluedHashMap<String, String> params;

    public SingleOrganizationAdapter(SessionStorage sessionStorage, ClientTenant clientTenant) {
        super(sessionStorage);
        this.clientTenant = clientTenant;
        this.params = new MultivaluedHashMap<>();
    }

    public OperationResult<ClientTenant> get() {
        return buildRequest().get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientTenant>, R> callback) {
        final JerseyRequest<ClientTenant> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientTenant> createOrUpdate(ClientTenant clientTenant) {
        return buildRequest().put(clientTenant);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientTenant clientTenant,
                                                    final Callback<OperationResult<ClientTenant>, R> callback) {
        final JerseyRequest<ClientTenant> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(clientTenant));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * Saves Organization. Use this method only if you want to create a new single Organization
     * with or without passing OrganizationParameter.
     *
     * @return persisted wrapped Organization
     */
    public OperationResult<ClientTenant> create() {
        JerseyRequest<ClientTenant> request = request();
        return params.size() != 0
                ? request.addParams(params).post(clientTenant)
                : request.post(clientTenant);
    }

    /**
     * Adds parameter to the URI
     *
     * @param parameter URI parameter
     * @param value     parameter value
     * @return this
     */
    public SingleOrganizationAdapter parameter(OrganizationParameter parameter, boolean value) {
        params.add(parameter.getParamName(), String.valueOf(value));
        return this;
    }

    public SingleOrganizationAdapter parameter(OrganizationParameter parameter, String value) {
        params.add(parameter.getParamName(), UrlUtils.encode(value));
        return this;
    }

    public OperationResult<ClientTenant> delete() {
        return buildRequest().delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<ClientTenant>, R> callback) {
        final JerseyRequest<ClientTenant> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientTenant> buildRequest() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                ClientTenant.class,
                new String[]{SERVICE_URI,
                        (clientTenant.getId() == null) ? clientTenant.getAlias() : clientTenant.getId()},
                new DefaultErrorHandler()
        );
    }

    private JerseyRequest<ClientTenant> request() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                ClientTenant.class,
                new String[]{SERVICE_URI},
                new DefaultErrorHandler()
        );
    }

}