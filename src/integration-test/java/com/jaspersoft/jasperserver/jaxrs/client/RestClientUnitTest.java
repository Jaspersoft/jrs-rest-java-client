package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;

/**
 * @author Tetiana Iefimenko
 */
public abstract class RestClientUnitTest {
    protected RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("test_config.properties");
    protected JasperserverRestClient client = new JasperserverRestClient(configuration);
}
