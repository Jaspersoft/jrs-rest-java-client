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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reportExecution")
public class ReportExecution {

    private Integer currentPage;
    private String reportURI;
    private String requestId;
    private String status;
    private Integer totalPages;
    private ErrorDescriptor errorDescriptor;
    private ExportsContainer exports = new ExportsContainer();
    private ReportExecutionOptions options;

    public ReportExecution() {
    }

    public ReportExecution(ReportExecution other) {
        this.currentPage = new Integer(other.currentPage);
        this.reportURI = other.reportURI;
        this.requestId = other.requestId;
        this.status = other.status;
        this.totalPages = new Integer(other.totalPages);
        this.errorDescriptor = new ErrorDescriptor(other.errorDescriptor);
        this.exports = new ExportsContainer(other.exports);
        this.options = new ReportExecutionOptions(other.options);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public ReportExecution setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public String getReportURI() {
        return reportURI;
    }

    public ReportExecution setReportURI(String reportURI) {
        this.reportURI = reportURI;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public ReportExecution setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ReportExecution setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public ReportExecution setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public ReportExecution setErrorDescriptor(ErrorDescriptor errorDescriptor) {
        this.errorDescriptor = errorDescriptor;
        return this;
    }

    public ReportExecution setExports(Set<ExportExecution> exports) {
        this.exports = new ExportsContainer();
        final HashMap<String, ExportExecution> executions = new HashMap<>();
        for (ExportExecution export : exports) {
            executions.put(export.getId(), export);
        }
        this.exports.setExecutions(executions);
        return this;
    }

    public ReportExecutionOptions getOptions() {
        return options;
    }

    public ReportExecution setOptions(ReportExecutionOptions options) {
        this.options = options;
        return this;
    }
    @XmlElement(name = "export")
    @XmlElementWrapper(name = "exports")
    public Set<ExportExecution> getExports() {
        return new HashSet<ExportExecution>(exports.getExecutions().values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportExecution)) return false;

        ReportExecution that = (ReportExecution) o;

        if (currentPage != null ? !currentPage.equals(that.currentPage) : that.currentPage != null) return false;
        if (reportURI != null ? !reportURI.equals(that.reportURI) : that.reportURI != null) return false;
        if (requestId != null ? !requestId.equals(that.requestId) : that.requestId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (totalPages != null ? !totalPages.equals(that.totalPages) : that.totalPages != null) return false;
        if (errorDescriptor != null ? !errorDescriptor.equals(that.errorDescriptor) : that.errorDescriptor != null)
            return false;
        if (exports != null ? !exports.equals(that.exports) : that.exports != null) return false;
        return options != null ? options.equals(that.options) : that.options == null;
    }

    @Override
    public int hashCode() {
        int result = currentPage != null ? currentPage.hashCode() : 0;
        result = 31 * result + (reportURI != null ? reportURI.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        result = 31 * result + (errorDescriptor != null ? errorDescriptor.hashCode() : 0);
        result = 31 * result + (exports != null ? exports.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportExecution{" +
                "currentPage=" + currentPage +
                ", reportURI='" + reportURI + '\'' +
                ", requestId='" + requestId + '\'' +
                ", status='" + status + '\'' +
                ", totalPages=" + totalPages +
                ", errorDescriptor=" + errorDescriptor +
                ", exports=" + exports +
                ", options=" + options +
                '}';
    }
}

