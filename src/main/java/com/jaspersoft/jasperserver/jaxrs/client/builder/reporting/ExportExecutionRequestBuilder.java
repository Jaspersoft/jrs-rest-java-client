package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;

import java.io.File;

public class ExportExecutionRequestBuilder {

    private String requestId;
    private String exportOutput;

    public ExportExecutionRequestBuilder(String requestId, String exportOutput){
        this.requestId = requestId;
        this.exportOutput = exportOutput;
    }

    public OperationResult<File> outputResource(){
        RequestBuilder<Object, File> builder = new RequestBuilder<Object, File>(File.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("export")
                .setPath(exportOutput)
                .setPath("outputResource");
        return builder.get();
    }

    public OperationResult<File> attachment(String attachmentId){
        RequestBuilder<Object, File> builder = new RequestBuilder<Object, File>(File.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("export")
                .setPath(exportOutput)
                .setPath("attachments")
                .setPath(attachmentId);
        return builder.get();
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        RequestBuilder<Object, ReportExecutionStatusEntity> builder =
                new RequestBuilder<Object, ReportExecutionStatusEntity>(ReportExecutionStatusEntity.class);
        builder
                .setPath("reportExecutions")
                .setPath(requestId)
                .setPath("export")
                .setPath(exportOutput)
                .setPath("status");
        return builder.get();
    }
}
