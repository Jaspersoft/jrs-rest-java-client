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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportexecution.ReportExecutionAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class ReportingService extends AbstractAdapter {

    public static final String REPORT_EXECUTIONS_URI = "reportExecutions";

    public ReportingService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }
@Deprecated
    public OperationResult<ReportExecutionDescriptor> newReportExecutionRequest(ReportExecutionRequest request) {
        JerseyRequest<ReportExecutionDescriptor> jerseyRequest = buildRequest(sessionStorage,
                ReportExecutionDescriptor.class,
                new String[]{REPORT_EXECUTIONS_URI});
        if (request.getTimeZone() != null) {
            jerseyRequest.addHeader("Accept-Timezone", request.getTimeZone().getID());
        }
        return jerseyRequest
                .post(request);
    }
    @Deprecated
    public <R> RequestExecution asyncNewReportExecutionRequest(final ReportExecutionRequest reportExecutionRequest, final Callback<OperationResult<ReportExecutionDescriptor>, R> callback) {
        final JerseyRequest<ReportExecutionDescriptor> request = buildRequest(sessionStorage, ReportExecutionDescriptor.class, new String[]{REPORT_EXECUTIONS_URI});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(reportExecutionRequest));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
    @Deprecated
    public ReportExecutionRequestBuilder reportExecutionRequest(String requestId) {
        return new ReportExecutionRequestBuilder(sessionStorage, requestId);
    }
    @Deprecated
    public ReportsAndJobsSearchAdapter runningReportsAndJobs() {
        return new ReportsAndJobsSearchAdapter(sessionStorage);
    }

    public ReportsAdapter report(String reportUnitUri) {
        return new ReportsAdapter(sessionStorage, reportUnitUri);
    }

    public ReportExecutionAdapter reportExecutions() {
        return new ReportExecutionAdapter(sessionStorage);
    }

    public ReportExecutionAdapter reportExecution(ReportExecutionRequest reportExecution) {
        return new ReportExecutionAdapter(sessionStorage, reportExecution);
    }

    public ReportExecutionAdapter reportExecution(String executionId) {
        return new ReportExecutionAdapter(sessionStorage, executionId);
    }

}
