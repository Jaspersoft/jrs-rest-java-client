package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionOptions;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
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
    public void testCreateNewReportRequest() {
        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .newReportExecutionRequest(request);

        reportExecutionDescriptor = operationResult.getEntity();

        assertNotEquals(reportExecutionDescriptor, null);
    }

    @Test(dependsOnMethods = {"testCreateNewReportRequest"}, priority = 1)
    public void testGetReportExecutionStatus() {
        OperationResult<ReportExecutionStatusEntity> operationResult =
                session
                        .reportingService()
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionStatus"}, priority = 2)
    public void testGetReportExecutionDetails() {
        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                        .executionDetails();

        ReportExecutionDescriptor descriptor = operationResult.getEntity();
        assertNotEquals(descriptor, null);
    }

    @Test(dependsOnMethods = {"testGetReportExecutionDetails"}, priority = 3)
    public void testGetExportOutputResource() {
        OperationResult<InputStream> operationResult =
                session
                        .reportingService()
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
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
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                        .executionDetails();

        ReportExecutionDescriptor descriptor = operationResult.getEntity();

        ExportDescriptor exportDescriptor = descriptor.getExports().get(0);
        String fileName = exportDescriptor.getAttachments().get(0).getFileName();

        OperationResult<InputStream> operationResult1 =
                session
                        .reportingService()
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                        .export(exportDescriptor.getId())
                        .attachment(fileName);

        InputStream file = operationResult1.getEntity();
        assertNotEquals(file, null);
    }

    @Test(dependsOnMethods = {"testGetExportAttachment"}, priority = 5)
    public void testRunNewExport() {
        ExportExecutionOptions exportExecutionOptions = new ExportExecutionOptions()
                .setOutputFormat("pdf")
                .setPages("3");

        OperationResult<ExportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                        .runExport(exportExecutionOptions);

        ExportExecutionDescriptor statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

    @Test(dependsOnMethods = {"testGetExportAttachment"})
    public void testGetReportExportStatus() {
        OperationResult<ReportExecutionStatusEntity> operationResult =
                session
                        .reportingService()
                        .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                        .export("html")
                        .status();

        ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
        assertNotEquals(statusEntity, null);
    }

    @Test(dependsOnMethods = {"testGetExportAttachment"})
    public void testCancelRequestExecution() {

        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/StandardChartsReport");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .newReportExecutionRequest(request);

        ReportExecutionDescriptor executionDescriptor = operationResult.getEntity();

        OperationResult<ReportExecutionStatusEntity> operationResult1 =
                session
                        .reportingService()
                        .reportExecutionRequest(executionDescriptor.getRequestId())
                        .cancelExecution();

        ReportExecutionStatusEntity statusEntity = operationResult1.getEntity();
        assertNotEquals(statusEntity, null);
        assertEquals(statusEntity.getValue(), "cancelled");
    }

    /**
     * Very unstable test. Its correctness depends on server's speed.
     */
    /*@Test(dependsOnMethods = {"testCancelRequestExecution"})
    public void testFindRunningReportsAndJobs() {

        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/reports/samples/AllAccounts");
        request
                .setAsync(true)
                .setOutputFormat("html");

        OperationResult<ReportExecutionDescriptor> operationResult =
                session
                        .reportingService()
                        .newReportExecutionRequest(request);

        OperationResult<ReportExecutionListWrapper> operationResult1 =
                session
                        .reportingService()
                        .runningReportsAndJobs()
                        .parameter(ReportAndJobSearchParameter.REPORT_URI, "/reports/samples/AllAccounts")
                        .find();

        ReportExecutionListWrapper entity = operationResult1.getEntity();
        assertNotEquals(entity, null);
        assertEquals(entity.getReportExecutions().size(), 1);
    }*/


}
