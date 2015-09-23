package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Tetiana Iefimenko
 */
public abstract class RestClientTestUtil {
    protected  RestClientConfiguration configuration;
    protected  JasperserverRestClient client;
    protected Session session;

    protected void initClient() {
        configuration = RestClientConfiguration.loadConfiguration("test_config.properties");
        client = new JasperserverRestClient(configuration);
    }

    protected void initSession() {
        session = client.authenticate("superuser", "superuser");
    }
}
