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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.CommonExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

public class RunReportExceptionHandler extends CommonExceptionHandler {

    private static final Log log = LogFactory.getLog(RunReportExceptionHandler.class);

    @Override
    protected void handleOtherErrors(Response response) {
        String jasperServerError = response.getHeaderString("JasperServerError");
        if (jasperServerError != null && jasperServerError.equals("true")){
            JSClientWebException exception = null;
            try {
                String message = response.readEntity(String.class);
                Class<? extends JSClientWebException> exceptionType = getExceptionType(null, response.getStatus());
                exception = exceptionType.getConstructor(String.class).newInstance(message);
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
}
