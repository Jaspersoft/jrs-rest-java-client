package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

public enum PermissionResourceParameter {

    ;

    private String paramName;

    private PermissionResourceParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
