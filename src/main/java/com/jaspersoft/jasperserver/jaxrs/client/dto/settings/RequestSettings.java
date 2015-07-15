package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 * Created by tetiana.iefimenko on 7/15/2015.
 */
public class RequestSettings {

    private Integer maxInactiveInterval;
    private String contextPath;

    public Integer getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(Integer maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
