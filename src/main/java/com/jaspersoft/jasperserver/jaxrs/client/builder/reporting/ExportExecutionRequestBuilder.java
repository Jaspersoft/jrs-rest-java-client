package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import java.io.InputStream;

public class ExportExecutionRequestBuilder {

    private final SessionStorage sessionStorage;
    private String requestId;
    private String exportOutput;

    public ExportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId, String exportOutput){
        this.requestId = requestId;
        this.exportOutput = exportOutput;
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<InputStream> outputResource(){
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(sessionStorage, InputStream.class);
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
                new JerseyRequestBuilder<InputStream>(sessionStorage, InputStream.class);
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
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(sessionStorage, ReportExecutionStatusEntity.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("exports")
                .setPath(exportOutput)
                .setPath("status");
        return builder.get();
    }
}
