package com.jaspersoft.jasperserver.jaxrs.client.core.config;

/**
 * @author Alexander Krasnyanskiy
 */
public class Timeout {
    private Integer connection;
    private Integer read;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }
}
