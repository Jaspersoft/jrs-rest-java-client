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

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.TimeZone;

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
    private TimeZone timeZone;
    private Boolean allowInlineScripts = true;
    private String baseUrl;
    private String markupType;
    private String anchor;

    public ReportExecutionRequest() {
    }

    public ReportExecutionRequest(ReportExecutionRequest other) {
        this.reportUnitUri = other.reportUnitUri;
        this.freshData = other.freshData;
        this.saveDataSnapshot = other.saveDataSnapshot;
        this.interactive = other.interactive;
        this.ignorePagination = other.ignorePagination;
        this.async = other.async;
        this.transformerKey = other.transformerKey;
        this.outputFormat = other.outputFormat;
        this.attachmentsPrefix = other.attachmentsPrefix;
        this.pages = other.pages;
        this.parameters = other.parameters;
        this.timeZone = TimeZone.getTimeZone(timeZone.getID());
        this.allowInlineScripts = other.allowInlineScripts;
        this.baseUrl = other.baseUrl;
        this.markupType = other.markupType;
        this.anchor = other.anchor;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public ReportExecutionRequest setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        return this;
    }

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

    public ReportExecutionRequest setOutputFormat(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat outputFormat) {
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

    public Boolean getAllowInlineScripts() {
        return allowInlineScripts;
    }

    public ReportExecutionRequest setAllowInlineScripts(Boolean allowInlineScripts) {
        this.allowInlineScripts = allowInlineScripts;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ReportExecutionRequest setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getMarkupType() {
        return markupType;
    }

    public ReportExecutionRequest setMarkupType(String markupType) {
        this.markupType = markupType;
        return this;
    }

    public String getAnchor() {
        return anchor;
    }

    public ReportExecutionRequest setAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportExecutionRequest)) return false;

        ReportExecutionRequest that = (ReportExecutionRequest) o;

        if (reportUnitUri != null ? !reportUnitUri.equals(that.reportUnitUri) : that.reportUnitUri != null)
            return false;
        if (freshData != null ? !freshData.equals(that.freshData) : that.freshData != null) return false;
        if (saveDataSnapshot != null ? !saveDataSnapshot.equals(that.saveDataSnapshot) : that.saveDataSnapshot != null)
            return false;
        if (interactive != null ? !interactive.equals(that.interactive) : that.interactive != null) return false;
        if (ignorePagination != null ? !ignorePagination.equals(that.ignorePagination) : that.ignorePagination != null)
            return false;
        if (async != null ? !async.equals(that.async) : that.async != null) return false;
        if (transformerKey != null ? !transformerKey.equals(that.transformerKey) : that.transformerKey != null)
            return false;
        if (outputFormat != null ? !outputFormat.equals(that.outputFormat) : that.outputFormat != null) return false;
        if (attachmentsPrefix != null ? !attachmentsPrefix.equals(that.attachmentsPrefix) : that.attachmentsPrefix != null)
            return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        if (timeZone != null ? !timeZone.equals(that.timeZone) : that.timeZone != null) return false;
        if (allowInlineScripts != null ? !allowInlineScripts.equals(that.allowInlineScripts) : that.allowInlineScripts != null)
            return false;
        if (baseUrl != null ? !baseUrl.equals(that.baseUrl) : that.baseUrl != null) return false;
        if (markupType != null ? !markupType.equals(that.markupType) : that.markupType != null) return false;
        return anchor != null ? anchor.equals(that.anchor) : that.anchor == null;
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
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        result = 31 * result + (allowInlineScripts != null ? allowInlineScripts.hashCode() : 0);
        result = 31 * result + (baseUrl != null ? baseUrl.hashCode() : 0);
        result = 31 * result + (markupType != null ? markupType.hashCode() : 0);
        result = 31 * result + (anchor != null ? anchor.hashCode() : 0);
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
                ", allowInlineScripts=" + allowInlineScripts +
                ", baseUrl='" + baseUrl + '\'' +
                ", markupType='" + markupType + '\'' +
                ", anchor='" + anchor + '\'' +
                '}';
    }
}
