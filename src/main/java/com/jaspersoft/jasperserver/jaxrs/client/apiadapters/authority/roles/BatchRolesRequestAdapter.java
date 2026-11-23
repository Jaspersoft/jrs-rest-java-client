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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
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
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class BatchRolesRequestAdapter extends AbstractAdapter {

    private final MultivaluedMap<String, String> params;
    private final ArrayList<String> uri = new ArrayList<>();

    public BatchRolesRequestAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        params = new MultivaluedHashMap<>();
        if (organizationId != null) {
            uri.add("organizations");
            uri.add(organizationId);
            uri.add("roles");
        } else {
            uri.add("roles");
        }
    }

    public BatchRolesRequestAdapter param(RolesParameter rolesParam, String value) {
        params.add(rolesParam.getParamName(), UrlUtils.encode(value));
        return this;
    }

    public OperationResult<RolesListWrapper> get() {
        JerseyRequest<RolesListWrapper> request = buildRequest(sessionStorage,
                RolesListWrapper.class,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());
        request.addParams(params);
        return request.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<RolesListWrapper>, R> callback) {
        final JerseyRequest<RolesListWrapper> request = buildRequest(sessionStorage, RolesListWrapper.class, uri.toArray(new String[uri.size()]), new DefaultErrorHandler());
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
}
