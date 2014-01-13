package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class ReportExecutionRequestBuilder {

    private final AuthenticationCredentials credentials;
    private final String requestId;

    public ReportExecutionRequestBuilder(AuthenticationCredentials credentials, String requestId){
        this.requestId = requestId;
        this.credentials = credentials;
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(credentials, ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        return builder.get();
    }

    public OperationResult<ReportExecutionDescriptor> executionDetails(){
        JerseyRequestBuilder<ReportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ReportExecutionDescriptor>(credentials, ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions").setPath(requestId);
        return builder.get();
    }

    public OperationResult<ReportExecutionStatusEntity> cancelExecution(){
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(credentials, ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        return builder.put(statusEntity);
    }

    public ExportExecutionRequestBuilder export(String exportOutput){
        return new ExportExecutionRequestBuilder(credentials, requestId, exportOutput);
    }

}
