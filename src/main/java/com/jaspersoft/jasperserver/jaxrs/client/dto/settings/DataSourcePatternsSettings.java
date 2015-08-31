package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

/**
 *  @author Tetiana Iefimenko
 */
public class DataSourcePatternsSettings {
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String sName;
    private String driverType;
    private String schemaName;
    private String informixServerName;
    private String dynamicUrlPartPattern;
    private String forbidWhitespacesPattern;
    private String attributePlaceholderPattern;

    public DataSourcePatternsSettings() {
    }

    public DataSourcePatternsSettings(DataSourcePatternsSettings other) {
        this.dbHost = other.dbHost;
        this.dbPort = other.dbPort;
        this.dbName = other.dbName;
        this.sName = other.sName;
        this.driverType = other.driverType;
        this.schemaName = other.schemaName;
        this.informixServerName = other.informixServerName;
        this.dynamicUrlPartPattern = other.dynamicUrlPartPattern;
        this.forbidWhitespacesPattern = other.forbidWhitespacesPattern;
        this.attributePlaceholderPattern = other.attributePlaceholderPattern;
    }

    public String getDbHost() {
        return dbHost;
    }

    public DataSourcePatternsSettings setDbHost(String dbHost) {
        this.dbHost = dbHost;
        return  this;
    }

    public String getDbPort() {
        return dbPort;
    }

    public DataSourcePatternsSettings setDbPort(String dbPort) {
        this.dbPort = dbPort;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public DataSourcePatternsSettings setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getsName() {
        return sName;
    }

    public DataSourcePatternsSettings setsName(String sName) {
        this.sName = sName;
        return this;
    }

    public String getDriverType() {
        return driverType;
    }

    public DataSourcePatternsSettings setDriverType(String driverType) {
        this.driverType = driverType;
        return this;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public DataSourcePatternsSettings setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public String getInformixServerName() {
        return informixServerName;
    }

    public DataSourcePatternsSettings setInformixServerName(String informixServerName) {
        this.informixServerName = informixServerName;
        return this;
    }

    public String getDynamicUrlPartPattern() {
        return dynamicUrlPartPattern;
    }

    public DataSourcePatternsSettings setDynamicUrlPartPattern(String dynamicUrlPartPattern) {
        this.dynamicUrlPartPattern = dynamicUrlPartPattern;
        return this;
    }

    public String getForbidWhitespacesPattern() {
        return forbidWhitespacesPattern;
    }

    public DataSourcePatternsSettings setForbidWhitespacesPattern(String forbidWhitespacesPattern) {
        this.forbidWhitespacesPattern = forbidWhitespacesPattern;
        return this;
    }

    public String getAttributePlaceholderPattern() {
        return attributePlaceholderPattern;
    }

    public DataSourcePatternsSettings setAttributePlaceholderPattern(String attributePlaceholderPattern) {
        this.attributePlaceholderPattern = attributePlaceholderPattern;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourcePatternsSettings)) return false;

        DataSourcePatternsSettings that = (DataSourcePatternsSettings) o;

        if (getDbHost() != null ? !getDbHost().equals(that.getDbHost()) : that.getDbHost() != null)
            return false;
        if (getDbPort() != null ? !getDbPort().equals(that.getDbPort()) : that.getDbPort() != null) return false;
        if (getDbName() != null ? !getDbName().equals(that.getDbName()) : that.getDbName() != null) return false;
        if (getsName() != null ? !getsName().equals(that.getsName()) : that.getsName() != null) return false;
        if (getDriverType() != null ? !getDriverType().equals(that.getDriverType()) : that.getDriverType() != null)
            return false;
        if (getSchemaName() != null ? !getSchemaName().equals(that.getSchemaName()) : that.getSchemaName() != null)
            return false;
        if (getInformixServerName() != null ? !getInformixServerName().equals(that.getInformixServerName()) : that.getInformixServerName() != null)
            return false;
        if (getDynamicUrlPartPattern() != null ? !getDynamicUrlPartPattern().equals(that.getDynamicUrlPartPattern()) : that.getDynamicUrlPartPattern() != null)
            return false;
        if (getForbidWhitespacesPattern() != null ? !getForbidWhitespacesPattern().equals(that.getForbidWhitespacesPattern()) : that.getForbidWhitespacesPattern() != null)
            return false;
        return !(getAttributePlaceholderPattern() != null ? !getAttributePlaceholderPattern().equals(that.getAttributePlaceholderPattern()) : that.getAttributePlaceholderPattern() != null);

    }

    @Override
    public int hashCode() {
        int result = getDbHost() != null ? getDbHost().hashCode() : 0;
        result = 31 * result + (getDbPort() != null ? getDbPort().hashCode() : 0);
        result = 31 * result + (getDbName() != null ? getDbName().hashCode() : 0);
        result = 31 * result + (getsName() != null ? getsName().hashCode() : 0);
        result = 31 * result + (getDriverType() != null ? getDriverType().hashCode() : 0);
        result = 31 * result + (getSchemaName() != null ? getSchemaName().hashCode() : 0);
        result = 31 * result + (getInformixServerName() != null ? getInformixServerName().hashCode() : 0);
        result = 31 * result + (getDynamicUrlPartPattern() != null ? getDynamicUrlPartPattern().hashCode() : 0);
        result = 31 * result + (getForbidWhitespacesPattern() != null ? getForbidWhitespacesPattern().hashCode() : 0);
        result = 31 * result + (getAttributePlaceholderPattern() != null ? getAttributePlaceholderPattern().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataSourcePatternsSettings{" +
                "dbHost='" + dbHost + '\'' +
                ", dbPort='" + dbPort + '\'' +
                ", dbName='" + dbName + '\'' +
                ", sName='" + sName + '\'' +
                ", driverType='" + driverType + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", informixServerName='" + informixServerName + '\'' +
                ", dynamicUrlPartPattern='" + dynamicUrlPartPattern + '\'' +
                ", forbidWhitespacesPattern='" + forbidWhitespacesPattern + '\'' +
                ", attributePlaceholderPattern='" + attributePlaceholderPattern + '\'' +
                '}';
    }
}
