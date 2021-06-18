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
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionOptions;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionStatusEntity;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
@Deprecated
public class ReportExecutionRequestBuilder extends AbstractAdapter {

    public static final String REPORT_EXECUTIONS_URI = "reportExecutions";
    public static final String STATUS_URI = "status";
    public static final String EXPORTS_URI = "exports";
    private final String requestId;

    public ReportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId) {
        super(sessionStorage);
        this.requestId = requestId;
    }

    public OperationResult<ReportExecutionStatusEntity> status() {
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class, new String[]{REPORT_EXECUTIONS_URI, requestId, STATUS_URI})
                .get();
    }

    public <R> RequestExecution asyncStatus(final Callback<OperationResult<ReportExecutionStatusEntity>, R> callback) {
        final JerseyRequest<ReportExecutionStatusEntity> request =
                buildRequest(sessionStorage, ReportExecutionStatusEntity.class, new String[]{REPORT_EXECUTIONS_URI, requestId, STATUS_URI});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ReportExecutionDescriptor> executionDetails() {
        return buildRequest(sessionStorage, ReportExecutionDescriptor.class, new String[]{REPORT_EXECUTIONS_URI, requestId})
                .get();
    }

    public <R> RequestExecution asyncExecutionDetails(final Callback<OperationResult<ReportExecutionDescriptor>, R> callback) {
        final JerseyRequest<ReportExecutionDescriptor> request =
                buildRequest(sessionStorage, ReportExecutionDescriptor.class, new String[]{REPORT_EXECUTIONS_URI, requestId});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ReportExecutionStatusEntity> cancelExecution() {
        ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class, new String[]{REPORT_EXECUTIONS_URI, requestId, STATUS_URI})
                .put(statusEntity);
    }

    public <R> RequestExecution asyncCancelExecution(final Callback<OperationResult<ReportExecutionStatusEntity>, R> callback) {
        final ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        final JerseyRequest<ReportExecutionStatusEntity> request =
                buildRequest(sessionStorage, ReportExecutionStatusEntity.class, new String[]{REPORT_EXECUTIONS_URI, requestId, STATUS_URI});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(statusEntity));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public ExportExecutionRequestBuilder export(String exportId) {
        return new ExportExecutionRequestBuilder(sessionStorage, requestId, exportId);
    }

    public OperationResult<ExportExecutionDescriptor> runExport(ExportExecutionOptions exportExecutionOptions) {
        return buildRequest(sessionStorage, ExportExecutionDescriptor.class, new String[]{REPORT_EXECUTIONS_URI, requestId, EXPORTS_URI})
                .post(exportExecutionOptions);
    }

    public <R> RequestExecution asyncRunExport(final ExportExecutionOptions exportExecutionOptions,
                                               final Callback<OperationResult<ExportExecutionDescriptor>, R> callback) {
        final JerseyRequest<ExportExecutionDescriptor> request =
                buildRequest(sessionStorage, ExportExecutionDescriptor.class, new String[]{REPORT_EXECUTIONS_URI, requestId, EXPORTS_URI});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(exportExecutionOptions));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

}
