/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@Deprecated
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
