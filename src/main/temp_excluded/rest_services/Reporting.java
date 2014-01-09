package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.ReportExecutionRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class Reporting {

    private final AuthenticationCredentials credentials;

    public Reporting(AuthenticationCredentials credentials) {
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
                Reporting.newReportRequest(request);
        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        System.out.println(descriptor);
*/
        //Reporting.reportRequest(operationResult.getEntity().getRequestId()).export("text/html").status();
    }

}
