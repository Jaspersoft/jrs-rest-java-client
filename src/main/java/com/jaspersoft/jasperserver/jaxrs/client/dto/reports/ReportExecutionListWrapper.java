package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "reportExecutions")
public class ReportExecutionListWrapper {

    private List<ReportExecutionDescriptor> reportExecutions;

    public ReportExecutionListWrapper(){}

    public ReportExecutionListWrapper(List<ReportExecutionDescriptor> reportExecutions){
        this.reportExecutions = reportExecutions;
    }

    @XmlElement(name = "reportExecution")
    public List<ReportExecutionDescriptor> getReportExecutions() {
        return reportExecutions;
    }

    public void setReportExecutions(List<ReportExecutionDescriptor> reportExecutions) {
        this.reportExecutions = reportExecutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportExecutionListWrapper)) return false;

        ReportExecutionListWrapper that = (ReportExecutionListWrapper) o;

        if (reportExecutions != null ? !reportExecutions.equals(that.reportExecutions) : that.reportExecutions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return reportExecutions != null ? reportExecutions.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ReportExecutionListWrapper{" +
                "reportExecutions=" + reportExecutions +
                '}';
    }
}
