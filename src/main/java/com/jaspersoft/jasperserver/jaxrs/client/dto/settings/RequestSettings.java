package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestSettings that = (RequestSettings) o;

        if (maxInactiveInterval != null ? !maxInactiveInterval.equals(that.maxInactiveInterval) : that.maxInactiveInterval != null)
            return false;
        return !(contextPath != null ? !contextPath.equals(that.contextPath) : that.contextPath != null);

    }

    @Override
    public int hashCode() {
        int result = maxInactiveInterval != null ? maxInactiveInterval.hashCode() : 0;
        result = 31 * result + (contextPath != null ? contextPath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestSettings{" +
                "maxInactiveInterval=" + maxInactiveInterval +
                ", contextPath='" + contextPath + '\'' +
                '}';
    }
}
