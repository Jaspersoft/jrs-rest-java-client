package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.ReportExecutionRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class Reporting {

    public static OperationResult<ReportExecutionDescriptor> newReportRequest(ReportExecutionRequest request) {
        RequestBuilder<ReportExecutionRequest, ReportExecutionDescriptor> builder =
                new RequestBuilder<ReportExecutionRequest,
                        ReportExecutionDescriptor>(ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions");
        return builder.post(request);
    }

    public static ReportExecutionRequestBuilder reportRequest(String requestId) {
        return new ReportExecutionRequestBuilder(requestId);
    }


    public static void main(String[] args) {
        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                Reporting.newReportRequest(request);
        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        System.out.println(descriptor);

        //Reporting.reportRequest(operationResult.getEntity().getRequestId()).export("text/html").status();
    }

}
