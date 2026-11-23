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

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AccessDeniedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.BadRequestException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ConflictException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.HttpMethodNotAllowedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.InternalServerErrorException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.RepresentationalTypeNotSupportedForResourceException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.RequestedRepresentationNotAvailableForResourceException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultErrorHandler implements ErrorHandler {

//    private static final Log log = LogFactory.getLog(DefaultErrorHandler.class);

    protected static final Map<Response.Status, Class<? extends JSClientWebException>> httpErrorCodeToTypeMap =
            new HashMap<Response.Status, Class<? extends JSClientWebException>>() {{
                put(Response.Status.BAD_REQUEST, BadRequestException.class);
                put(Response.Status.FORBIDDEN, AccessDeniedException.class);
                put(Response.Status.NOT_ACCEPTABLE, HttpMethodNotAllowedException.class);
                put(Response.Status.INTERNAL_SERVER_ERROR, InternalServerErrorException.class);
                put(Response.Status.NOT_ACCEPTABLE, RequestedRepresentationNotAvailableForResourceException.class);
                put(Response.Status.NOT_FOUND, ResourceNotFoundException.class);
                put(Response.Status.UNAUTHORIZED, AuthenticationFailedException.class);
                put(Response.Status.CONFLICT, ConflictException.class);
                put(Response.Status.UNSUPPORTED_MEDIA_TYPE, RepresentationalTypeNotSupportedForResourceException.class);
            }};

    @Override
    public void handleError(Response response) {
        if (response.hasEntity()) {
            response.bufferEntity();
        }
        handleBodyError(response);
        handleStatusCodeError(response, null);
    }

    protected <T> T readBody(Response response, Class<T> expectedType) {
        T entity = null;
        try {
            entity = response.readEntity(expectedType);
        } catch (MessageBodyProviderNotFoundException e) {
//            log.warn("Cannot read entity from response body: unexpected body content");
        } catch (ProcessingException e) {
//            log.warn("Cannot read entity from response body");
        }
        return entity;
    }

    protected void handleBodyError(Response response) {
        String contentType = response.getHeaderString("Content-Type");
        if (contentType != null && contentType.contains("text/html")) {
            return;
        }
        ErrorDescriptor errorDescriptor = readBody(response, ErrorDescriptor.class);
        if (errorDescriptor != null) {
            JSClientWebException exception = null;
            try {
                Class<? extends JSClientWebException> exceptionType = JRSExceptionsMapping.ERROR_CODE_TO_TYPE_MAP.get(errorDescriptor.getErrorCode());
                if (exceptionType != null) {
                    String message = errorDescriptor.getMessage();
                    exception = exceptionType.getConstructor(String.class, List.class).newInstance(message != null ? message : errorDescriptor.getErrorCode(), Arrays.asList(errorDescriptor));
                }
            } catch (Exception e) {
//                log.warn("Cannot instantiate exception.", e);
            }
            if (exception != null) {
                throw exception;
            }
        }
    }

    protected void handleStatusCodeError(Response response, String overridingMessage) {
        Class<? extends JSClientWebException> exceptionType = httpErrorCodeToTypeMap.get(Response.Status.fromStatusCode(response.getStatus()));
        String reasonPhrase = response.getStatusInfo().getReasonPhrase();
        JSClientWebException exception = new JSClientWebException(overridingMessage != null ? overridingMessage : reasonPhrase);
        try {
            exception = exceptionType.getConstructor(String.class).newInstance(overridingMessage != null ? overridingMessage : reasonPhrase);
        } catch (Exception e) {
//            log.error("Cannot instantiate exception", e);
        }
        throw exception;
    }
}
