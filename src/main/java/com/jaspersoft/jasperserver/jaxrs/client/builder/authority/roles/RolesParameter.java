package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles;

public enum  RolesParameter {

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
     *  If false, returns all roles of users, set by "user" param,
     *  otherwise returns only roles, which owned by all set users.
     *
     *  By default it's false.
     */
    HAS_ALL_USERS("hasAllUsers"),

    /**
     * Describes, will service retrieve roles of sub organizations.
     * If false, roles of exactly set organization will be retrieved.
     * Is true by default.
     */
    INCLUDE_SUB_ORGS("includeSubOrgs"),

    /**
     *  Returns roles of user, can be set multiple times.
     */
    USER("user")
    ;

    private String paramName;

    private RolesParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
