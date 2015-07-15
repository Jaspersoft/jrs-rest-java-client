package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 * Created by tetiana.iefimenko on 7/15/2015.
 */
public class DataSourcePatternsSettings {
    private String dataSourcePatterns;
    private String dbPort;
    private String dbName;
    private String sName;
    private String driverType;
    private String schemaName;
    private String informixServerName;
    private String dynamicUrlPartPattern;
    private String forbidWhitespacesPattern;
    private String attributePlaceholderPattern;

    public String getDataSourcePatterns() {
        return dataSourcePatterns;
    }

    public void setDataSourcePatterns(String dataSourcePatterns) {
        this.dataSourcePatterns = dataSourcePatterns;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getInformixServerName() {
        return informixServerName;
    }

    public void setInformixServerName(String informixServerName) {
        this.informixServerName = informixServerName;
    }

    public String getDynamicUrlPartPattern() {
        return dynamicUrlPartPattern;
    }

    public void setDynamicUrlPartPattern(String dynamicUrlPartPattern) {
        this.dynamicUrlPartPattern = dynamicUrlPartPattern;
    }

    public String getForbidWhitespacesPattern() {
        return forbidWhitespacesPattern;
    }

    public void setForbidWhitespacesPattern(String forbidWhitespacesPattern) {
        this.forbidWhitespacesPattern = forbidWhitespacesPattern;
    }

    public String getAttributePlaceholderPattern() {
        return attributePlaceholderPattern;
    }

    public void setAttributePlaceholderPattern(String attributePlaceholderPattern) {
        this.attributePlaceholderPattern = attributePlaceholderPattern;
    }
}
