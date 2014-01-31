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

package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionOptions;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;

import javax.ws.rs.core.MediaType;

public class ReportExecutionRequestBuilder {

    private final SessionStorage sessionStorage;
    private final String requestId;

    public ReportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId) {
        this.requestId = requestId;
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ReportExecutionStatusEntity> status() {
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(sessionStorage, ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        return builder.get();
    }

    public OperationResult<ReportExecutionDescriptor> executionDetails() {
        JerseyRequestBuilder<ReportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ReportExecutionDescriptor>(sessionStorage, ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions").setPath(requestId);
        builder.setAccept(MediaType.APPLICATION_JSON);
        return builder.get();
    }

    public OperationResult<ReportExecutionStatusEntity> cancelExecution() {
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(sessionStorage, ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        return builder.put(statusEntity);
    }

    public ExportExecutionRequestBuilder export(String exportOutput) {
        return new ExportExecutionRequestBuilder(sessionStorage, requestId, exportOutput);
    }

    public OperationResult<ExportExecutionDescriptor> runExport(ExportExecutionOptions exportExecutionOptions) {
        JerseyRequestBuilder<ExportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ExportExecutionDescriptor>(sessionStorage, ExportExecutionDescriptor.class);

        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("exports");

        return builder.post(exportExecutionOptions);
    }

}
