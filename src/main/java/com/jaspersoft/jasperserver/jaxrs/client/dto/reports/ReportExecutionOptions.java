package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ReportExecutionOptions {

    private Boolean freshData = false;
    private Boolean saveDataSnapshot = false;
    private Boolean interactive = false;
    private Boolean ignorePagination;
    private Boolean defaultIgnorePagination;
    private Boolean async = false;
    private String transformerKey;
    private String contextPath;
    private String defaultAttachmentsPrefixTemplate;
    private String requestId;

    public ReportExecutionOptions(){}
    public ReportExecutionOptions(ReportExecutionOptions source){
        freshData = source.isFreshData();
        saveDataSnapshot = source.isSaveDataSnapshot();
        interactive = source.isInteractive();
        ignorePagination = source.getIgnorePagination();
        async = source.isAsync();
        transformerKey = source.getTransformerKey();
        contextPath = source.getContextPath();
        defaultAttachmentsPrefixTemplate = source.getDefaultAttachmentsPrefixTemplate();
        requestId = source.getRequestId();

        defaultIgnorePagination = source.getDefaultIgnorePagination();
    }


    public Boolean getDefaultIgnorePagination() {
        return defaultIgnorePagination;
    }


    public ReportExecutionOptions setDefaultIgnorePagination(Boolean defaultIgnorePagination) {
        this.defaultIgnorePagination = defaultIgnorePagination;
        return this;
    }


    public String getRequestId() {
        return requestId;
    }

    public ReportExecutionOptions setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getDefaultAttachmentsPrefixTemplate() {
        return defaultAttachmentsPrefixTemplate;
    }

    public ReportExecutionOptions setDefaultAttachmentsPrefixTemplate(String defaultAttachmentsPrefixTemplate) {
        this.defaultAttachmentsPrefixTemplate = defaultAttachmentsPrefixTemplate;
        return this;
    }

    public Boolean isAsync() {
        return async;
    }

    public ReportExecutionOptions setAsync(Boolean async) {
        this.async = async;
        return this;
    }

    public String getContextPath() {
        return contextPath;
    }

    public ReportExecutionOptions setContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public Boolean isFreshData() {
        return freshData;
    }

    public ReportExecutionOptions setFreshData(Boolean freshData) {
        this.freshData = freshData;
        return this;
    }

    public Boolean isSaveDataSnapshot() {
        return saveDataSnapshot;
    }

    public ReportExecutionOptions setSaveDataSnapshot(Boolean saveDataSnapshot) {
        this.saveDataSnapshot = saveDataSnapshot;
        return this;
    }

    /**
     * ignorePagination is optional parameter. Can be null. Check for null is required.
     * @return the ignorePagination value
     */
    public Boolean getIgnorePagination() {
        return ignorePagination;
    }

    public ReportExecutionOptions setIgnorePagination(Boolean ignorePagination) {
        this.ignorePagination = ignorePagination;
        return this;
    }

    public String getTransformerKey() {
        return transformerKey;
    }

    public ReportExecutionOptions setTransformerKey(String transformerKey) {
        this.transformerKey = transformerKey;
        return this;
    }

    public Boolean isInteractive() {
        return interactive;
    }

    public ReportExecutionOptions setInteractive(Boolean interactive) {
        this.interactive = interactive;
        return this;
    }
}
