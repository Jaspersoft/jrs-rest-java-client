package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;

import javax.ws.rs.core.MediaType;

public class ReportExecutionRequestBuilder {

    private final SessionStorage sessionStorage;
    private final String requestId;

    public ReportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId){
        this.requestId = requestId;
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ReportExecutionStatusEntity> status(){
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(sessionStorage, ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        return builder.get();
    }

    public OperationResult<ReportExecutionDescriptor> executionDetails(){
        JerseyRequestBuilder<ReportExecutionDescriptor> builder =
                new JerseyRequestBuilder<ReportExecutionDescriptor>(sessionStorage, ReportExecutionDescriptor.class);
        builder.setPath("reportExecutions").setPath(requestId);
        builder.setAccept(MediaType.APPLICATION_JSON);
        return builder.get();
    }

    public OperationResult<ReportExecutionStatusEntity> cancelExecution(){
        JerseyRequestBuilder<ReportExecutionStatusEntity> builder =
                new JerseyRequestBuilder<ReportExecutionStatusEntity>(sessionStorage, ReportExecutionStatusEntity.class);
        builder.setPath("reportExecutions").setPath(requestId).setPath("status");
        ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        return builder.put(statusEntity);
    }

    public ExportExecutionRequestBuilder export(String exportOutput){
        return new ExportExecutionRequestBuilder(sessionStorage, requestId, exportOutput);
    }

}
