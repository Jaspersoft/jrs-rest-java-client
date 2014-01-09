package com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users;

public enum UsersAttributesParameter {

    NAME("name");

    private String paramName;

    private UsersAttributesParameter(String paramName){
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

}
