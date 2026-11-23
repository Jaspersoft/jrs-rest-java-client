package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import java.util.HashSet;
import java.util.Set;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@XmlRootElement(name = "reportExecutions")
public class ReportExecutionsSetWrapper {

    private Set<ReportExecutionStatus> reportExecutionStatuses;

    public ReportExecutionsSetWrapper(){}

    public ReportExecutionsSetWrapper(Set<ReportExecutionStatus> reportExecutionStatuses) {
        if (reportExecutionStatuses != null) {
            this.reportExecutionStatuses = new HashSet<>();
            for (ReportExecutionStatus reportExecutionStatus : reportExecutionStatuses) {
                this.reportExecutionStatuses.add(new ReportExecutionStatus(reportExecutionStatus));
            }
        }
    }

    @XmlElement(name = "reportExecution")
    public Set<ReportExecutionStatus> getReportExecutionStatuses() {
        return reportExecutionStatuses;
    }

    public ReportExecutionsSetWrapper setReportExecutionStatuses(Set<ReportExecutionStatus> reportExecutionStatuses) {
        this.reportExecutionStatuses = reportExecutionStatuses;
        return this;
    }
}
