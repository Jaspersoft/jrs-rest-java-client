package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import junit.framework.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.org.mozilla.javascript.internal.ast.WhileLoop;

import java.io.*;

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
                .report("/organizations/organization_1/adhoc/topics/Cascading_multi_select_topic")
                .prepareForRun(ReportOutputFormat.PDF, 1)
                .parameter("Cascading_state_multi_select", "CA", "OR", "WA")
                .parameter("Cascading_name_single_select", "Adams-Steen Transportation Holdings")
                .parameter("Country_multi_select", "USA")
                .run();

        InputStream entity = result.getEntity();
        /** Then **/
        Assert.assertNotNull(entity);

        /*
        OutputStream output = null;
        try {
            output = new FileOutputStream("file.pdf");
            int i = 0;
            while (i != -1) {
                i = entity.read();
                output.write(i);
                output.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                entity.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

    @AfterMethod
    public void after() {
        client = null;
        configuration = null;
    }
}