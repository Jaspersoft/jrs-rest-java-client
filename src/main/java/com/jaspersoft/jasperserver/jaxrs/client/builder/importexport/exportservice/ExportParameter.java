package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice;

public enum ExportParameter {

    /**
     *  <p>ExportService everything except audit data: all repository resources,
     *  permissions, report.</p>
     *  <p>ExportService everything except audit data: all repository resources,
     *  permissions, report jobs, users, and roles.</p>
     *  <p>This option is equivalent to:--uris --repository-permissions
     *  --report-jobs --users --roles</p>
     */
    EVERYTHING("everything"),

    /**
     *  <p>When this option is present, repository permissions are exported
     *  along with each exported folder and resource.</p>
     *  <p>This option should only be used in conjunction with uris.</p>
     */
    REPOSITORY_PERMISSIONS("repository-permissions"),

    /**
     *  <p>When this option is present, each role export triggers the export of all
     *  users belonging to the role.</p>
     *  <p>This option should only be used in conjunction with --roles</p>
     */
    ROLE_USERS("role-users"),

    /**
     *  Access events (date, time, and user name of last modification) are
     *  exported.
     */
    INCLUDE_ACCESS_EVENTS("include-access-events"),

    /**
     *  Include audit data for all resources and users in the export.
     */
    INCLUDE_AUDIT_EVENTS("include-audit-events"),

    /**
     *  Include monitoring events
     */
    INCLUDE_MONITORING_EVENTS("include-monitoring-events");

    private String paramName;

    private ExportParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
