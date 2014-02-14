package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

public class RunReportServiceTest extends Assert {

    private final JasperserverRestClient client;

    public RunReportServiceTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testRunReport() throws IOException {
        OperationResult<InputStream> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .reportingService()
                .report("/reports/samples/Cascading_multi_select_report")
                .prepareForRun(ReportOutputFormat.HTML, 1)
                .parameter("Cascading_name_single_select", "A & U Stalker Telecommunications, Inc")
                .run();
        InputStream report = result.getEntity();
        assertNotEquals(report, null);
    }

}
