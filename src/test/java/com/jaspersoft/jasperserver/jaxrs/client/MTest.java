package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportOutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.annotations.Test;

import java.io.InputStream;

/**
 * @author Alex Krasnyanskiy
 */
public class MTest {
    
    @Test
    public void should_do_magic() {
        
        RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        
        OperationResult<InputStream> result = client
                .authenticate("superuser", "superuser")
                .reportingService()
                .report("/public/Samples/Reports/08g.UnitSalesDetailReport")
                .prepareForRun(ReportOutputFormat.JRPRINT, 1)
                .run();
    }
}
