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

    public ReportExecutionRequest setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
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
        if (o == null || getClass() != o.getClass()) return false;

        ReportExecutionRequest that = (ReportExecutionRequest) o;

        if (async != null ? !async.equals(that.async) : that.async != null) return false;
        if (attachmentsPrefix != null ? !attachmentsPrefix.equals(that.attachmentsPrefix) : that.attachmentsPrefix != null)
            return false;
        if (freshData != null ? !freshData.equals(that.freshData) : that.freshData != null) return false;
        if (ignorePagination != null ? !ignorePagination.equals(that.ignorePagination) : that.ignorePagination != null)
            return false;
        if (interactive != null ? !interactive.equals(that.interactive) : that.interactive != null) return false;
        if (outputFormat != null ? !outputFormat.equals(that.outputFormat) : that.outputFormat != null) return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        if (reportUnitUri != null ? !reportUnitUri.equals(that.reportUnitUri) : that.reportUnitUri != null)
            return false;
        if (saveDataSnapshot != null ? !saveDataSnapshot.equals(that.saveDataSnapshot) : that.saveDataSnapshot != null)
            return false;
        if (transformerKey != null ? !transformerKey.equals(that.transformerKey) : that.transformerKey != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reportUnitUri != null ? reportUnitUri.hashCode() : 0;
        result = 31 * result + (freshData != null ? freshData.hashCode() : 0);
        result = 31 * result + (saveDataSnapshot != null ? saveDataSnapshot.hashCode() : 0);
        result = 31 * result + (interactive != null ? interactive.hashCode() : 0);
        result = 31 * result + (ignorePagination != null ? ignorePagination.hashCode() : 0);
        result = 31 * result + (async != null ? async.hashCode() : 0);
        result = 31 * result + (transformerKey != null ? transformerKey.hashCode() : 0);
        result = 31 * result + (outputFormat != null ? outputFormat.hashCode() : 0);
        result = 31 * result + (attachmentsPrefix != null ? attachmentsPrefix.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
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
                '}';
    }
}
