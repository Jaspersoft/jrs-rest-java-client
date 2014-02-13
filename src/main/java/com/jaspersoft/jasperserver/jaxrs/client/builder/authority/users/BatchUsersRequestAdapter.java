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

import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class BatchUsersRequestAdapter {

    private final SessionStorage sessionStorage;
    private MultivaluedMap<String, String> params;


    public BatchUsersRequestAdapter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchUsersRequestAdapter param(UsersParameter userParam, String value){
        params.add(userParam.getParamName(), value);
        return this;
    }

    public OperationResult<UsersListWrapper> get(){
        return buildRequest(sessionStorage, UsersListWrapper.class, new String[]{"users"}, null, null, params, null).get();
    }

}
