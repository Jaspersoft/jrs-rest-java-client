package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users;

public enum UsersParameter {

    /**
     * max quantity of results
     * @deprecated  Use common approach instead.
     */
    @Deprecated
    MAX_RECORDS("maxRecords"),

    /**
     * Allows to search roles, may work in conjunction with any
     * other param.
     * @deprecated  Use common approach instead.
     */
    @Deprecated
    SEARCH("search"),

    /**
     *  If <b>hasAllRequiredRoles</b> is true then users should contain all
     *  <b>requiredRole</b> specified in query ( think as AND operation)
     *
     *  If <b>hasAllRequiredRoles</b> is false then users could contain any of
     *  <b>requiredRole</b> specified in query ( think as OR operation).
     *  Is true by default.
     */
    HAS_ALL_REQUIRED_ROLES("hasAllRequiredRoles"),

    /**
     *  <b>requiredRole</b> specify role name. In multitenancy environment, there
     *  are a common case when roles with the same name exist in different
     *  organizations, so to separate them specify role name by next pattern
     *  <b>"{rolename}|{organizationId}"</b> (organization id and role name separated by pipe)
     */
    REQUIRED_ROLE("requiredRole"),

    /**
     * is true by default
     */
    INCLUDE_SUB_ORGS("includeSubOrgs")
    ;

    private String paramName;

    private UsersParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
