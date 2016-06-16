package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Tetiana Iefimenko
 */
public class GlobalConfigurationSettings {
    private Map<String, String> messages;
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

    public GlobalConfigurationSettings() {
    }

    public GlobalConfigurationSettings(GlobalConfigurationSettings other) {
        this.messages = (other.messages != null) ? new LinkedHashMap<String, String>(other.messages) : null;
        this.paginatorItemsPerPage = other.paginatorItemsPerPage;
        this.paginatorPagesRange = other.paginatorPagesRange;
        this.reportLevelConfigurable = other.reportLevelConfigurable;
        this.paginationForSinglePageReport = other.paginationForSinglePageReport;
        this.calendarInputJsp = other.calendarInputJsp;
        this.userItemsPerPage = other.userItemsPerPage;
        this.roleItemsPerPage = other.roleItemsPerPage;
        this.tenantItemsPerPage = other.tenantItemsPerPage;
        this.userNameNotSupportedSymbols = other.userNameNotSupportedSymbols;
        this.roleNameNotSupportedSymbols = other.roleNameNotSupportedSymbols;
        this.userNameSeparator = other.userNameSeparator;
        this.defaultRole = other.defaultRole;
        this.passwordMask = other.passwordMask;
        this.viewReportsFilterList = (other.viewReportsFilterList != null) ?
                new LinkedList<String>(other.viewReportsFilterList) : null;
        this.outputFolderFilterList = (other.outputFolderFilterList != null) ?
                new LinkedList<String>(other.outputFolderFilterList) : null;
        this.outputFolderFilterPatterns = (other.outputFolderFilterPatterns != null) ?
                new LinkedList<String>(other.outputFolderFilterPatterns) : null;
        this.tenantNameNotSupportedSymbols = other.tenantNameNotSupportedSymbols;
        this.tenantIdNotSupportedSymbols = other.tenantIdNotSupportedSymbols;
        this.resourceIdNotSupportedSymbols = other.resourceIdNotSupportedSymbols;
        this.publicFolderUri = other.publicFolderUri;
        this.themeDefaultName = other.themeDefaultName;
        this.themeFolderName = other.themeFolderName;
        this.themeServletPrefix = other.themeServletPrefix;
        this.dateFormat = other.dateFormat;
        this.currentYearDateFormat = other.currentYearDateFormat;
        this.timestampFormat = other.timestampFormat;
        this.timeFormat = other.timeFormat;
        this.entitiesPerPage = other.entitiesPerPage;
        this.tempFolderUri = other.tempFolderUri;
        this.organizationsFolderUri = other.organizationsFolderUri;
        this.jdbcDriversFolderUri = other.jdbcDriversFolderUri;
        this.emailRegExpPattern = other.emailRegExpPattern;
        this.enableSaveToHostFS = other.enableSaveToHostFS;
        this.optimizeJavaScript = other.optimizeJavaScript;
        this.defaultAddToDomainDependents = other.defaultAddToDomainDependents;
        this.defaultDomainDependentsUseACL = other.defaultDomainDependentsUseACL;
        this.forceDomainDependentsUseACL = other.forceDomainDependentsUseACL;
        this.defaultDomainDependentsBlockAndUpdate = other.defaultDomainDependentsBlockAndUpdate;
        this.defaultDontUpdateDomainDependents = other.defaultDontUpdateDomainDependents;
        if (other.dataSourceTypes != null) {
            this.dataSourceTypes = new LinkedList<DataSourceType>();
            for (DataSourceType dataSourceType : other.dataSourceTypes) {
                this.dataSourceTypes.add(new DataSourceType(dataSourceType));
            }
        }
        this.allFileResourceTypes = (other.allFileResourceTypes != null) ?
                new LinkedHashMap<String, String>(other.allFileResourceTypes) : null;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public GlobalConfigurationSettings setMessages(Map<String, String> messages) {
        this.messages = messages;
        return this;
    }

    public Integer getPaginatorItemsPerPage() {
        return paginatorItemsPerPage;
    }

    public GlobalConfigurationSettings setPaginatorItemsPerPage(Integer paginatorItemsPerPage) {
        this.paginatorItemsPerPage = paginatorItemsPerPage;
        return this;
    }

    public Integer getPaginatorPagesRange() {
        return paginatorPagesRange;
    }

    public GlobalConfigurationSettings setPaginatorPagesRange(Integer paginatorPagesRange) {
        this.paginatorPagesRange = paginatorPagesRange;
        return this;
    }

    public Boolean getReportLevelConfigurable() {
        return reportLevelConfigurable;
    }

    public GlobalConfigurationSettings setReportLevelConfigurable(Boolean reportLevelConfigurable) {
        this.reportLevelConfigurable = reportLevelConfigurable;
        return this;
    }

    public Boolean getPaginationForSinglePageReport() {
        return paginationForSinglePageReport;
    }

    public GlobalConfigurationSettings setPaginationForSinglePageReport(Boolean paginationForSinglePageReport) {
        this.paginationForSinglePageReport = paginationForSinglePageReport;
        return this;
    }

    public String getCalendarInputJsp() {
        return calendarInputJsp;
    }

    public GlobalConfigurationSettings setCalendarInputJsp(String calendarInputJsp) {
        this.calendarInputJsp = calendarInputJsp;
        return this;
    }

    public Integer getUserItemsPerPage() {
        return userItemsPerPage;
    }

    public GlobalConfigurationSettings setUserItemsPerPage(Integer userItemsPerPage) {
        this.userItemsPerPage = userItemsPerPage;
        return this;
    }

    public Integer getRoleItemsPerPage() {
        return roleItemsPerPage;
    }

    public GlobalConfigurationSettings setRoleItemsPerPage(Integer roleItemsPerPage) {
        this.roleItemsPerPage = roleItemsPerPage;
        return this;
    }

    public Integer getTenantItemsPerPage() {
        return tenantItemsPerPage;
    }

    public GlobalConfigurationSettings setTenantItemsPerPage(Integer tenantItemsPerPage) {
        this.tenantItemsPerPage = tenantItemsPerPage;
        return this;
    }

    public String getUserNameNotSupportedSymbols() {
        return userNameNotSupportedSymbols;
    }

    public GlobalConfigurationSettings setUserNameNotSupportedSymbols(String userNameNotSupportedSymbols) {
        this.userNameNotSupportedSymbols = userNameNotSupportedSymbols;
        return this;
    }

    public String getRoleNameNotSupportedSymbols() {
        return roleNameNotSupportedSymbols;
    }

    public GlobalConfigurationSettings setRoleNameNotSupportedSymbols(String roleNameNotSupportedSymbols) {
        this.roleNameNotSupportedSymbols = roleNameNotSupportedSymbols;
        return this;
    }

    public String getUserNameSeparator() {
        return userNameSeparator;
    }

    public GlobalConfigurationSettings setUserNameSeparator(String userNameSeparator) {
        this.userNameSeparator = userNameSeparator;
        return this;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public GlobalConfigurationSettings setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
        return this;
    }

    public String getPasswordMask() {
        return passwordMask;
    }

    public GlobalConfigurationSettings setPasswordMask(String passwordMask) {
        this.passwordMask = passwordMask;
        return this;
    }

    public List<String> getViewReportsFilterList() {
        return viewReportsFilterList;
    }

    public GlobalConfigurationSettings setViewReportsFilterList(List<String> viewReportsFilterList) {
        this.viewReportsFilterList = viewReportsFilterList;
        return this;
    }

    public List<String> getOutputFolderFilterList() {
        return outputFolderFilterList;
    }

    public GlobalConfigurationSettings setOutputFolderFilterList(List<String> outputFolderFilterList) {
        this.outputFolderFilterList = outputFolderFilterList;
        return this;
    }

    public List<String> getOutputFolderFilterPatterns() {
        return outputFolderFilterPatterns;
    }

    public GlobalConfigurationSettings setOutputFolderFilterPatterns(List<String> outputFolderFilterPatterns) {
        this.outputFolderFilterPatterns = outputFolderFilterPatterns;
        return this;
    }

    public String getTenantNameNotSupportedSymbols() {
        return tenantNameNotSupportedSymbols;
    }

    public GlobalConfigurationSettings setTenantNameNotSupportedSymbols(String tenantNameNotSupportedSymbols) {
        this.tenantNameNotSupportedSymbols = tenantNameNotSupportedSymbols;
        return this;
    }

    public String getTenantIdNotSupportedSymbols() {
        return tenantIdNotSupportedSymbols;
    }

    public GlobalConfigurationSettings setTenantIdNotSupportedSymbols(String tenantIdNotSupportedSymbols) {
        this.tenantIdNotSupportedSymbols = tenantIdNotSupportedSymbols;
        return this;
    }

    public String getResourceIdNotSupportedSymbols() {
        return resourceIdNotSupportedSymbols;
    }

    public GlobalConfigurationSettings setResourceIdNotSupportedSymbols(String resourceIdNotSupportedSymbols) {
        this.resourceIdNotSupportedSymbols = resourceIdNotSupportedSymbols;
        return this;
    }

    public String getPublicFolderUri() {
        return publicFolderUri;
    }

    public GlobalConfigurationSettings setPublicFolderUri(String publicFolderUri) {
        this.publicFolderUri = publicFolderUri;
        return this;
    }

    public String getThemeDefaultName() {
        return themeDefaultName;
    }

    public GlobalConfigurationSettings setThemeDefaultName(String themeDefaultName) {
        this.themeDefaultName = themeDefaultName;
        return this;
    }

    public String getThemeFolderName() {
        return themeFolderName;
    }

    public GlobalConfigurationSettings setThemeFolderName(String themeFolderName) {
        this.themeFolderName = themeFolderName;
        return this;
    }

    public String getThemeServletPrefix() {
        return themeServletPrefix;
    }

    public GlobalConfigurationSettings setThemeServletPrefix(String themeServletPrefix) {
        this.themeServletPrefix = themeServletPrefix;
        return this;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public GlobalConfigurationSettings setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public String getCurrentYearDateFormat() {
        return currentYearDateFormat;
    }

    public GlobalConfigurationSettings setCurrentYearDateFormat(String currentYearDateFormat) {
        this.currentYearDateFormat = currentYearDateFormat;
        return this;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public GlobalConfigurationSettings setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
        return this;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public GlobalConfigurationSettings setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
        return this;
    }

    public Integer getEntitiesPerPage() {
        return entitiesPerPage;
    }

    public GlobalConfigurationSettings setEntitiesPerPage(Integer entitiesPerPage) {
        this.entitiesPerPage = entitiesPerPage;
        return this;
    }

    public String getTempFolderUri() {
        return tempFolderUri;
    }

    public GlobalConfigurationSettings setTempFolderUri(String tempFolderUri) {
        this.tempFolderUri = tempFolderUri;
        return this;
    }

    public String getOrganizationsFolderUri() {
        return organizationsFolderUri;
    }

    public GlobalConfigurationSettings setOrganizationsFolderUri(String organizationsFolderUri) {
        this.organizationsFolderUri = organizationsFolderUri;
        return this;
    }

    public String getJdbcDriversFolderUri() {
        return jdbcDriversFolderUri;
    }

    public GlobalConfigurationSettings setJdbcDriversFolderUri(String jdbcDriversFolderUri) {
        this.jdbcDriversFolderUri = jdbcDriversFolderUri;
        return this;
    }

    public String getEmailRegExpPattern() {
        return emailRegExpPattern;
    }

    public GlobalConfigurationSettings setEmailRegExpPattern(String emailRegExpPattern) {
        this.emailRegExpPattern = emailRegExpPattern;
        return this;
    }

    public Boolean getEnableSaveToHostFS() {
        return enableSaveToHostFS;
    }

    public GlobalConfigurationSettings setEnableSaveToHostFS(Boolean enableSaveToHostFS) {
        this.enableSaveToHostFS = enableSaveToHostFS;
        return this;
    }

    public Boolean getOptimizeJavaScript() {
        return optimizeJavaScript;
    }

    public GlobalConfigurationSettings setOptimizeJavaScript(Boolean optimizeJavaScript) {
        this.optimizeJavaScript = optimizeJavaScript;
        return this;
    }

    public Boolean getDefaultAddToDomainDependents() {
        return defaultAddToDomainDependents;
    }

    public GlobalConfigurationSettings setDefaultAddToDomainDependents(Boolean defaultAddToDomainDependents) {
        this.defaultAddToDomainDependents = defaultAddToDomainDependents;
        return this;
    }

    public Boolean getDefaultDomainDependentsUseACL() {
        return defaultDomainDependentsUseACL;
    }

    public GlobalConfigurationSettings setDefaultDomainDependentsUseACL(Boolean defaultDomainDependentsUseACL) {
        this.defaultDomainDependentsUseACL = defaultDomainDependentsUseACL;
        return this;
    }

    public Boolean getForceDomainDependentsUseACL() {
        return forceDomainDependentsUseACL;
    }

    public GlobalConfigurationSettings setForceDomainDependentsUseACL(Boolean forceDomainDependentsUseACL) {
        this.forceDomainDependentsUseACL = forceDomainDependentsUseACL;
        return this;
    }

    public Boolean getDefaultDomainDependentsBlockAndUpdate() {
        return defaultDomainDependentsBlockAndUpdate;
    }

    public GlobalConfigurationSettings setDefaultDomainDependentsBlockAndUpdate(Boolean defaultDomainDependentsBlockAndUpdate) {
        this.defaultDomainDependentsBlockAndUpdate = defaultDomainDependentsBlockAndUpdate;
        return this;
    }

    public Boolean getDefaultDontUpdateDomainDependents() {
        return defaultDontUpdateDomainDependents;
    }

    public GlobalConfigurationSettings setDefaultDontUpdateDomainDependents(Boolean defaultDontUpdateDomainDependents) {
        this.defaultDontUpdateDomainDependents = defaultDontUpdateDomainDependents;
        return this;
    }

    public List<DataSourceType> getDataSourceTypes() {
        return dataSourceTypes;
    }

    public GlobalConfigurationSettings setDataSourceTypes(List<DataSourceType> dataSourceTypes) {
        this.dataSourceTypes = dataSourceTypes;
        return this;
    }

    public Map<String, String> getAllFileResourceTypes() {
        return allFileResourceTypes;
    }

    public GlobalConfigurationSettings setAllFileResourceTypes(Map<String, String> allFileResourceTypes) {
        this.allFileResourceTypes = allFileResourceTypes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GlobalConfigurationSettings)) return false;

        GlobalConfigurationSettings that = (GlobalConfigurationSettings) o;

        if (getMessages() != null ? !getMessages().equals(that.getMessages()) : that.getMessages() != null)
            return false;
        if (getPaginatorItemsPerPage() != null ? !getPaginatorItemsPerPage().equals(that.getPaginatorItemsPerPage()) : that.getPaginatorItemsPerPage() != null)
            return false;
        if (getPaginatorPagesRange() != null ? !getPaginatorPagesRange().equals(that.getPaginatorPagesRange()) : that.getPaginatorPagesRange() != null)
            return false;
        if (getReportLevelConfigurable() != null ? !getReportLevelConfigurable().equals(that.getReportLevelConfigurable()) : that.getReportLevelConfigurable() != null)
            return false;
        if (getPaginationForSinglePageReport() != null ? !getPaginationForSinglePageReport().equals(that.getPaginationForSinglePageReport()) : that.getPaginationForSinglePageReport() != null)
            return false;
        if (getCalendarInputJsp() != null ? !getCalendarInputJsp().equals(that.getCalendarInputJsp()) : that.getCalendarInputJsp() != null)
            return false;
        if (getUserItemsPerPage() != null ? !getUserItemsPerPage().equals(that.getUserItemsPerPage()) : that.getUserItemsPerPage() != null)
            return false;
        if (getRoleItemsPerPage() != null ? !getRoleItemsPerPage().equals(that.getRoleItemsPerPage()) : that.getRoleItemsPerPage() != null)
            return false;
        if (getTenantItemsPerPage() != null ? !getTenantItemsPerPage().equals(that.getTenantItemsPerPage()) : that.getTenantItemsPerPage() != null)
            return false;
        if (getUserNameNotSupportedSymbols() != null ? !getUserNameNotSupportedSymbols().equals(that.getUserNameNotSupportedSymbols()) : that.getUserNameNotSupportedSymbols() != null)
            return false;
        if (getRoleNameNotSupportedSymbols() != null ? !getRoleNameNotSupportedSymbols().equals(that.getRoleNameNotSupportedSymbols()) : that.getRoleNameNotSupportedSymbols() != null)
            return false;
        if (getUserNameSeparator() != null ? !getUserNameSeparator().equals(that.getUserNameSeparator()) : that.getUserNameSeparator() != null)
            return false;
        if (getDefaultRole() != null ? !getDefaultRole().equals(that.getDefaultRole()) : that.getDefaultRole() != null)
            return false;
        if (getPasswordMask() != null ? !getPasswordMask().equals(that.getPasswordMask()) : that.getPasswordMask() != null)
            return false;
        if (getViewReportsFilterList() != null ? !getViewReportsFilterList().equals(that.getViewReportsFilterList()) : that.getViewReportsFilterList() != null)
            return false;
        if (getOutputFolderFilterList() != null ? !getOutputFolderFilterList().equals(that.getOutputFolderFilterList()) : that.getOutputFolderFilterList() != null)
            return false;
        if (getOutputFolderFilterPatterns() != null ? !getOutputFolderFilterPatterns().equals(that.getOutputFolderFilterPatterns()) : that.getOutputFolderFilterPatterns() != null)
            return false;
        if (getTenantNameNotSupportedSymbols() != null ? !getTenantNameNotSupportedSymbols().equals(that.getTenantNameNotSupportedSymbols()) : that.getTenantNameNotSupportedSymbols() != null)
            return false;
        if (getTenantIdNotSupportedSymbols() != null ? !getTenantIdNotSupportedSymbols().equals(that.getTenantIdNotSupportedSymbols()) : that.getTenantIdNotSupportedSymbols() != null)
            return false;
        if (getResourceIdNotSupportedSymbols() != null ? !getResourceIdNotSupportedSymbols().equals(that.getResourceIdNotSupportedSymbols()) : that.getResourceIdNotSupportedSymbols() != null)
            return false;
        if (getPublicFolderUri() != null ? !getPublicFolderUri().equals(that.getPublicFolderUri()) : that.getPublicFolderUri() != null)
            return false;
        if (getThemeDefaultName() != null ? !getThemeDefaultName().equals(that.getThemeDefaultName()) : that.getThemeDefaultName() != null)
            return false;
        if (getThemeFolderName() != null ? !getThemeFolderName().equals(that.getThemeFolderName()) : that.getThemeFolderName() != null)
            return false;
        if (getThemeServletPrefix() != null ? !getThemeServletPrefix().equals(that.getThemeServletPrefix()) : that.getThemeServletPrefix() != null)
            return false;
        if (getDateFormat() != null ? !getDateFormat().equals(that.getDateFormat()) : that.getDateFormat() != null)
            return false;
        if (getCurrentYearDateFormat() != null ? !getCurrentYearDateFormat().equals(that.getCurrentYearDateFormat()) : that.getCurrentYearDateFormat() != null)
            return false;
        if (getTimestampFormat() != null ? !getTimestampFormat().equals(that.getTimestampFormat()) : that.getTimestampFormat() != null)
            return false;
        if (getTimeFormat() != null ? !getTimeFormat().equals(that.getTimeFormat()) : that.getTimeFormat() != null)
            return false;
        if (getEntitiesPerPage() != null ? !getEntitiesPerPage().equals(that.getEntitiesPerPage()) : that.getEntitiesPerPage() != null)
            return false;
        if (getTempFolderUri() != null ? !getTempFolderUri().equals(that.getTempFolderUri()) : that.getTempFolderUri() != null)
            return false;
        if (getOrganizationsFolderUri() != null ? !getOrganizationsFolderUri().equals(that.getOrganizationsFolderUri()) : that.getOrganizationsFolderUri() != null)
            return false;
        if (getJdbcDriversFolderUri() != null ? !getJdbcDriversFolderUri().equals(that.getJdbcDriversFolderUri()) : that.getJdbcDriversFolderUri() != null)
            return false;
        if (getEmailRegExpPattern() != null ? !getEmailRegExpPattern().equals(that.getEmailRegExpPattern()) : that.getEmailRegExpPattern() != null)
            return false;
        if (getEnableSaveToHostFS() != null ? !getEnableSaveToHostFS().equals(that.getEnableSaveToHostFS()) : that.getEnableSaveToHostFS() != null)
            return false;
        if (getOptimizeJavaScript() != null ? !getOptimizeJavaScript().equals(that.getOptimizeJavaScript()) : that.getOptimizeJavaScript() != null)
            return false;
        if (getDefaultAddToDomainDependents() != null ? !getDefaultAddToDomainDependents().equals(that.getDefaultAddToDomainDependents()) : that.getDefaultAddToDomainDependents() != null)
            return false;
        if (getDefaultDomainDependentsUseACL() != null ? !getDefaultDomainDependentsUseACL().equals(that.getDefaultDomainDependentsUseACL()) : that.getDefaultDomainDependentsUseACL() != null)
            return false;
        if (getForceDomainDependentsUseACL() != null ? !getForceDomainDependentsUseACL().equals(that.getForceDomainDependentsUseACL()) : that.getForceDomainDependentsUseACL() != null)
            return false;
        if (getDefaultDomainDependentsBlockAndUpdate() != null ? !getDefaultDomainDependentsBlockAndUpdate().equals(that.getDefaultDomainDependentsBlockAndUpdate()) : that.getDefaultDomainDependentsBlockAndUpdate() != null)
            return false;
        if (getDefaultDontUpdateDomainDependents() != null ? !getDefaultDontUpdateDomainDependents().equals(that.getDefaultDontUpdateDomainDependents()) : that.getDefaultDontUpdateDomainDependents() != null)
            return false;
        if (getDataSourceTypes() != null ? !getDataSourceTypes().equals(that.getDataSourceTypes()) : that.getDataSourceTypes() != null)
            return false;
        return !(getAllFileResourceTypes() != null ? !getAllFileResourceTypes().equals(that.getAllFileResourceTypes()) : that.getAllFileResourceTypes() != null);

    }

    @Override
    public int hashCode() {
        int result = getMessages() != null ? getMessages().hashCode() : 0;
        result = 31 * result + (getPaginatorItemsPerPage() != null ? getPaginatorItemsPerPage().hashCode() : 0);
        result = 31 * result + (getPaginatorPagesRange() != null ? getPaginatorPagesRange().hashCode() : 0);
        result = 31 * result + (getReportLevelConfigurable() != null ? getReportLevelConfigurable().hashCode() : 0);
        result = 31 * result + (getPaginationForSinglePageReport() != null ? getPaginationForSinglePageReport().hashCode() : 0);
        result = 31 * result + (getCalendarInputJsp() != null ? getCalendarInputJsp().hashCode() : 0);
        result = 31 * result + (getUserItemsPerPage() != null ? getUserItemsPerPage().hashCode() : 0);
        result = 31 * result + (getRoleItemsPerPage() != null ? getRoleItemsPerPage().hashCode() : 0);
        result = 31 * result + (getTenantItemsPerPage() != null ? getTenantItemsPerPage().hashCode() : 0);
        result = 31 * result + (getUserNameNotSupportedSymbols() != null ? getUserNameNotSupportedSymbols().hashCode() : 0);
        result = 31 * result + (getRoleNameNotSupportedSymbols() != null ? getRoleNameNotSupportedSymbols().hashCode() : 0);
        result = 31 * result + (getUserNameSeparator() != null ? getUserNameSeparator().hashCode() : 0);
        result = 31 * result + (getDefaultRole() != null ? getDefaultRole().hashCode() : 0);
        result = 31 * result + (getPasswordMask() != null ? getPasswordMask().hashCode() : 0);
        result = 31 * result + (getViewReportsFilterList() != null ? getViewReportsFilterList().hashCode() : 0);
        result = 31 * result + (getOutputFolderFilterList() != null ? getOutputFolderFilterList().hashCode() : 0);
        result = 31 * result + (getOutputFolderFilterPatterns() != null ? getOutputFolderFilterPatterns().hashCode() : 0);
        result = 31 * result + (getTenantNameNotSupportedSymbols() != null ? getTenantNameNotSupportedSymbols().hashCode() : 0);
        result = 31 * result + (getTenantIdNotSupportedSymbols() != null ? getTenantIdNotSupportedSymbols().hashCode() : 0);
        result = 31 * result + (getResourceIdNotSupportedSymbols() != null ? getResourceIdNotSupportedSymbols().hashCode() : 0);
        result = 31 * result + (getPublicFolderUri() != null ? getPublicFolderUri().hashCode() : 0);
        result = 31 * result + (getThemeDefaultName() != null ? getThemeDefaultName().hashCode() : 0);
        result = 31 * result + (getThemeFolderName() != null ? getThemeFolderName().hashCode() : 0);
        result = 31 * result + (getThemeServletPrefix() != null ? getThemeServletPrefix().hashCode() : 0);
        result = 31 * result + (getDateFormat() != null ? getDateFormat().hashCode() : 0);
        result = 31 * result + (getCurrentYearDateFormat() != null ? getCurrentYearDateFormat().hashCode() : 0);
        result = 31 * result + (getTimestampFormat() != null ? getTimestampFormat().hashCode() : 0);
        result = 31 * result + (getTimeFormat() != null ? getTimeFormat().hashCode() : 0);
        result = 31 * result + (getEntitiesPerPage() != null ? getEntitiesPerPage().hashCode() : 0);
        result = 31 * result + (getTempFolderUri() != null ? getTempFolderUri().hashCode() : 0);
        result = 31 * result + (getOrganizationsFolderUri() != null ? getOrganizationsFolderUri().hashCode() : 0);
        result = 31 * result + (getJdbcDriversFolderUri() != null ? getJdbcDriversFolderUri().hashCode() : 0);
        result = 31 * result + (getEmailRegExpPattern() != null ? getEmailRegExpPattern().hashCode() : 0);
        result = 31 * result + (getEnableSaveToHostFS() != null ? getEnableSaveToHostFS().hashCode() : 0);
        result = 31 * result + (getOptimizeJavaScript() != null ? getOptimizeJavaScript().hashCode() : 0);
        result = 31 * result + (getDefaultAddToDomainDependents() != null ? getDefaultAddToDomainDependents().hashCode() : 0);
        result = 31 * result + (getDefaultDomainDependentsUseACL() != null ? getDefaultDomainDependentsUseACL().hashCode() : 0);
        result = 31 * result + (getForceDomainDependentsUseACL() != null ? getForceDomainDependentsUseACL().hashCode() : 0);
        result = 31 * result + (getDefaultDomainDependentsBlockAndUpdate() != null ? getDefaultDomainDependentsBlockAndUpdate().hashCode() : 0);
        result = 31 * result + (getDefaultDontUpdateDomainDependents() != null ? getDefaultDontUpdateDomainDependents().hashCode() : 0);
        result = 31 * result + (getDataSourceTypes() != null ? getDataSourceTypes().hashCode() : 0);
        result = 31 * result + (getAllFileResourceTypes() != null ? getAllFileResourceTypes().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GlobalConfigurationSettings{" +
                " paginatorItemsPerPage=" + paginatorItemsPerPage +
                ", paginatorPagesRange=" + paginatorPagesRange +
                ", reportLevelConfigurable=" + reportLevelConfigurable +
                ", paginationForSinglePageReport=" + paginationForSinglePageReport +
                ", calendarInputJsp='" + calendarInputJsp + '\'' +
                ", userItemsPerPage=" + userItemsPerPage +
                ", roleItemsPerPage=" + roleItemsPerPage +
                ", tenantItemsPerPage=" + tenantItemsPerPage +
                ", userNameNotSupportedSymbols='" + userNameNotSupportedSymbols + '\'' +
                ", roleNameNotSupportedSymbols='" + roleNameNotSupportedSymbols + '\'' +
                ", userNameSeparator='" + userNameSeparator + '\'' +
                ", defaultRole='" + defaultRole + '\'' +
                ", passwordMask='" + passwordMask + '\'' +
                ", tenantNameNotSupportedSymbols='" + tenantNameNotSupportedSymbols + '\'' +
                ", tenantIdNotSupportedSymbols='" + tenantIdNotSupportedSymbols + '\'' +
                ", resourceIdNotSupportedSymbols='" + resourceIdNotSupportedSymbols + '\'' +
                ", publicFolderUri='" + publicFolderUri + '\'' +
                ", themeDefaultName='" + themeDefaultName + '\'' +
                ", themeFolderName='" + themeFolderName + '\'' +
                ", themeServletPrefix='" + themeServletPrefix + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", currentYearDateFormat='" + currentYearDateFormat + '\'' +
                ", timestampFormat='" + timestampFormat + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", entitiesPerPage=" + entitiesPerPage +
                ", tempFolderUri='" + tempFolderUri + '\'' +
                ", organizationsFolderUri='" + organizationsFolderUri + '\'' +
                ", jdbcDriversFolderUri='" + jdbcDriversFolderUri + '\'' +
                ", emailRegExpPattern='" + emailRegExpPattern + '\'' +
                ", enableSaveToHostFS=" + enableSaveToHostFS +
                ", optimizeJavaScript=" + optimizeJavaScript +
                ", defaultAddToDomainDependents=" + defaultAddToDomainDependents +
                ", defaultDomainDependentsUseACL=" + defaultDomainDependentsUseACL +
                ", forceDomainDependentsUseACL=" + forceDomainDependentsUseACL +
                ", defaultDomainDependentsBlockAndUpdate=" + defaultDomainDependentsBlockAndUpdate +
                ", defaultDontUpdateDomainDependents=" + defaultDontUpdateDomainDependents +
                '}';
    }
}
