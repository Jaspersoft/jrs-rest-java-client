package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class ClientConfigurationFactory {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;


    protected ClientConfigurationFactory() {

    }
}
