package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

public class ReportExecutionRequestBuilder {

    private String requestId;

    public ReportExecutionRequestBuilder(String requestId){
        this.requestId = requestId;
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        RequestBuilder<Object, ReportExecutionStatusEntity> builder =
                new RequestBuilder<Object, ReportExecutionStatusEntity>(ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        return builder.get();
    }

    public OperationResult<ReportExecutionDescriptor> executionDetails(){
        RequestBuilder<Object, ReportExecutionDescriptor> builder =
                new RequestBuilder<Object, ReportExecutionDescriptor>(ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions").setPath(requestId);
        return builder.get();
    }

    public OperationResult<ReportExecutionStatusEntity> cancelExecution(){
        RequestBuilder<ReportExecutionStatusEntity, ReportExecutionStatusEntity> builder =
                new RequestBuilder<ReportExecutionStatusEntity,
                        ReportExecutionStatusEntity>(ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        return builder.put(statusEntity);
    }

    public ExportExecutionRequestBuilder export(String exportOutput){
        return new ExportExecutionRequestBuilder(requestId, exportOutput);
    }

}
