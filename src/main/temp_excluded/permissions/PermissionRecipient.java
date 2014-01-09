package com.jaspersoft.jasperserver.jaxrs.client.builder.permissions;

public enum PermissionRecipient {

    USER("user"), ROLE("role");

    private String protocol;

    private PermissionRecipient(String protocol){
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
