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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.CommonExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationError;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ValidationErrorsListWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class JobValidationExceptionHandler extends CommonExceptionHandler {

    private static final Log log = LogFactory.getLog(JobValidationExceptionHandler.class);

    @Override
    protected void handleOtherErrors(Response response) {
        if (response.getHeaderString("Content-Type").contains("xml") ||
                response.getHeaderString("Content-Type").contains("json")){

            JSClientWebException exception = null;
            try {

                ValidationErrorsListWrapper validationErrors = response.readEntity(ValidationErrorsListWrapper.class);
                List<ErrorDescriptor> errorDescriptors = toErrorDescriptor(validationErrors);

                Class<? extends JSClientWebException> exceptionType =
                        getExceptionType(errorDescriptors.get(0).getErrorCode(), response.getStatus());

                exception = exceptionType.getConstructor(String.class, List.class)
                        .newInstance(generateErrorMessage(errorDescriptors), errorDescriptors);
            } catch (ProcessingException e) {
                log.warn("Cannot read entity from response body", e);
            } catch (Exception e) {
                log.warn("Cannot instantiate exception", e);
            }

            if (exception != null)
                throw exception;
        }
        super.handleOtherErrors(response);
    }

    protected List<ErrorDescriptor> toErrorDescriptor(ValidationErrorsListWrapper validationErrors) {
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

    protected String generateErrorMessage(List<ErrorDescriptor> errorDescriptors) {
        StringBuilder sb = new StringBuilder();
        for (ErrorDescriptor errorDescriptor : errorDescriptors) {
            sb.append("\n\t\t").append(errorDescriptor.getMessage());
        }
        return sb.toString();
    }
}
