package com.jaspersoft.jasperserver.jaxrs.client.builder;

public class AuthenticationCredentials {

    private String username;
    private String password;
    private String sessionId;

    public AuthenticationCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationCredentials(String username, String password, String sessionId) {
        this.username = username;
        this.password = password;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
