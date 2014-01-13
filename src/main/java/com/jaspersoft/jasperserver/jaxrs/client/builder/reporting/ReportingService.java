package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class ReportingService {

    private final AuthenticationCredentials credentials;

    public ReportingService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public OperationResult<ReportExecutionDescriptor> newReportRequest(ReportExecutionRequest request) {
        JerseyRequestBuilder<ReportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ReportExecutionDescriptor>(credentials, ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions");
        return builder.post(request);
    }

    public ReportExecutionRequestBuilder reportRequest(String requestId) {
        return new ReportExecutionRequestBuilder(credentials, requestId);
    }


    public static void main(String[] args) {
        /*ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                ReportingService.newReportRequest(request);
        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        System.out.println(descriptor);
*/
        //ReportingService.reportRequest(operationResult.getEntity().getRequestId()).export("text/html").status();
    }

}
