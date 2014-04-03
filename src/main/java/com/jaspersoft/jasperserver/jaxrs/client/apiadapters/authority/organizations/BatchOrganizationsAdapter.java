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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.Organization;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.OrganizationsListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class BatchOrganizationsAdapter extends AbstractAdapter {

    private final MultivaluedMap<String, String> params;

    public BatchOrganizationsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchOrganizationsAdapter parameter(OrganizationParameter orgParam, String value) {
        params.add(orgParam.getParamName(), value);
        return this;
    }

    public OperationResult<OrganizationsListWrapper> get() {
        JerseyRequest<OrganizationsListWrapper> builder = buildRequest(OrganizationsListWrapper.class);
        builder.addParams(params);
        return builder.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<OrganizationsListWrapper>, R> callback) {
        final JerseyRequest<OrganizationsListWrapper> builder = buildRequest(OrganizationsListWrapper.class);
        builder.addParams(params);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<Organization> create(Organization organization) {
        JerseyRequest<Organization> builder = buildRequest(Organization.class);
        builder.addParams(params);
        return builder.post(organization);
    }

    public <R> RequestExecution asyncCreate(final Organization organization, final Callback<OperationResult<Organization>, R> callback){
        final JerseyRequest<Organization> builder = buildRequest(Organization.class);
        builder.addParams(params);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.post(organization));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private <T> JerseyRequest<T> buildRequest(Class<T> responseType) {
        return JerseyRequest.buildRequest(sessionStorage, responseType, new String[]{"/organizations"}, new DefaultErrorHandler());
    }

}
