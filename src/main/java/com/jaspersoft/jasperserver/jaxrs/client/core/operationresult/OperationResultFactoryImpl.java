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

package com.jaspersoft.jasperserver.jaxrs.client.core.operationresult;

import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesTypeResolverUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.WebExceptionsFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.WebExceptionsFactoryImpl;

import javax.ws.rs.core.Response;


public class OperationResultFactoryImpl implements OperationResultFactory {

    private WebExceptionsFactory exceptionsFactory;

    public OperationResultFactoryImpl(){
        exceptionsFactory = new WebExceptionsFactoryImpl();
    }

    @Override
    public <T> OperationResult<T> getOperationResult(Response response, Class<T> responseClass)
            throws JSClientWebException {
        checkForError(response);
        if (isClientResource(responseClass))
            responseClass = (Class<T>) getSpecificResourceType(response);

        return getAppropriateOperationResultInstance(response, responseClass);
    }

    private <T> OperationResult<T> getAppropriateOperationResultInstance(Response response,
                                                                                   Class<T> responseClass){
        OperationResult<T> result;
        if (response.hasEntity())
            result = new WithEntityOperationResult<T>(response, responseClass);
        else
            result = new NullEntityOperationResult(response, responseClass);
        return result;
    }

    private void checkForError(Response response) throws JSClientWebException {
        int statusCode = response.getStatus();
        if (isError(statusCode))
            throw exceptionsFactory.getException(response);
    }

    private boolean isError(int responseCode){
        return responseCode >= 400;
    }

    private boolean isClientResource(Class<?> clazz){
        return clazz.isAssignableFrom(ClientResource.class);
    }

    private Class<? extends ClientResource> getSpecificResourceType(Response response){
        return ResourcesTypeResolverUtil.getClassForMime(response.getHeaderString("Content-Type"));
    }

}
