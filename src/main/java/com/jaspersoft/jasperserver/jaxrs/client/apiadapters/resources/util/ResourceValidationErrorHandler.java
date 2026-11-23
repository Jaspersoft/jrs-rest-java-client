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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ValidationException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class ResourceValidationErrorHandler extends DefaultErrorHandler {

    @Override
    protected void handleBodyError(Response response) {
        List<ErrorDescriptor> errorDescriptors = null;
        if (response.getHeaderString("Content-Type").contains("xml") ||
                response.getHeaderString("Content-Type").contains("json")) {
            ErrorDescriptor[] validationErrors = readBody(response, ErrorDescriptor[].class);

            if (validationErrors == null){
                super.handleBodyError(response);
                return;
            }

            errorDescriptors = Arrays.asList(validationErrors);
            throw new ValidationException(generateErrorMessage(errorDescriptors), errorDescriptors);
        }
        super.handleBodyError(response);
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
