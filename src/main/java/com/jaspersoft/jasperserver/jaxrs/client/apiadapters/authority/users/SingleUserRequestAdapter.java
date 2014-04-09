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

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class SingleUserRequestAdapter extends AbstractAdapter {

    private final String userUriPrefix;

    public SingleUserRequestAdapter(SessionStorage sessionStorage, String organizationId,  String username) {
        super(sessionStorage);
        if (organizationId != null)
            userUriPrefix = "/organizations/" + organizationId + "/users/" + username;
        else
            userUriPrefix = "/users/" + username;
    }

    public SingleAttributeInterfaceAdapter attribute(String attributeName){
        if ("".equals(attributeName) || "/".equals(attributeName))
            throw new  IllegalArgumentException("'attributeName' mustn't be an empty string");
        return new SingleAttributeInterfaceAdapter(attributeName);
    }

    public BatchAttributeInterfaceAdapter attributes(){
        return new BatchAttributeInterfaceAdapter();
    }

    public OperationResult<ClientUser> get(){
        return buildRequest().get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUser>, R> callback){
        final JerseyRequest<ClientUser> request = buildRequest();

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientUser> createOrUpdate(ClientUser user){
        return buildRequest().put(user);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientUser user, final Callback<OperationResult<ClientUser>, R> callback){
        final JerseyRequest<ClientUser> request = buildRequest();

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(user));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete(){
        return buildRequest().delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<ClientUser>, R> callback){
        final JerseyRequest<ClientUser> request = buildRequest();

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientUser> buildRequest(){
        return JerseyRequest.buildRequest(
                sessionStorage,
                ClientUser.class,
                new String[]{userUriPrefix},
                new DefaultErrorHandler());
    }

    public class SingleAttributeInterfaceAdapter {

        private final String attributeName;

        public SingleAttributeInterfaceAdapter(String attributeName) {
            this.attributeName = attributeName;
        }

        public OperationResult<ClientUserAttribute> get(){
            return buildRequest().get();
        }

        public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUserAttribute>, R> callback){
            final JerseyRequest<ClientUserAttribute> request = buildRequest();

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
            return buildRequest().delete();
        }

        public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback){
            final JerseyRequest request = buildRequest();

            RequestExecution task = new RequestExecution(new Runnable() {
                @Override
                public void run() {
                    callback.execute(request.delete());
                }
            });

            ThreadPoolUtil.runAsynchronously(task);
            return task;
        }

        public OperationResult createOrUpdate(ClientUserAttribute attribute){
            return buildRequest().put(attribute);
        }

        public <R> RequestExecution asyncCreateOrUpdate(final ClientUserAttribute userAttribute,
                                                        final Callback<OperationResult<ClientUserAttribute>, R> callback){
            final JerseyRequest<ClientUserAttribute> request = buildRequest();

            RequestExecution task = new RequestExecution(new Runnable() {
                @Override
                public void run() {
                    callback.execute(request.put(userAttribute));
                }
            });

            ThreadPoolUtil.runAsynchronously(task);
            return task;
        }

        private JerseyRequest<ClientUserAttribute> buildRequest(){
            return JerseyRequest.buildRequest(
                    sessionStorage,
                    ClientUserAttribute.class,
                    new String[]{userUriPrefix, "/attributes", attributeName},
                    new DefaultErrorHandler());
        }

    }

    public class BatchAttributeInterfaceAdapter {

        private MultivaluedMap<String, String> params;

        public BatchAttributeInterfaceAdapter(){
            params = new MultivaluedHashMap<String, String>();
        }

        public BatchAttributeInterfaceAdapter param(UsersAttributesParameter usersAttributesParam, String value){
            params.add(usersAttributesParam.getParamName(), value);
            return this;
        }

        public OperationResult<UserAttributesListWrapper> get(){
            JerseyRequest<UserAttributesListWrapper> request = buildRequest();
            request.addParams(params);
            return request.get();
        }

        public <R> RequestExecution asyncGet(final Callback<OperationResult<UserAttributesListWrapper>, R> callback){
            final JerseyRequest<UserAttributesListWrapper> request = buildRequest();
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

        public OperationResult createOrUpdate(UserAttributesListWrapper attributesList){
            return buildRequest().put(attributesList);
        }

        public <R> RequestExecution asyncCreateOrUpdate(final UserAttributesListWrapper attributesList,
                                                        final Callback<OperationResult<UserAttributesListWrapper>, R> callback){
            final JerseyRequest<UserAttributesListWrapper> request = buildRequest();

            RequestExecution task = new RequestExecution(new Runnable() {
                @Override
                public void run() {
                    callback.execute(request.put(attributesList));
                }
            });

            ThreadPoolUtil.runAsynchronously(task);
            return task;
        }

        public OperationResult delete(){
            JerseyRequest<UserAttributesListWrapper> request = buildRequest();
            request.addParams(params);
            return request.delete();
        }

        public <R> RequestExecution asyncDelete(final Callback<OperationResult<UserAttributesListWrapper>, R> callback){
            final JerseyRequest<UserAttributesListWrapper> request = buildRequest();
            request.addParams(params);

            RequestExecution task = new RequestExecution(new Runnable() {
                @Override
                public void run() {
                    callback.execute(request.delete());
                }
            });

            ThreadPoolUtil.runAsynchronously(task);
            return task;
        }

        private JerseyRequest<UserAttributesListWrapper> buildRequest(){
            return JerseyRequest.buildRequest(
                    sessionStorage,
                    UserAttributesListWrapper.class,
                    new String[]{userUriPrefix, "/attributes"},
                    new DefaultErrorHandler());
        }

    }

}
