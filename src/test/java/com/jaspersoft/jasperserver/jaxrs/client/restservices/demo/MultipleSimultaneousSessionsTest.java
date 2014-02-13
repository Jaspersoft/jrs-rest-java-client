package com.jaspersoft.jasperserver.jaxrs.client.restservices.demo;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionRequest;
import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

public class MultipleSimultaneousSessionsTest extends Assert {

    private Runnable reportingTask;

    public MultipleSimultaneousSessionsTest(){
        reportingTask = new Runnable() {

            private Session session;
            private ReportExecutionDescriptor reportExecutionDescriptor;

            private void init(){
                RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
                JasperserverRestClient client = new JasperserverRestClient(configuration);
                session = client.authenticate("jasperadmin", "jasperadmin");
            }

            public void createNewReportRequest(){

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

            public void getReportExecutionStatus(){
                OperationResult<ReportExecutionStatusEntity> operationResult =
                        session
                                .reportingService()
                                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                                .status();

                ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
                assertNotEquals(statusEntity, null);
            }

            public void getReportExecutionDetails(){
                OperationResult<ReportExecutionDescriptor> operationResult =
                        session
                                .reportingService()
                                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                                .executionDetails();

                ReportExecutionDescriptor descriptor = operationResult.getEntity();
                assertNotEquals(descriptor, null);
            }

            public void getExportOutputResource(){
                OperationResult<InputStream> operationResult =
                        session
                                .reportingService()
                                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                                .export("html")
                                .outputResource();

                InputStream file = operationResult.getEntity();
                assertNotEquals(file, null);
            }

            public void getExportAttachment() throws InterruptedException {
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

            public void getReportExportStatus(){
                OperationResult<ReportExecutionStatusEntity> operationResult =
                        session
                                .reportingService()
                                .reportExecutionRequest(reportExecutionDescriptor.getRequestId())
                                .export("html")
                                .status();

                ReportExecutionStatusEntity statusEntity = operationResult.getEntity();
                assertNotEquals(statusEntity, null);
            }

            @Override
            public void run() {
                try {
                    init();
                    createNewReportRequest();
                    getReportExecutionStatus();
                    getReportExecutionDetails();
                    getExportOutputResource();
                    getExportAttachment();
                    getReportExportStatus();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void testRunThreeSessions() throws InterruptedException {
        for (int i = 0; i < 3; i++){
            Thread thread = new Thread(reportingTask);
            thread.start();
            thread.join();
        }

    }
}
