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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ValidationException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationError;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationErrorsListWrapper;

import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class AlertValidationErrorHandler extends DefaultErrorHandler {

    @Override
    protected void handleBodyError(Response response) {
        List<ErrorDescriptor> errorDescriptors;
        if (response.getHeaderString("Content-Type").contains("xml") || response.getHeaderString("Content-Type").contains("alert+json")) {
            ValidationErrorsListWrapper validationErrors = readBody(response, ValidationErrorsListWrapper.class);
            if (validationErrors == null) {
                super.handleBodyError(response);
                return;
            }
            errorDescriptors = toErrorDescriptorList(validationErrors);
            throw new ValidationException(generateErrorMessage(errorDescriptors), errorDescriptors);
        }
        super.handleBodyError(response);
    }

    protected List<ErrorDescriptor> toErrorDescriptorList(ValidationErrorsListWrapper validationErrors) {
        List<ErrorDescriptor> errorDescriptors = new ArrayList<ErrorDescriptor>();
        List<ValidationError> errors = validationErrors.getErrors();
        for (ValidationError error : errors) {
            ErrorDescriptor errorDescriptor = new ErrorDescriptor();
            errorDescriptor.setMessage(error.toString() + " (field: " + error.getField() + ")");
            errorDescriptor.setErrorCode(error.getErrorCode());
            errorDescriptor.addParameters(error.getErrorArguments());
            errorDescriptors.add(errorDescriptor);
        }
        return errorDescriptors;
    }

    private String generateErrorMessage(List<ErrorDescriptor> errorDescriptors) {
        StringBuilder sb = new StringBuilder();
        if (errorDescriptors != null) {
            for (ErrorDescriptor errorDescriptor : errorDescriptors) {
                String message = errorDescriptor.getMessage();
                sb.append("\n\t\t").append(message != null ? message : errorDescriptor.getErrorCode());
            }
        }
        return sb.toString();
    }
}
