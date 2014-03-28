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
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class BatchRolesRequestAdapter extends AbstractAdapter{

    private final MultivaluedMap<String, String> params;
    private final String uri;

    public BatchRolesRequestAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        params = new MultivaluedHashMap<String, String>();
        if (organizationId != null)
            uri = "/organizations/" + organizationId + "/roles";
        else
            uri = "/roles";
    }

    public BatchRolesRequestAdapter param(RolesParameter rolesParam, String value){
        params.add(rolesParam.getParamName(), value);
        return this;
    }

    public OperationResult<RolesListWrapper> get(){
        JerseyRequestBuilder<RolesListWrapper> builder =
                buildRequest(sessionStorage, RolesListWrapper.class, new String[]{uri}, new DefaultErrorHandler());
        builder.addParams(params);
        return builder.get();
    }

}
