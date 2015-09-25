package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Tetiana Iefimenko
 */
public abstract class RestClientTestUtil {
    protected RestClientConfiguration configuration;
    protected JasperserverRestClient client;
    protected Session session;
    protected Properties properties;

    protected void initClient() {
        loadTestProperties("test_config.properties");
        configuration = RestClientConfiguration.loadConfiguration(properties);
        client = new JasperserverRestClient(configuration);
    }

    protected void initSession() {
        session = client.authenticate(properties.getProperty("username"), properties.getProperty("password"));
    }

    private void loadTestProperties(String path) {
        properties = new Properties();
        InputStream is;
        try {
            is = RestClientConfiguration.class.getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new IOException("Property file is not found");
            }
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
