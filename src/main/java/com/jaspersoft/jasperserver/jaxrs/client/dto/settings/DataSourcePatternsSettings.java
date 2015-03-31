package com.jaspersoft.jasperserver.jaxrs.client.dto.settings;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataSourcePatternsSettings {

    private String dbHost;
    private String dbPort;
    private String dbName;
    private String sName;
    private String driverType;
    private String schemaName;
    private String informixServerName;
    private String dynamicUrlPartPattern;

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSourcePatternsSettings that = (DataSourcePatternsSettings) o;

        if (dbHost != null ? !dbHost.equals(that.dbHost) : that.dbHost != null) return false;
        if (dbPort != null ? !dbPort.equals(that.dbPort) : that.dbPort != null) return false;
        if (dbName != null ? !dbName.equals(that.dbName) : that.dbName != null) return false;
        if (sName != null ? !sName.equals(that.sName) : that.sName != null) return false;
        if (driverType != null ? !driverType.equals(that.driverType) : that.driverType != null) return false;
        if (schemaName != null ? !schemaName.equals(that.schemaName) : that.schemaName != null) return false;
        if (informixServerName != null ? !informixServerName.equals(that.informixServerName) : that.informixServerName != null)
            return false;
        return !(dynamicUrlPartPattern != null ? !dynamicUrlPartPattern.equals(that.dynamicUrlPartPattern) : that.dynamicUrlPartPattern != null);

    }

    @Override
    public int hashCode() {
        int result = dbHost != null ? dbHost.hashCode() : 0;
        result = 31 * result + (dbPort != null ? dbPort.hashCode() : 0);
        result = 31 * result + (dbName != null ? dbName.hashCode() : 0);
        result = 31 * result + (sName != null ? sName.hashCode() : 0);
        result = 31 * result + (driverType != null ? driverType.hashCode() : 0);
        result = 31 * result + (schemaName != null ? schemaName.hashCode() : 0);
        result = 31 * result + (informixServerName != null ? informixServerName.hashCode() : 0);
        result = 31 * result + (dynamicUrlPartPattern != null ? dynamicUrlPartPattern.hashCode() : 0);
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
                '}';
    }
}
