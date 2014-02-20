package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class BundlesServiceTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testGetBundles() throws Exception {
        OperationResult<List<String>> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .bundlesService()
                .bundles();

        List<String> bundlesList = result.getEntity();
        assertNotNull(bundlesList);
    }

    @Test
    public void testGetBundle() throws Exception {
        OperationResult<Map<String,String>> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .bundlesService()
                .bundle("jasperserver_messages");

        Map<String, String> bundlesList = result.getEntity();
        assertNotNull(bundlesList);
    }
}
