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

package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling;

import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class DefaultExceptionHandler implements ExceptionHandler {

    private static final Log log = LogFactory.getLog(DefaultExceptionHandler.class);

    protected static final Map<Integer, Class<? extends JSClientWebException>> httpErrorCodeToTypeMap =
            new HashMap<Integer, Class<? extends JSClientWebException>>() {{
                put(ResponseStatus.BAD_REQUEST, BadRequestException.class);
                put(ResponseStatus.FORBIDDEN, AccessDeniedException.class);
                put(ResponseStatus.NOT_ALLOWED, HttpMethodNotAllowedException.class);
                put(ResponseStatus.SERVER_ERROR, InternalServerErrorException.class);
                put(ResponseStatus.NOT_ACCEPTABLE, RequestedRepresentationNotAvailableForResourceException.class);
                put(ResponseStatus.NOT_FOUND, ResourceNotFoundException.class);
                put(ResponseStatus.UNAUTHORIZED, AuthenticationFailedException.class);
                put(ResponseStatus.CONFLICT, ConflictException.class);
            }};

    @Override
    public void handleException(Response response) {
        switch (response.getStatus()){
            case ResponseStatus.UNAUTHORIZED:
                throw new AuthenticationFailedException(response.getStatusInfo().getReasonPhrase());
            case ResponseStatus.NOT_ALLOWED:
                throw new HttpMethodNotAllowedException(response.getStatusInfo().getReasonPhrase());
            case ResponseStatus.NOT_ACCEPTABLE:
                throw new RequestedRepresentationNotAvailableForResourceException(response.getStatusInfo().getReasonPhrase());
            case ResponseStatus.UNSUPPORTED_TYPE:
                throw new RepresentationalTypeNotSupportedForResourceException(response.getStatusInfo().getReasonPhrase());
            case ResponseStatus.FORBIDDEN:
            case ResponseStatus.NOT_FOUND:
            case ResponseStatus.CONFLICT:
            case ResponseStatus.SERVER_ERROR: {
                handleOtherErrors(response);
            }
            case ResponseStatus.BAD_REQUEST: {
                handleBadRequestError(response);
            }
            default:
                throw new JSClientWebException(response.getStatusInfo().getReasonPhrase());
        }
    }

    protected void handleBadRequestError(Response response){
        throw new BadRequestException(response.getStatusInfo().getReasonPhrase());
    }

    protected void handleOtherErrors(Response response){
        Class<? extends JSClientWebException> exceptionType = httpErrorCodeToTypeMap.get(response.getStatus());
        JSClientWebException exception = new JSClientWebException("Unexpected error");
        try {
            exception = exceptionType.getConstructor(String.class)
                    .newInstance(response.getStatusInfo().getReasonPhrase());
        } catch (Exception e) {
            log.error("Cannot instantiate exception", e);
        }
        throw exception;
    }

}
