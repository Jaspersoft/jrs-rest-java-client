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

import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportOutputFormat;
import java.util.TimeZone;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportExecutionRequest.java 26599 2012-12-10 13:04:23Z ykovalchyk $
 */
@XmlRootElement
public class ReportExecutionRequest {
    private String reportUnitUri;
    private Boolean freshData = false;
    private Boolean saveDataSnapshot = false;
    /* reports are interactive by default in v2 services*/
    private Boolean interactive = true;
    private Boolean ignorePagination = false;
    private Boolean async = false;
    private String transformerKey;
    private String outputFormat;
    private String attachmentsPrefix;
    private String pages;
    private ReportParameters parameters;

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public ReportExecutionRequest setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    private TimeZone timeZone;

    public String getReportUnitUri() {
        return reportUnitUri;
    }

    public ReportExecutionRequest setReportUnitUri(String reportUnitUri) {
        this.reportUnitUri = reportUnitUri;
        return this;
    }

    public Boolean getFreshData() {
        return freshData;
    }

    public ReportExecutionRequest setFreshData(Boolean freshData) {
        this.freshData = freshData;
        return this;
    }

    public Boolean getSaveDataSnapshot() {
        return saveDataSnapshot;
    }

    public ReportExecutionRequest setSaveDataSnapshot(Boolean saveDataSnapshot) {
        this.saveDataSnapshot = saveDataSnapshot;
        return this;
    }

    public Boolean getInteractive() {
        return interactive;
    }

    public ReportExecutionRequest setInteractive(Boolean interactive) {
        this.interactive = interactive;
        return this;
    }

    public Boolean getIgnorePagination() {
        return ignorePagination;
    }

    public ReportExecutionRequest setIgnorePagination(Boolean ignorePagination) {
        this.ignorePagination = ignorePagination;
        return this;
    }

    public Boolean getAsync() {
        return async;
    }

    public ReportExecutionRequest setAsync(Boolean async) {
        this.async = async;
        return this;
    }

    public String getTransformerKey() {
        return transformerKey;
    }

    public ReportExecutionRequest setTransformerKey(String transformerKey) {
        this.transformerKey = transformerKey;
        return this;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public ReportExecutionRequest setOutputFormat(ReportOutputFormat outputFormat) {
        this.outputFormat = outputFormat.toString().toLowerCase();
        return this;
    }
    public ReportExecutionRequest setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat.toLowerCase();
        return this;
    }

    public String getAttachmentsPrefix() {
        return attachmentsPrefix;
    }

    public ReportExecutionRequest setAttachmentsPrefix(String attachmentsPrefix) {
        this.attachmentsPrefix = attachmentsPrefix;
        return this;
    }

    public String getPages() {
        return pages;
    }

    public ReportExecutionRequest setPages(String pages) {
        this.pages = pages;
        return this;
    }

    public ReportParameters getParameters() {
        return parameters;
    }

    public ReportExecutionRequest setParameters(ReportParameters parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportExecutionRequest)) return false;

        ReportExecutionRequest that = (ReportExecutionRequest) o;

        if (getReportUnitUri() != null ? !getReportUnitUri().equals(that.getReportUnitUri()) : that.getReportUnitUri() != null)
            return false;
        if (getFreshData() != null ? !getFreshData().equals(that.getFreshData()) : that.getFreshData() != null)
            return false;
        if (getSaveDataSnapshot() != null ? !getSaveDataSnapshot().equals(that.getSaveDataSnapshot()) : that.getSaveDataSnapshot() != null)
            return false;
        if (getInteractive() != null ? !getInteractive().equals(that.getInteractive()) : that.getInteractive() != null)
            return false;
        if (getIgnorePagination() != null ? !getIgnorePagination().equals(that.getIgnorePagination()) : that.getIgnorePagination() != null)
            return false;
        if (getAsync() != null ? !getAsync().equals(that.getAsync()) : that.getAsync() != null) return false;
        if (getTransformerKey() != null ? !getTransformerKey().equals(that.getTransformerKey()) : that.getTransformerKey() != null)
            return false;
        if (getOutputFormat() != null ? !getOutputFormat().equals(that.getOutputFormat()) : that.getOutputFormat() != null)
            return false;
        if (getAttachmentsPrefix() != null ? !getAttachmentsPrefix().equals(that.getAttachmentsPrefix()) : that.getAttachmentsPrefix() != null)
            return false;
        if (getPages() != null ? !getPages().equals(that.getPages()) : that.getPages() != null) return false;
        if (getParameters() != null ? !getParameters().equals(that.getParameters()) : that.getParameters() != null)
            return false;
        return !(getTimeZone() != null ? !getTimeZone().equals(that.getTimeZone()) : that.getTimeZone() != null);

    }

    @Override
    public int hashCode() {
        int result = getReportUnitUri() != null ? getReportUnitUri().hashCode() : 0;
        result = 31 * result + (getFreshData() != null ? getFreshData().hashCode() : 0);
        result = 31 * result + (getSaveDataSnapshot() != null ? getSaveDataSnapshot().hashCode() : 0);
        result = 31 * result + (getInteractive() != null ? getInteractive().hashCode() : 0);
        result = 31 * result + (getIgnorePagination() != null ? getIgnorePagination().hashCode() : 0);
        result = 31 * result + (getAsync() != null ? getAsync().hashCode() : 0);
        result = 31 * result + (getTransformerKey() != null ? getTransformerKey().hashCode() : 0);
        result = 31 * result + (getOutputFormat() != null ? getOutputFormat().hashCode() : 0);
        result = 31 * result + (getAttachmentsPrefix() != null ? getAttachmentsPrefix().hashCode() : 0);
        result = 31 * result + (getPages() != null ? getPages().hashCode() : 0);
        result = 31 * result + (getParameters() != null ? getParameters().hashCode() : 0);
        result = 31 * result + (getTimeZone() != null ? getTimeZone().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportExecutionRequest{" +
                "reportUnitUri='" + reportUnitUri + '\'' +
                ", freshData=" + freshData +
                ", saveDataSnapshot=" + saveDataSnapshot +
                ", interactive=" + interactive +
                ", ignorePagination=" + ignorePagination +
                ", async=" + async +
                ", transformerKey='" + transformerKey + '\'' +
                ", outputFormat='" + outputFormat + '\'' +
                ", attachmentsPrefix='" + attachmentsPrefix + '\'' +
                ", pages='" + pages + '\'' +
                ", parameters=" + parameters +
                ", timeZone=" + timeZone +
                '}';
    }
}
