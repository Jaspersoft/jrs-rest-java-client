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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.dashboard;

import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecution;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecutionListWrapper;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecutionStatus;
import com.jaspersoft.jasperserver.dto.dashboard.DashboardParameters;
import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;

public class DashboardExecutionsAdapter extends AbstractAdapter {

    public static final String DASHBOARD_EXECUTIONS_URI = "dashboardExecutions";
    public static final String STATUS_URI = "status";

    private DashboardExportExecution executionRequest;
    private String executionId;
    private final ArrayList<String> path = new ArrayList<>();

    public DashboardExecutionsAdapter(SessionStorage sessionStorage, DashboardExportExecution executionRequest) {
        super(sessionStorage);
        this.executionRequest = executionRequest;
        this.path.add(DASHBOARD_EXECUTIONS_URI);
    }

    public DashboardExecutionsAdapter(SessionStorage sessionStorage, String executionId) {
        super(sessionStorage);
        this.executionId = executionId;
        this.path.add(DASHBOARD_EXECUTIONS_URI);
    }

    public DashboardExecutionsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.path.add(DASHBOARD_EXECUTIONS_URI);
        this.executionRequest = new DashboardExportExecution();
    }

    public DashboardExecutionsAdapter outputFormat(DashboardExportExecution.ExportFormat outputFormat) {
        this.executionRequest.setFormat(outputFormat);
        return this;
    }

    public DashboardExecutionsAdapter dashboardUri(String dashboardUri) {
        this.executionRequest.setUri(dashboardUri);
        return this;
    }

    public DashboardExecutionsAdapter detailed(Boolean value) {
        this.executionRequest.setDetailed(value);
        return this;
    }

    public DashboardExecutionsAdapter dashboardParameters(DashboardParameters dashboardParameters) {
        this.executionRequest.setParameters(dashboardParameters);
        return this;
    }

    public DashboardExecutionsAdapter dashboardParameter(String name, String... values) {
        this.dashboardParameter(new ReportParameter().setName(name).setValues(asList(values)));
        return this;
    }

    public DashboardExecutionsAdapter dashboardParameter(ReportParameter reportParameter) {
        if (executionRequest.getParameters() == null) {
            executionRequest.setParameters(new DashboardParameters(new ArrayList<>()));
        }
        final DashboardParameters parameters = executionRequest.getParameters();
        List<ReportParameter> dashboardParametersList = parameters.getDashboardParameters();
        if (dashboardParametersList == null) {
            dashboardParametersList = new ArrayList<>();
        }
        dashboardParametersList.add(reportParameter);
        return this;
    }

    public DashboardOutputResourceAdapter outputResource() {
        return new DashboardOutputResourceAdapter(sessionStorage, executionId);
    }

    public OperationResult<DashboardExportExecutionListWrapper> search() {
        JerseyRequest<DashboardExportExecutionListWrapper> request = JerseyRequest.buildRequest(
                sessionStorage,
                DashboardExportExecutionListWrapper.class,
                path.toArray(new String[0]),
                new DefaultErrorHandler());
        return request.get();
    }

    public OperationResult<DashboardExportExecution> details() {
        path.add(executionId);
        JerseyRequest<DashboardExportExecution> request = JerseyRequest.buildRequest(
                sessionStorage,
                DashboardExportExecution.class,
                path.toArray(new String[0]),
                new DefaultErrorHandler());
        return request.get();
    }

    public OperationResult<DashboardExportExecutionStatus> status() {
        path.add(executionId);
        path.add(STATUS_URI);
        JerseyRequest<DashboardExportExecutionStatus> request = JerseyRequest.buildRequest(
                sessionStorage,
                DashboardExportExecutionStatus.class,
                path.toArray(new String[0]),
                new DefaultErrorHandler());

        return request.get();
    }

    public OperationResult<Object> delete() {
        path.add(executionId);
        JerseyRequest<Object> request = JerseyRequest.buildRequest(
                sessionStorage,
                Object.class,
                path.toArray(new String[0]),
                new DefaultErrorHandler());

        return request.delete();
    }

    public OperationResult<DashboardExportExecution> run() {
        JerseyRequest<DashboardExportExecution> jerseyRequest = buildRequest(sessionStorage,
                DashboardExportExecution.class,
                new String[]{DASHBOARD_EXECUTIONS_URI},
                new DefaultErrorHandler());
        return jerseyRequest
                .post(executionRequest);
    }

}