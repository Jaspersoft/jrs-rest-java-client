/*
 * Copyright © 2014-2018. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import com.jaspersoft.jasperserver.dto.reports.ReportParameters;

/**
 * @author Tatyana Matveyeva
 */
public abstract class AbstractReportExecutionRequest<R extends AbstractReportExecutionRequest> {
    protected String reportUnitUri;
    protected Boolean freshData = false;
    protected Boolean saveDataSnapshot = false;
    /* reports are interactive by default in v2 services*/
    protected Boolean interactive = true;
    protected Boolean ignorePagination = false;
    protected Boolean async = false;
    protected String transformerKey;
    protected String outputFormat;
    protected String attachmentsPrefix;
    protected String pages;
    protected ReportParameters parameters;
    protected Boolean allowInlineScripts = true;
    protected String baseUrl;
    protected String markupType;
    protected String anchor;

    public String getReportUnitUri() {
        return reportUnitUri;
    }

    public R setReportUnitUri(String reportUnitUri) {
        this.reportUnitUri = reportUnitUri;
        return getThis();
    }

    public Boolean getFreshData() {
        return freshData;
    }

    public R setFreshData(Boolean freshData) {
        this.freshData = freshData;
        return getThis();
    }

    public Boolean getSaveDataSnapshot() {
        return saveDataSnapshot;
    }

    public R setSaveDataSnapshot(Boolean saveDataSnapshot) {
        this.saveDataSnapshot = saveDataSnapshot;
        return getThis();
    }

    public Boolean getInteractive() {
        return interactive;
    }

    public R setInteractive(Boolean interactive) {
        this.interactive = interactive;
        return getThis();
    }

    public Boolean getIgnorePagination() {
        return ignorePagination;
    }

    public R setIgnorePagination(Boolean ignorePagination) {
        this.ignorePagination = ignorePagination;
        return getThis();
    }

    public Boolean getAsync() {
        return async;
    }

    public R setAsync(Boolean async) {
        this.async = async;
        return getThis();
    }

    public String getTransformerKey() {
        return transformerKey;
    }

    public R setTransformerKey(String transformerKey) {
        this.transformerKey = transformerKey;
        return getThis();
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public R setOutputFormat(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat outputFormat) {
        this.outputFormat = outputFormat.toString().toLowerCase();
        return getThis();
    }

    public R setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat.toLowerCase();
        return getThis();
    }

    public String getAttachmentsPrefix() {
        return attachmentsPrefix;
    }

    public R setAttachmentsPrefix(String attachmentsPrefix) {
        this.attachmentsPrefix = attachmentsPrefix;
        return getThis();
    }

    public String getPages() {
        return pages;
    }

    public R setPages(String pages) {
        this.pages = pages;
        return getThis();
    }

    public ReportParameters getParameters() {
        return parameters;
    }

    public R setParameters(ReportParameters parameters) {
        this.parameters = parameters;
        return getThis();
    }

    public Boolean getAllowInlineScripts() {
        return allowInlineScripts;
    }

    public R setAllowInlineScripts(Boolean allowInlineScripts) {
        this.allowInlineScripts = allowInlineScripts;
        return getThis();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public R setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return getThis();
    }

    public String getMarkupType() {
        return markupType;
    }

    public R setMarkupType(String markupType) {
        this.markupType = markupType;
        return getThis();
    }

    public String getAnchor() {
        return anchor;
    }

    public R setAnchor(String anchor) {
        this.anchor = anchor;
        return getThis();
    }

    protected abstract R getThis();
}
