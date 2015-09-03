package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import java.io.InputStream;
import junit.framework.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 */
public class ReportingServiceIT {

    private RestClientConfiguration configuration;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        configuration = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        client = new JasperserverRestClient(configuration);
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    public void should_return_proper_entity_if_pass_pdf_report_output_format() {

        /** When **/
        OperationResult<InputStream> result = session
                .reportingService()
                .report("/organizations/organization_1/adhoc/topics/Cascading_multi_select_topic")
                .prepareForRun(ReportOutputFormat.PDF, 1)
                .parameter("Cascading_state_multi_select", "CA")
                .parameter("Cascading_state_multi_select", "OR", "WA")
                .parameter("Cascading_name_single_select", "Adams-Steen Transportation Holdings")
                .parameter("Country_multi_select", "USA")
                .run();

        InputStream entity = result.getEntity();
        /** Then **/
        Assert.assertNotNull(entity);

    }

    @Test
    public void should_return_proper_entity_without_numbers_of_pages() {

        /** When **/
        OperationResult<InputStream> result = session
                .reportingService()
                .report("/organizations/organization_1/adhoc/topics/Cascading_multi_select_topic")
                .prepareForRun(ReportOutputFormat.PDF)
                .parameter("Cascading_state_multi_select", "CA")
                .parameter("Cascading_state_multi_select", "OR", "WA")
                .parameter("Cascading_name_single_select", "Adams-Steen Transportation Holdings")
                .parameter("Country_multi_select", "USA")
                .run();

        InputStream entity = result.getEntity();
        /** Then **/
        Assert.assertNotNull(entity);

    }

    @Test
    public void should_return_proper_entity_in_async_mode() {

        /** When **/
        ReportExecutionRequest request = new ReportExecutionRequest();
        request.setReportUnitUri("/organizations/organization_1/adhoc/topics/Cascading_multi_select_topic");
        request
                .setAsync(true)
                .setOutputFormat(ReportOutputFormat.HTML);

        OperationResult<ReportExecutionDescriptor> operationResult = session
                .reportingService()
                .newReportExecutionRequest(request);

        ReportExecutionDescriptor reportExecutionDescriptor = operationResult.getEntity();
        /** Then **/
        Assert.assertNotNull(reportExecutionDescriptor);
    }

    @AfterMethod
    public void after() {
        client = null;
        configuration = null;
    }
}