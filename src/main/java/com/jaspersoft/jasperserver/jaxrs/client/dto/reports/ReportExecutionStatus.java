package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import java.util.HashMap;
import java.util.Map;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@XmlRootElement(name = "reportExecution")
public class ReportExecutionStatus {

    private String requestId;
    private String reportURI;
    private Map<String, Object> propertyMap = new HashMap<>();

    public ReportExecutionStatus() {
    }

    public ReportExecutionStatus(ReportExecutionStatus other) {
        this.requestId = other.requestId;
        if (other.propertyMap != null) {
            for (String property : propertyMap.keySet()) {
                this.propertyMap.put(property, propertyMap.get(property));
            }
        }
        this.reportURI = other.reportURI;
    }

    public String getRequestId() {
        return requestId;
    }

    public ReportExecutionStatus setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getReportURI() {
        return reportURI;
    }

    public ReportExecutionStatus setReportURI(String reportURI) {
        this.reportURI = reportURI;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportExecutionStatus)) return false;

        ReportExecutionStatus that = (ReportExecutionStatus) o;

        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (reportURI != null ? !reportURI.equals(that.reportURI) : that.reportURI != null) return false;
        return propertyMap != null ? propertyMap.equals(that.propertyMap) : that.propertyMap == null;
    }

    @Override
    public int hashCode() {
        int result = requestId != null ? requestId.hashCode() : 0;
        result = 31 * result + (reportURI != null ? reportURI.hashCode() : 0);
        result = 31 * result + (propertyMap != null ? propertyMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportExecutionStatus{" +
                "requestId='" + requestId + '\'' +
                ", reportURI='" + reportURI + '\'' +
                ", propertyMap=" + propertyMap +
                '}';
    }
}