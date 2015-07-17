package com.jaspersoft.jasperserver.jaxrs.client.dto.authority.users;

import com.sun.xml.txw2.annotation.XmlElement;

/**
 * Created by tetiana.iefimenko on 7/17/2015.
 */
@XmlElement
public class User {

    private String username;
    private String fullName;
    private String externallyDefined;
    private String tenantId;
    private String type;
    private String identifier;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExternallyDefined() {
        return externallyDefined;
    }

    public void setExternallyDefined(String externallyDefined) {
        this.externallyDefined = externallyDefined;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
