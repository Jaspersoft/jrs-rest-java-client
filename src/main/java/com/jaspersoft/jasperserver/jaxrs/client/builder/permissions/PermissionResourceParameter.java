package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

public enum PermissionResourceParameter {

    /**
     *  When set to <b>true</b> shows all permissions who
     *  affect given uri, if <b>false</b> - only directly assigned. If recipient does not
     *  have any permission assigned or permission is not reachable not uri
     *  will be returned. Default - <b>false</b>.
     */
    EFFECTIVE_PERMISSIONS("effectivePermissions"),

    /**
     * Type of recipient (e.g. user/role).
     */
    RECIPIENT_TYPE("recipientType"),

    /**
     *  Id of recipient, requires <b>recipientType</b>. For multitenant
     *  environment should be tenant qualified.
     */
    RECIPIENT_ID("recipientId"),

    /**
     *  Describes resolving of recipients. Default - <b>false</b>.
     *  If <b>false</b> - all previous results filtered by set parameters <b>recipientType</b>, <b>recipientId</b>.
     *  If <b>true</b> - resolve permissions for ALL matched recipients by <b>recipientType</b>,
     *  <b>recipientId</b> ( like on repository page's permission
     *  dialog) If recipient does not have any permission assigned or
     *  permission is not reachable not uri will be returned.
     */
    RESOLVE_ALL("resolveAll")
    ;

    private String paramName;

    private PermissionResourceParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
