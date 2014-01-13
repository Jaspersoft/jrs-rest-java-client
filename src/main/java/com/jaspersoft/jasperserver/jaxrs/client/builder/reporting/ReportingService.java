package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class ReportingService {

    private static String sessionId;

    private final AuthenticationCredentials credentials;

    public ReportingService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public OperationResult<ReportExecutionDescriptor> newReportRequest(ReportExecutionRequest request) {
        JerseyRequestBuilder<ReportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ReportExecutionDescriptor>(credentials, ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions");
        OperationResult<ReportExecutionDescriptor> descriptor = builder.post(request);
        sessionId = descriptor.getSessionId();
        return descriptor;
    }

    public ReportExecutionRequestBuilder reportRequest(String requestId) {
        credentials.setSessionId(sessionId);
        return new ReportExecutionRequestBuilder(credentials, requestId);
    }

}
