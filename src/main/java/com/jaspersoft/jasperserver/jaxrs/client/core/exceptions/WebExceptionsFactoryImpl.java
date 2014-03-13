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

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationError;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationErrorsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebExceptionsFactoryImpl implements WebExceptionsFactory {

    private static final Map<Integer, Class<? extends JSClientWebException>> errorCodeToTypeMap =
            new HashMap<Integer, Class<? extends JSClientWebException>>() {{
                put(ResponseStatus.BAD_REQUEST, BadRequestException.class);
                put(ResponseStatus.FORBIDDEN, AccessForbiddenException.class);
                put(ResponseStatus.NOT_ALLOWED, HttpMethodNotAllowedException.class);
                put(ResponseStatus.SERVER_ERROR, InternalServerErrorException.class);
                put(ResponseStatus.NOT_ACCEPTABLE, RepresentationNotAvailableForResourceException.class);
                put(ResponseStatus.NOT_FOUND, ResourceNotFoundException.class);
                put(ResponseStatus.UNAUTHORIZED, AuthenticationFailedException.class);
                put(ResponseStatus.CONFLICT, ConflictException.class);
            }};

    @Override
    public JSClientWebException getException(Response response) {
        Class<? extends JSClientWebException> exceptionClass = errorCodeToTypeMap.get(response.getStatus());
        JSClientWebException exception = null;
        try {
            List<ErrorDescriptor> errorDescriptors = new ArrayList<ErrorDescriptor>();

            if (response.hasEntity() && !response.getHeaderString("Content-Type").contains("text/html")) {
                response.bufferEntity();
                try {
                    ErrorDescriptor errorDescriptor = response.readEntity(ErrorDescriptor.class);
                    errorDescriptors.add(errorDescriptor);
                } catch (Exception e) {
                    ValidationErrorsListWrapper validationErrors = response.readEntity(ValidationErrorsListWrapper.class);
                    errorDescriptors = toErrorDescriptor(validationErrors);
                }
            }
            exception = exceptionClass.getConstructor(String.class, List.class)
                    .newInstance(generateErrorMessage(errorDescriptors), errorDescriptors);
        } catch (Exception ignored) {}

        return exception;
    }

    private List<ErrorDescriptor> toErrorDescriptor(ValidationErrorsListWrapper validationErrors) {
        List<ErrorDescriptor> errorDescriptors = new ArrayList<ErrorDescriptor>();
        List<ValidationError> errors = validationErrors.getErrors();
        for (ValidationError error : errors) {
            ErrorDescriptor errorDescriptor = new ErrorDescriptor();
            errorDescriptor.setMessage(error.toString() + " (field: " + error.getField() + ")");
            errorDescriptor.setErrorCode(error.getErrorCode());
            errorDescriptor.setParameters(error.getErrorArguments());

            errorDescriptors.add(errorDescriptor);
        }
        return errorDescriptors;
    }

    private String generateErrorMessage(List<ErrorDescriptor> errorDescriptors) {
        StringBuilder sb = new StringBuilder();
        for (ErrorDescriptor errorDescriptor : errorDescriptors) {
            sb.append("\n\t\t").append(errorDescriptor.getMessage());
        }
        return sb.toString();
    }

}
