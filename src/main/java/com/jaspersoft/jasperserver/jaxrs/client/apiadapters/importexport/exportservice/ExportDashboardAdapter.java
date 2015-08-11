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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecution;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecutionStatus;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardParameters;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

/**
 * Created by serhii.blazhyievskyi on 7/31/2015.
 */
public class ExportDashboardAdapter extends AbstractAdapter {

    private static final Log log = LogFactory.getLog(ExportDashboardAdapter.class);

    private String dashboardUri;
    private String jobId;
    private DashboardExportExecution dashboardExportExecution = new DashboardExportExecution();

    public ExportDashboardAdapter(SessionStorage sessionStorage, String dashboardUri, String jobId) {
        super(sessionStorage);
        this.dashboardUri = dashboardUri;
        this.jobId = jobId;
        this.dashboardExportExecution.setParameters(new DashboardParameters());
    }

    public ExportDashboardAdapter parameters(DashboardParameters parameters) {
        this.dashboardExportExecution.setParameters(parameters);
        return this;
    }

    public OperationResult<InputStream> exportReport() {
        JerseyRequest<InputStream> request = buildRequest(sessionStorage, InputStream.class, new String[]{"/dashboards", dashboardUri, "/exportFormat"});
        request.setAccept("application/zip");
        return request.get();
    }

    public OperationResult<DashboardExportExecution> getAllJobs() {
        return buildRequest(sessionStorage, DashboardExportExecution.class, new String[]{"/dashboardExecutions"}).get();
    }

    public OperationResult<DashboardExportExecution> getJob() {
        return buildRequest(sessionStorage, DashboardExportExecution.class, new String[]{"/dashboardExecutions", jobId}).get();
    }

    public OperationResult<DashboardExportExecution> create(DashboardExportExecution param) {
        return buildRequest(sessionStorage, DashboardExportExecution.class, new String[]{"/dashboardExecutions"},
                new DefaultErrorHandler()).post(param);
    }

    public OperationResult<DashboardExportExecutionStatus> getJobStatus() {
        return buildRequest(sessionStorage, DashboardExportExecutionStatus.class, new String[]{"/dashboardExecutions", jobId, "/status"}).get();
    }

    public OperationResult<InputStream> getExportResult() {
        JerseyRequest<InputStream> request = buildRequest(sessionStorage, InputStream.class, new String[]{"/dashboardExecutions", jobId, "/outputResource"});
        request.setAccept("application/zip");
        return request.get();
    }

    public OperationResult<DashboardExportExecution> deleteJob() {
        return buildRequest(sessionStorage, DashboardExportExecution.class, new String[]{"/dashboardExecutions", jobId}).delete();
    }
}
