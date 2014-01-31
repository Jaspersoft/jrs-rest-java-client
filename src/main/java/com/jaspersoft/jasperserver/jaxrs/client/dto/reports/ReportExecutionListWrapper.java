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
}
