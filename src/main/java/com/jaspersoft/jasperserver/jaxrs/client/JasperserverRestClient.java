package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.Session;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class JasperserverRestClient {

    private Session session;
    private SessionStorage sessionStorage;

    public JasperserverRestClient(RestClientConfiguration configuration){
        this.sessionStorage = new SessionStorage();

        if (configuration == null)
            throw new IllegalArgumentException("You must define the configuration");

        sessionStorage.setConfiguration(configuration);
    }

    public Session authenticate(String username, String password){
        AuthenticationCredentials credentials = new AuthenticationCredentials(username, password);
        sessionStorage.setCredentials(credentials);
        return new Session(sessionStorage);
    }

}
