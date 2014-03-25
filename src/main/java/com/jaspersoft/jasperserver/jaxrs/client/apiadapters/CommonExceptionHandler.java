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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters;

import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.JRSExceptionsMapping;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ErrorDescriptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class CommonExceptionHandler extends DefaultExceptionHandler {

    private static final Log log = LogFactory.getLog(CommonExceptionHandler.class);

    @Override
    protected void handleBadRequestError(Response response) {
        handleOtherErrors(response);
    }

    @Override
    protected void handleOtherErrors(Response response) {
        if (response.getHeaderString("Content-Type").contains("xml") ||
                response.getHeaderString("Content-Type").contains("json")){

            JSClientWebException exception = null;
            try {

                ErrorDescriptor errorDescriptor = response.readEntity(ErrorDescriptor.class);
                Class<? extends JSClientWebException> exceptionType =
                        getExceptionType(errorDescriptor.getErrorCode(), response.getStatus());

                String message = errorDescriptor.getMessage();
                exception = exceptionType.getConstructor(String.class, List.class)
                        .newInstance(message != null ? message : errorDescriptor.getErrorCode(), Arrays.asList(errorDescriptor));
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

    protected Class<? extends JSClientWebException> getExceptionType(String errorCode, int responseStatus){
        Class<? extends JSClientWebException> exceptionType =
                JRSExceptionsMapping.ERROR_CODE_TO_TYPE_MAP.get(errorCode);
        if (exceptionType == null)
            exceptionType = httpErrorCodeToTypeMap.get(responseStatus);

        return exceptionType;
    }


}
