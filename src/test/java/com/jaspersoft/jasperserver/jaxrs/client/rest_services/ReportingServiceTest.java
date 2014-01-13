package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.AttachmentDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class ReportingServiceTest extends Assert {

    private ReportExecutionDescriptor reportExecutionDescriptor;
    private String sessionId;

    @Test(priority = 0)
    public void testCreateNewReportRequest(){
        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin")
                        .reportingService()
                        .newReportRequest(request);

        reportExecutionDescriptor = operationResult.getEntity();
        sessionId = operationResult.getSessionId();

        assertNotEquals(reportExecutionDescriptor, null);
    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"}, priority = 1)
    public void testGetReportExecutionStatus(){
        OperationResult<ReportExecutionStatusEntity> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin", sessionId)
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionStatus"}, priority = 2)
    public void testGetReportExecutionDetails(){
        OperationResult<ReportExecutionDescriptor> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin", sessionId)
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .executionDetails();

        ReportExecutionDescriptor descriptor = operationResult.getEntity();
        assertNotEquals(descriptor, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionDetails"}, priority = 3)
    public void testGetExportOutputResource(){
        OperationResult<File> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin", sessionId)
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .export("html")
                        .outputResource();

        File file = operationResult.getEntity();
        assertNotEquals(file, null);
    }

    @Test(dependsOnMethods = {"testGetExportOutputResource"}, priority = 4)
    public void testGetExportAttachment() throws InterruptedException {
        OperationResult<ReportExecutionDescriptor> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin", sessionId)
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .executionDetails();

        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        OperationResult<ReportExecutionStatusEntity> operationResultStatus =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin", sessionId)
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResultStatus.getEntity();

        while (true){

            if (statusEntity.getValue().equals("ready")){
                ExportDescriptor exportDescriptor = descriptor.getExports().get(0);
                AttachmentDescriptor attachmentDescriptor = exportDescriptor.getAttachments().get(0);
                String contentType = attachmentDescriptor.getContentType();
                String fileName = attachmentDescriptor.getFileName();

                OperationResult<File> operationResult1 =
                        JasperserverRestClient
                                .authenticate("jasperadmin", "jasperadmin", sessionId)
                                .reportingService()
                                .reportRequest(reportExecutionDescriptor.getRequestId())
                                .export(exportDescriptor.getId())
                                .attachment(fileName);

                File file = operationResult1.getEntity();
                assertNotEquals(file, null);
                break;
            }
            else {
                Thread.sleep(500);
            }
        }

    }

    @Test(dependsOnMethods = {"testGetExportAttachment"}, priority = 5)
    public void testGetReportExportStatus(){
        OperationResult<ReportExecutionStatusEntity> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin", sessionId)
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .export("html")
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

}
