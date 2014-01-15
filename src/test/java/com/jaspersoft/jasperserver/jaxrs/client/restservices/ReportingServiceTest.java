package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.AttachmentDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.ReportExecutionDescriptor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;

public class ReportingServiceTest extends Assert {

    private static Session session;
    private ReportExecutionDescriptor reportExecutionDescriptor;


    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        session = client.authenticate("jasperadmin", "jasperadmin");
    }

    @Test(priority = 0)
    public void testCreateNewReportRequest(){
        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .newReportRequest(request);

        reportExecutionDescriptor = operationResult.getEntity();

        assertNotEquals(reportExecutionDescriptor, null);
    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"}, priority = 1)
    public void testGetReportExecutionStatus(){
        OperationResult<ReportExecutionStatusEntity> operationResult =
                session
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionStatus"}, priority = 2)
    public void testGetReportExecutionDetails(){
        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .executionDetails();

        ReportExecutionDescriptor descriptor = operationResult.getEntity();
        assertNotEquals(descriptor, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionDetails"}, priority = 3)
    public void testGetExportOutputResource(){
        OperationResult<InputStream> operationResult =
                session
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .export("html")
                        .outputResource();

        InputStream file = operationResult.getEntity();
        assertNotEquals(file, null);
    }

    @Test(dependsOnMethods = {"testGetExportOutputResource"}, priority = 4)
    public void testGetExportAttachment() throws InterruptedException {
        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .executionDetails();

        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        OperationResult<ReportExecutionStatusEntity> operationResultStatus =
                session
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

                OperationResult<InputStream> operationResult1 =
                        session
                                .reportingService()
                                .reportRequest(reportExecutionDescriptor.getRequestId())
                                .export(exportDescriptor.getId())
                                .attachment(fileName);

                InputStream file = operationResult1.getEntity();
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
                session
                        .reportingService()
                        .reportRequest(reportExecutionDescriptor.getRequestId())
                        .export("html")
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

}
