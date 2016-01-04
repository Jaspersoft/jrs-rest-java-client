package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public enum CollectorStatus {

    RUNNING("running"),

    SHUTTING_DOWN("shutting_down"),

    STOPPED("stopped");

    private String name;

    CollectorStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
