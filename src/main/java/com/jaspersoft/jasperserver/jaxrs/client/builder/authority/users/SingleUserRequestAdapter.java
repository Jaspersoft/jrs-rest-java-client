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

package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class SingleUserRequestAdapter {

    private SessionStorage sessionStorage;
    private String username;


    public SingleUserRequestAdapter(SessionStorage sessionStorage, String username) {
        this.sessionStorage = sessionStorage;
        this.username = username;
    }

    public SingleAttributeInterfaceAdapter attribute(String attributeName){
        return new SingleAttributeInterfaceAdapter(attributeName);
    }

    public BatchAttributeInterfaceAdapter attributes(){
        return new BatchAttributeInterfaceAdapter();
    }

    public OperationResult<ClientUser> get(){
        JerseyRequestBuilder<ClientUser> builder =
                buildRequest(sessionStorage, ClientUser.class, new String[]{"/users", username});
        return builder.get();
    }

    public OperationResult createOrUpdate(ClientUser user){
        JerseyRequestBuilder<Object> builder =
                buildRequest(sessionStorage, Object.class, new String[]{"/users", username});
        return builder.put(user);
    }

    public OperationResult delete(){
        JerseyRequestBuilder<Object> builder =
                buildRequest(sessionStorage, Object.class, new String[]{"/users", username});
        return builder.delete();
    }

    public class SingleAttributeInterfaceAdapter {

        private final String attributeName;

        public SingleAttributeInterfaceAdapter(String attributeName) {
            this.attributeName = attributeName;
        }

        public OperationResult<ClientUserAttribute> get(){
            JerseyRequestBuilder<ClientUserAttribute> builder =
                    buildRequest(sessionStorage, ClientUserAttribute.class, new String[]{"/users", username, "/attributes", attributeName});
            return builder.get();
        }

        public OperationResult delete(){
            JerseyRequestBuilder<Object> builder =
                    buildRequest(sessionStorage, Object.class, new String[]{"/users", username, "/attributes", attributeName});
            return builder.delete();
        }

        public OperationResult createOrUpdate(ClientUserAttribute attribute){
            JerseyRequestBuilder<Object> builder =
                    buildRequest(sessionStorage, Object.class, new String[]{"/users", username, "/attributes", attributeName});
            return builder.put(attribute);
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
            JerseyRequestBuilder<UserAttributesListWrapper> builder =
                    buildRequest(sessionStorage, UserAttributesListWrapper.class, new String[]{"/users", username, "/attributes"});
            builder.addParams(params);
            return builder.get();
        }

        public OperationResult<UserAttributesListWrapper> createOrUpdate(UserAttributesListWrapper attributesList){
            JerseyRequestBuilder<UserAttributesListWrapper> builder =
                    buildRequest(sessionStorage, UserAttributesListWrapper.class, new String[]{"/users", username, "/attributes"});
            return builder.put(attributesList);
        }

        public OperationResult<UserAttributesListWrapper> delete(){
            JerseyRequestBuilder<UserAttributesListWrapper> builder =
                    buildRequest(sessionStorage, UserAttributesListWrapper.class, new String[]{"/users", username, "/attributes"});
            builder.addParams(params);
            return builder.delete();
        }

    }

}
