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

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@Deprecated
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "reportExecution")
public class ReportExecutionDescriptor {

    private Integer currentPage;
    private String reportURI;
    private String requestId;
    private String status;
    private Integer totalPages;
    private ErrorDescriptor errorDescriptor;
    private List<ExportDescriptor> exports;


    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public void setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getReportURI() {
        return reportURI;
    }

    public void setReportURI(String reportURI) {
        this.reportURI = reportURI;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @XmlElementWrapper(name = "exports")
    @XmlElement(name = "export", type = ExportDescriptor.class)
    //@XmlJavaTypeAdapter(ExportDescriptorsAdapter.class)
    public List<ExportDescriptor> getExports() {
        return exports;
    }

    public void setExports(List<ExportDescriptor> exports) {
        this.exports = exports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportExecutionDescriptor that = (ReportExecutionDescriptor) o;

        if (currentPage != null ? !currentPage.equals(that.currentPage) : that.currentPage != null) return false;
        if (exports != null ? !exports.equals(that.exports) : that.exports != null) return false;
        if (reportURI != null ? !reportURI.equals(that.reportURI) : that.reportURI != null) return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (totalPages != null ? !totalPages.equals(that.totalPages) : that.totalPages != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentPage != null ? currentPage.hashCode() : 0;
        result = 31 * result + (reportURI != null ? reportURI.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        result = 31 * result + (exports != null ? exports.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportExecutionDescriptor{" +
                "currentPage=" + currentPage +
                ", reportURI='" + reportURI + '\'' +
                ", requestId='" + requestId + '\'' +
                ", status='" + status + '\'' +
                ", totalPages=" + totalPages +
                ", exports=" + exports +
                '}';
    }
}

