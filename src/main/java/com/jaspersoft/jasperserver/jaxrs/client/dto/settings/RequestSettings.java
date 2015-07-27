package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 *  @author Tetiana Iefimenko
 */
public class RequestSettings {

    private Integer maxInactiveInterval;
    private String contextPath;

    public RequestSettings() {
    }

    public RequestSettings(RequestSettings other) {
        this.maxInactiveInterval = other.maxInactiveInterval;
        this.contextPath = other.contextPath;
    }

    public Integer getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public RequestSettings setMaxInactiveInterval(Integer maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
        return this;
    }

    public String getContextPath() {
        return contextPath;
    }

    public RequestSettings setContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestSettings)) return false;

        RequestSettings that = (RequestSettings) o;

        if (getMaxInactiveInterval() != null ? !getMaxInactiveInterval().equals(that.getMaxInactiveInterval()) : that.getMaxInactiveInterval() != null)
            return false;
        return !(getContextPath() != null ? !getContextPath().equals(that.getContextPath()) : that.getContextPath() != null);

    }

    @Override
    public int hashCode() {
        int result = getMaxInactiveInterval() != null ? getMaxInactiveInterval().hashCode() : 0;
        result = 31 * result + (getContextPath() != null ? getContextPath().hashCode() : 0);
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
