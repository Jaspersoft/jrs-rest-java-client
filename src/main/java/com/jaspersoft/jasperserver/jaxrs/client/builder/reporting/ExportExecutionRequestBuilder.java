package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import java.io.File;
import java.io.InputStream;

public class ExportExecutionRequestBuilder {

    private final AuthenticationCredentials credentials;
    private String requestId;
    private String exportOutput;

    public ExportExecutionRequestBuilder(AuthenticationCredentials credentials, String requestId, String exportOutput){
        this.requestId = requestId;
        this.exportOutput = exportOutput;
        this.credentials = credentials;
    }

    public OperationResult<InputStream> outputResource(){
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(credentials, InputStream.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("exports")
                .setPath(exportOutput)
                .setPath("outputResource");
        return builder.get();
    }

    public OperationResult<InputStream> attachment(String attachmentId){
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(credentials, InputStream.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("exports")
                .setPath(exportOutput)
                .setPath("attachments")
                .setPath(attachmentId);
        return builder.get();
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(credentials, ReportExecutionStatusEntity.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("exports")
                .setPath(exportOutput)
                .setPath("status");
        return builder.get();
    }
}
