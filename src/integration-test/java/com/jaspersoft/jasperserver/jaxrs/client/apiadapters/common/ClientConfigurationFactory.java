package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;

public abstract class ClientConfigurationFactory {

    /**
     * Default credentials
     */
    private String username = "superuser";
    private String password = "superuser";

    /**
     * Creates {@link Session} based on {@link ConfigType}.
     * Possible options: YML, JSON, XML
     * @param type given ConfigType
     * @return session
     */
    protected Session getClientSession(ConfigType type) {
        return new JasperserverRestClient(new RestClientConfiguration(type))
                .authenticate(username, password);
    }

    /**
     * creates {@link Session} based on URL and default configuration parameters.
     * @param url JasperReportServer URL
     * @return session
     */
    protected Session getClientSession(String url) {
        return new JasperserverRestClient(new RestClientConfiguration(url))
                .authenticate(username, password);
    }


    /**
     * Setters
     */
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
