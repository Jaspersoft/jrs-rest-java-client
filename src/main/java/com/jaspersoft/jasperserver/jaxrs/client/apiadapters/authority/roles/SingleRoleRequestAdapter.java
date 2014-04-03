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

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

public class SingleRoleRequestAdapter extends AbstractAdapter {


    private final String roleUriPrefix;

    public SingleRoleRequestAdapter(SessionStorage sessionStorage, String organizationId, String rolename) {
        super(sessionStorage);
        if (organizationId != null)
            roleUriPrefix = "/organizations/" + organizationId + "/roles/" + rolename;
        else
            roleUriPrefix = "/roles/" + rolename;
    }

    public OperationResult<ClientRole> get(){
        return buildRequest(ClientRole.class).get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientRole>, R> callback){
        final JerseyRequest<ClientRole> builder = buildRequest(ClientRole.class);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<RolesListWrapper> createOrUpdate(ClientRole user){
        return buildRequest(RolesListWrapper.class).put(user);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientRole user, final Callback<OperationResult<RolesListWrapper>, R> callback){
        final JerseyRequest<RolesListWrapper> builder = buildRequest(RolesListWrapper.class);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.put(user));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete(){
        return buildRequest(ClientRole.class).delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<ClientRole>, R> callback){
        final JerseyRequest<ClientRole> builder = buildRequest(ClientRole.class);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.delete());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private <T> JerseyRequest<T> buildRequest(Class<T> returnType){
        return JerseyRequest.buildRequest(
                sessionStorage,
                returnType,
                new String[]{roleUriPrefix},
                new DefaultErrorHandler());
    }

}
