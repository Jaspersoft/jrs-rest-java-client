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

package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions;

import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class WebExceptionsFactoryImpl implements WebExceptionsFactory {

    private static final Map<Integer, Class<? extends JSClientWebException>> errorCodeToTypeMap =
            new HashMap<Integer, Class<? extends JSClientWebException>>(){{
                put(ResponseStatus.BAD_REQUEST, BadRequestException.class);
                put(ResponseStatus.FORBIDDEN, AccessForbiddenException.class);
                put(ResponseStatus.NOT_ALLOWED, HttpMethodNotAllowedException.class);
                put(ResponseStatus.SERVER_ERROR, InternalServerErrorException.class);
                put(ResponseStatus.NOT_ACCEPTABLE, RepresentationNotAvailableForResourceException.class);
                put(ResponseStatus.NOT_FOUND, ResourceNotFoundException.class);
                put(ResponseStatus.UNAUTHORIZED, AuthenticationFailureException.class);
                put(ResponseStatus.CONFLICT, ConflictException.class);
            }};

    @Override
    public JSClientWebException getException(Response response) {
        Class<? extends JSClientWebException> exceptionClass = errorCodeToTypeMap.get(response.getStatus());
        JSClientWebException exception = null;
        try {
            ErrorDescriptor errorDescriptor = null;

            try {
                errorDescriptor = response.readEntity(ErrorDescriptor.class);
            } catch (Exception ignored) {}

            exception = exceptionClass.getConstructor(String.class)
                    .newInstance(errorDescriptor != null ? errorDescriptor.getMessage()
                            : "Exception happened with response: " + response);
        } catch (Exception ignored) {}

        return exception;
    }

}
