package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.List;
import java.util.Map;

/**
 * Created by tetiana.iefimenko on 7/15/2015.
 */
public class GlobalConfigurationSettings {
    private String messages;
    private Integer paginatorItemsPerPage;
    private Integer paginatorPagesRange;
    private Boolean reportLevelConfigurable;
    private Boolean paginationForSinglePageReport;
    private String calendarInputJsp;
    private Integer userItemsPerPage;
    private Integer roleItemsPerPage;
    private Integer tenantItemsPerPage;
    private String userNameNotSupportedSymbols;
    private String roleNameNotSupportedSymbols;
    private String userNameSeparator;
    private String defaultRole;
    private String passwordMask;
    private List<String> viewReportsFilterList;
    private List<String> outputFolderFilterList;
    private List<String> outputFolderFilterPatterns;
    private String tenantNameNotSupportedSymbols;
    private String tenantIdNotSupportedSymbols;
    private String resourceIdNotSupportedSymbols;
    private String publicFolderUri;
    private String themeDefaultName;
    private String themeFolderName;
    private String themeServletPrefix;
    private String dateFormat;
    private String currentYearDateFormat;
    private String timestampFormat;
    private String timeFormat;
    private Integer entitiesPerPage;
    private String tempFolderUri;
    private String organizationsFolderUri;
    private String jdbcDriversFolderUri;
    private String emailRegExpPattern;
    private Boolean enableSaveToHostFS;
    private Boolean optimizeJavaScript;
    private Boolean defaultAddToDomainDependents;
    private Boolean defaultDomainDependentsUseACL;
    private Boolean forceDomainDependentsUseACL;
    private Boolean defaultDomainDependentsBlockAndUpdate;
    private Boolean defaultDontUpdateDomainDependents;
    private List<DataSourceType> dataSourceTypes;
    private Map<String, String> allFileResourceTypes;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Integer getPaginatorItemsPerPage() {
        return paginatorItemsPerPage;
    }

    public void setPaginatorItemsPerPage(Integer paginatorItemsPerPage) {
        this.paginatorItemsPerPage = paginatorItemsPerPage;
    }

    public Integer getPaginatorPagesRange() {
        return paginatorPagesRange;
    }

    public void setPaginatorPagesRange(Integer paginatorPagesRange) {
        this.paginatorPagesRange = paginatorPagesRange;
    }

    public Boolean getReportLevelConfigurable() {
        return reportLevelConfigurable;
    }

    public void setReportLevelConfigurable(Boolean reportLevelConfigurable) {
        this.reportLevelConfigurable = reportLevelConfigurable;
    }

    public Boolean getPaginationForSinglePageReport() {
        return paginationForSinglePageReport;
    }

    public void setPaginationForSinglePageReport(Boolean paginationForSinglePageReport) {
        this.paginationForSinglePageReport = paginationForSinglePageReport;
    }

    public String getCalendarInputJsp() {
        return calendarInputJsp;
    }

    public void setCalendarInputJsp(String calendarInputJsp) {
        this.calendarInputJsp = calendarInputJsp;
    }

    public Integer getUserItemsPerPage() {
        return userItemsPerPage;
    }

    public void setUserItemsPerPage(Integer userItemsPerPage) {
        this.userItemsPerPage = userItemsPerPage;
    }

    public Integer getRoleItemsPerPage() {
        return roleItemsPerPage;
    }

    public void setRoleItemsPerPage(Integer roleItemsPerPage) {
        this.roleItemsPerPage = roleItemsPerPage;
    }

    public Integer getTenantItemsPerPage() {
        return tenantItemsPerPage;
    }

    public void setTenantItemsPerPage(Integer tenantItemsPerPage) {
        this.tenantItemsPerPage = tenantItemsPerPage;
    }

    public String getUserNameNotSupportedSymbols() {
        return userNameNotSupportedSymbols;
    }

    public void setUserNameNotSupportedSymbols(String userNameNotSupportedSymbols) {
        this.userNameNotSupportedSymbols = userNameNotSupportedSymbols;
    }

    public String getRoleNameNotSupportedSymbols() {
        return roleNameNotSupportedSymbols;
    }

    public void setRoleNameNotSupportedSymbols(String roleNameNotSupportedSymbols) {
        this.roleNameNotSupportedSymbols = roleNameNotSupportedSymbols;
    }

    public String getUserNameSeparator() {
        return userNameSeparator;
    }

    public void setUserNameSeparator(String userNameSeparator) {
        this.userNameSeparator = userNameSeparator;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }

    public String getPasswordMask() {
        return passwordMask;
    }

    public void setPasswordMask(String passwordMask) {
        this.passwordMask = passwordMask;
    }

    public List<String> getViewReportsFilterList() {
        return viewReportsFilterList;
    }

    public void setViewReportsFilterList(List<String> viewReportsFilterList) {
        this.viewReportsFilterList = viewReportsFilterList;
    }

    public List<String> getOutputFolderFilterList() {
        return outputFolderFilterList;
    }

    public void setOutputFolderFilterList(List<String> outputFolderFilterList) {
        this.outputFolderFilterList = outputFolderFilterList;
    }

    public List<String> getOutputFolderFilterPatterns() {
        return outputFolderFilterPatterns;
    }

    public void setOutputFolderFilterPatterns(List<String> outputFolderFilterPatterns) {
        this.outputFolderFilterPatterns = outputFolderFilterPatterns;
    }

    public String getTenantNameNotSupportedSymbols() {
        return tenantNameNotSupportedSymbols;
    }

    public void setTenantNameNotSupportedSymbols(String tenantNameNotSupportedSymbols) {
        this.tenantNameNotSupportedSymbols = tenantNameNotSupportedSymbols;
    }

    public String getTenantIdNotSupportedSymbols() {
        return tenantIdNotSupportedSymbols;
    }

    public void setTenantIdNotSupportedSymbols(String tenantIdNotSupportedSymbols) {
        this.tenantIdNotSupportedSymbols = tenantIdNotSupportedSymbols;
    }

    public String getResourceIdNotSupportedSymbols() {
        return resourceIdNotSupportedSymbols;
    }

    public void setResourceIdNotSupportedSymbols(String resourceIdNotSupportedSymbols) {
        this.resourceIdNotSupportedSymbols = resourceIdNotSupportedSymbols;
    }

    public String getPublicFolderUri() {
        return publicFolderUri;
    }

    public void setPublicFolderUri(String publicFolderUri) {
        this.publicFolderUri = publicFolderUri;
    }

    public String getThemeDefaultName() {
        return themeDefaultName;
    }

    public void setThemeDefaultName(String themeDefaultName) {
        this.themeDefaultName = themeDefaultName;
    }

    public String getThemeFolderName() {
        return themeFolderName;
    }

    public void setThemeFolderName(String themeFolderName) {
        this.themeFolderName = themeFolderName;
    }

    public String getThemeServletPrefix() {
        return themeServletPrefix;
    }

    public void setThemeServletPrefix(String themeServletPrefix) {
        this.themeServletPrefix = themeServletPrefix;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getCurrentYearDateFormat() {
        return currentYearDateFormat;
    }

    public void setCurrentYearDateFormat(String currentYearDateFormat) {
        this.currentYearDateFormat = currentYearDateFormat;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Integer getEntitiesPerPage() {
        return entitiesPerPage;
    }

    public void setEntitiesPerPage(Integer entitiesPerPage) {
        this.entitiesPerPage = entitiesPerPage;
    }

    public String getTempFolderUri() {
        return tempFolderUri;
    }

    public void setTempFolderUri(String tempFolderUri) {
        this.tempFolderUri = tempFolderUri;
    }

    public String getOrganizationsFolderUri() {
        return organizationsFolderUri;
    }

    public void setOrganizationsFolderUri(String organizationsFolderUri) {
        this.organizationsFolderUri = organizationsFolderUri;
    }

    public String getJdbcDriversFolderUri() {
        return jdbcDriversFolderUri;
    }

    public void setJdbcDriversFolderUri(String jdbcDriversFolderUri) {
        this.jdbcDriversFolderUri = jdbcDriversFolderUri;
    }

    public String getEmailRegExpPattern() {
        return emailRegExpPattern;
    }

    public void setEmailRegExpPattern(String emailRegExpPattern) {
        this.emailRegExpPattern = emailRegExpPattern;
    }

    public Boolean getEnableSaveToHostFS() {
        return enableSaveToHostFS;
    }

    public void setEnableSaveToHostFS(Boolean enableSaveToHostFS) {
        this.enableSaveToHostFS = enableSaveToHostFS;
    }

    public Boolean getOptimizeJavaScript() {
        return optimizeJavaScript;
    }

    public void setOptimizeJavaScript(Boolean optimizeJavaScript) {
        this.optimizeJavaScript = optimizeJavaScript;
    }

    public Boolean getDefaultAddToDomainDependents() {
        return defaultAddToDomainDependents;
    }

    public void setDefaultAddToDomainDependents(Boolean defaultAddToDomainDependents) {
        this.defaultAddToDomainDependents = defaultAddToDomainDependents;
    }

    public Boolean getDefaultDomainDependentsUseACL() {
        return defaultDomainDependentsUseACL;
    }

    public void setDefaultDomainDependentsUseACL(Boolean defaultDomainDependentsUseACL) {
        this.defaultDomainDependentsUseACL = defaultDomainDependentsUseACL;
    }

    public Boolean getForceDomainDependentsUseACL() {
        return forceDomainDependentsUseACL;
    }

    public void setForceDomainDependentsUseACL(Boolean forceDomainDependentsUseACL) {
        this.forceDomainDependentsUseACL = forceDomainDependentsUseACL;
    }

    public Boolean getDefaultDomainDependentsBlockAndUpdate() {
        return defaultDomainDependentsBlockAndUpdate;
    }

    public void setDefaultDomainDependentsBlockAndUpdate(Boolean defaultDomainDependentsBlockAndUpdate) {
        this.defaultDomainDependentsBlockAndUpdate = defaultDomainDependentsBlockAndUpdate;
    }

    public Boolean getDefaultDontUpdateDomainDependents() {
        return defaultDontUpdateDomainDependents;
    }

    public void setDefaultDontUpdateDomainDependents(Boolean defaultDontUpdateDomainDependents) {
        this.defaultDontUpdateDomainDependents = defaultDontUpdateDomainDependents;
    }

    public List<DataSourceType> getDataSourceTypes() {
        return dataSourceTypes;
    }

    public void setDataSourceTypes(List<DataSourceType> dataSourceTypes) {
        this.dataSourceTypes = dataSourceTypes;
    }

    public Map<String, String> getAllFileResourceTypes() {
        return allFileResourceTypes;
    }

    public void setAllFileResourceTypes(Map<String, String> allFileResourceTypes) {
        this.allFileResourceTypes = allFileResourceTypes;
    }
}
