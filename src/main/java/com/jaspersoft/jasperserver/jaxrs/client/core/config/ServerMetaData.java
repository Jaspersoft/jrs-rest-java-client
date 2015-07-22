package com.jaspersoft.jasperserver.jaxrs.client.core.config;

import com.jaspersoft.jasperserver.jaxrs.client.core.JRSVersion;

/**
 * @author Alexander Krasnyanskiy
 */
public class ServerMetaData {
    private String url;
    private JRSVersion version;
    private Headers headers;
    private Timeout timeout;
    private String authenticationType;

    public String getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JRSVersion getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = convert(version);
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Timeout getTimeout() {
        return timeout;
    }

    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }

    JRSVersion convert(String version) {
        return JRSVersion.valueOf("v" + version.replace(".", "_"));
    }
    
    public static class AuthenticationType {
    	public static final String SPRING = "spring";
    	public static final String REST = "rest";
    }
}
