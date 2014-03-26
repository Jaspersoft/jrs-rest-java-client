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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;

import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class ExportExecutionRequestBuilder extends AbstractAdapter {

    private String requestId;
    private String exportOutput;

    public ExportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId, String exportOutput){
        super(sessionStorage);
        this.requestId = requestId;
        this.exportOutput = exportOutput;
    }

    public OperationResult<InputStream> outputResource(){
        return buildRequest(sessionStorage, InputStream.class,
                new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/outputResource"}, new DefaultErrorHandler())
                .get();
    }

    public OperationResult<InputStream> attachment(String attachmentId){

        if ("".equals(attachmentId) || "/".equals(attachmentId))
            throw new  IllegalArgumentException("'attachmentId' mustn't be an empty string");

        while (!"ready".equals(status().getEntity().getValue())){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        return buildRequest(sessionStorage, InputStream.class,
                new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/attachments", attachmentId}, new DefaultErrorHandler())
                .get();
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class,
                new String[]{"/reportExecutions", requestId, "/exports", exportOutput, "/status"}, new DefaultErrorHandler())
                .get();
    }
}
