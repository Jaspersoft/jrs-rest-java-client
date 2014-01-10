package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._import;

public enum ImportParameter {
    /**
     *  Access events (date, time, and user name of last modification) are
     *  exported
     */
    INCLUDE_ACCESS_EVENTS("includeAccessEvents"),

    /**
     *  Include audit data for all resources and users in the export
     */
    INCLUDE_AUDIT_EVENTS("includeAuditEvents"),

    /**
     *  Resources in the catalog replace those in the repository if their URIs
     *  and types match
     */
    UPDATE("update"),

    /**
     *  When used with --update, users in the catalog are not imported or
     *  updated. Use this
     *  option to import catalogs without overwriting currently defined user
     */
    SKIP_USER_UPDATE("skipUserUpdate"),

    /**
     *  Include monitoring events
     */
    INCLUDE_MONITORING_EVENTS("includeMonitoringEvents"),

    /**
     *  Include server settings
     */
    INCLUDE_SERVER_SETTINGS("includeServerSettings");

    private String paramName;

    private ImportParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
