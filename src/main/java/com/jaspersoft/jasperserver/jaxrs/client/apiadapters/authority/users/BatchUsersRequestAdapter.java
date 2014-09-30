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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class BatchUsersRequestAdapter extends AbstractAdapter {

    private MultivaluedMap<String, String> params;
    private final String uri;

    public BatchUsersRequestAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        params = new MultivaluedHashMap<String, String>();
        if (organizationId != null) {
            uri = "/organizations/" + organizationId + "/users";
        } else {
            uri = "/users";
        }
    }

    public BatchUsersRequestAdapter param(UsersParameter userParam, String value) {
        params.add(userParam.getParamName(), value);
        return this;
    }

    public OperationResult<UsersListWrapper> get() {
        JerseyRequest<UsersListWrapper> request = buildRequest(sessionStorage, UsersListWrapper.class,
                new String[]{uri}, new DefaultErrorHandler());
        request.addParams(params);
        return request.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<UsersListWrapper>, R> callback) {
        final JerseyRequest<UsersListWrapper> request = buildRequest(sessionStorage, UsersListWrapper.class, new String[]{uri}, new DefaultErrorHandler());
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
