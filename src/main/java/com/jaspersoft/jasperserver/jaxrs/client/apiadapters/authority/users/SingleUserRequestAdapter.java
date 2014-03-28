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
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
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

    public OperationResult<ClientUser> createOrUpdate(ClientUser user){
        return buildRequest().put(user);
    }

    public OperationResult delete(){
        return buildRequest().delete();
    }

    private JerseyRequestBuilder<ClientUser> buildRequest(){
        return JerseyRequestBuilder.buildRequest(
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

        public OperationResult delete(){
            return buildRequest().delete();
        }

        public OperationResult createOrUpdate(ClientUserAttribute attribute){
            return buildRequest().put(attribute);
        }

        private JerseyRequestBuilder<ClientUserAttribute> buildRequest(){
            return JerseyRequestBuilder.buildRequest(
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
            JerseyRequestBuilder<UserAttributesListWrapper> builder = buildRequest();
            builder.addParams(params);
            return builder.get();
        }

        public OperationResult createOrUpdate(UserAttributesListWrapper attributesList){
            return buildRequest().put(attributesList);
        }

        public OperationResult delete(){
            JerseyRequestBuilder<UserAttributesListWrapper> builder = buildRequest();
            builder.addParams(params);
            return builder.delete();
        }

        private JerseyRequestBuilder<UserAttributesListWrapper> buildRequest(){
            return JerseyRequestBuilder.buildRequest(
                    sessionStorage,
                    UserAttributesListWrapper.class,
                    new String[]{userUriPrefix, "/attributes"},
                    new DefaultErrorHandler());
        }

    }

}
