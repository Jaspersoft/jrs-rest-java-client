package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import junit.framework.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;

public class ReportingServiceIT {

    private RestClientConfiguration configuration;
    private JasperserverRestClient client;

    @BeforeMethod
    public void before() {
        configuration = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void should_return_proper_entity_if_pass_jrprint_report_output_format() {

        /** When **/
        OperationResult<InputStream> result = client
                .authenticate("superuser", "superuser")
                .reportingService()
                .report("/public/Samples/Reports/08g.UnitSalesDetailReport")
                .prepareForRun(ReportOutputFormat.JRPRINT, 1)
                .run();

        InputStream entity = result.getEntity();

        /** Then **/
        Assert.assertNotNull(entity);
    }

    @AfterMethod
    public void after() {
        client = null;
        configuration = null;
    }
}