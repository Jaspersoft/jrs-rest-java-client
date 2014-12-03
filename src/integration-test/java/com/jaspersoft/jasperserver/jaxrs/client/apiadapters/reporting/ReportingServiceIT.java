package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class ReportingServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeMethod
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_run_report_and_return_descriptor() {
        ReportExecutionRequest request = new ReportExecutionRequest()
                .setReportUnitUri("/public/Samples/Reports/01._Geographic_Results_by_Segment_Report")
                .setAsync(true)
                .setOutputFormat("html");

        ReportExecutionDescriptor entity = session.reportingService()
                .newReportExecutionRequest(request)
                .entity();
        assertNotNull(entity);
    }

    @AfterMethod
    public void after() {
        session.logout();
        session = null;
    }
}
