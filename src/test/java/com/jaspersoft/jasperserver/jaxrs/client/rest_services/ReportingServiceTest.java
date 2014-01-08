package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.AttachmentDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class ReportingServiceTest extends Assert {

    private ReportExecutionDescriptor reportExecutionDescriptor;

    @Test
    public void testCreateNewReportRequest(){
        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                Reporting.newReportRequest(request);
        reportExecutionDescriptor = operationResult.getEntity();

        assertNotEquals(reportExecutionDescriptor, null);
    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"})
    public void testGetReportExecutionStatus(){
        OperationResult<ReportExecutionStatusEntity> operationResult =
                Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).status();
        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();

        assertNotEquals(statusEntity, null);
    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"})
    public void testGetReportExecutionDetails(){
        OperationResult<ReportExecutionDescriptor> operationResult =
                Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).executionDetails();
        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        assertNotEquals(descriptor, null);
    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"})
    public void testGetExportOutputResource(){
        OperationResult<File> operationResult =
                Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).export("text/html").outputResource();
        File file = operationResult.getEntity();

        assertNotEquals(file, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionDetails", "testGetReportExecutionStatus"})
    public void testGetExportAttachment() throws InterruptedException {
        OperationResult<ReportExecutionDescriptor> operationResult =
                Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).executionDetails();
        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        OperationResult<ReportExecutionStatusEntity> operationResultStatus =
                Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).status();
        ReportExecutionStatusEntity statusEntity = operationResultStatus.getEntity();

        while (true){

            if (statusEntity.getValue().equals("ready")){
                AttachmentDescriptor attachmentDescriptor = descriptor.getExports().get(0).getAttachments().get(0);
                String contentType = attachmentDescriptor.getContentType();
                String fileName = attachmentDescriptor.getFileName();

                OperationResult<File> operationResult1 =
                        Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).export(contentType).attachment(fileName);
                File file = operationResult1.getEntity();

                assertNotEquals(file, null);
            }
            else {
                Thread.sleep(500);
            }
        }

    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"})
    public void testGetReportExportStatus(){
        OperationResult<ReportExecutionStatusEntity> operationResult =
                Reporting.reportRequest(reportExecutionDescriptor.getRequestId()).export("text/html").status();
        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();

        assertNotEquals(statusEntity, null);
    }

}
