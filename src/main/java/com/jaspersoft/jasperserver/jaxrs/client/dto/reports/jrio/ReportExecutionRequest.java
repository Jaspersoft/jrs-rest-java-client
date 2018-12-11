/*
 * Copyright © 2014-2018. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports.jrio;

import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.AbstractReportExecutionRequest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tetyana Matveyeva
 */

@XmlRootElement
public class ReportExecutionRequest extends AbstractReportExecutionRequest<ReportExecutionRequest> {
    private String reportTimeZone;
    private String reportLocale;

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
        this.reportTimeZone = other.reportTimeZone;
        this.reportLocale = other.reportLocale;
        this.allowInlineScripts = other.allowInlineScripts;
        this.baseUrl = other.baseUrl;
        this.markupType = other.markupType;
        this.anchor = other.anchor;
    }

    public String getReportTimeZone() {
        return reportTimeZone;
    }

    public ReportExecutionRequest setReportTimeZone(String reportTimeZone) {
        this.reportTimeZone = reportTimeZone;
        return this;
    }

    public String getReportLocale() {
        return reportLocale;
    }

    public ReportExecutionRequest setReportLocale(String reportLocale) {
        this.reportLocale = reportLocale;
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
        if (reportTimeZone != null ? !reportTimeZone.equals(that.reportTimeZone) : that.reportTimeZone != null) return false;
        if (reportLocale != null ? !reportLocale.equals(that.reportLocale) : that.reportLocale != null) return false;
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
        result = 31 * result + (reportTimeZone != null ? reportTimeZone.hashCode() : 0);
        result = 31 * result + (reportLocale != null ? reportLocale.hashCode() : 0);
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
                ", reportTimeZone=" + reportTimeZone +
                ", reportLocale=" + reportLocale +
                ", allowInlineScripts=" + allowInlineScripts +
                ", baseUrl='" + baseUrl + '\'' +
                ", markupType='" + markupType + '\'' +
                ", anchor='" + anchor + '\'' +
                '}';
    }

    @Override
    protected ReportExecutionRequest getThis() {
        return this;
    }
}
