package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;

public class SessionStorage {

    private RestClientConfiguration configuration;
    private AuthenticationCredentials credentials;
    private String sessionId;

    public RestClientConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(RestClientConfiguration configuration) {
        this.configuration = configuration;
    }

    public AuthenticationCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        if (sessionId != null)
            this.sessionId = sessionId;
    }
}
