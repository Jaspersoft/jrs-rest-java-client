package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class ReportingService {

    private final SessionStorage sessionStorage;

    public ReportingService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ReportExecutionDescriptor> newReportRequest(ReportExecutionRequest request) {
        JerseyRequestBuilder<ReportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ReportExecutionDescriptor>(sessionStorage, ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions");
        OperationResult<ReportExecutionDescriptor> descriptor = builder.post(request);
        sessionStorage.setSessionId(descriptor.getSessionId());
        return descriptor;
    }

    public ReportExecutionRequestBuilder reportRequest(String requestId) {

        return new ReportExecutionRequestBuilder(sessionStorage, requestId);
    }

}
