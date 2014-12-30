package com.jaspersoft.jasperserver.jaxrs.client.dto.attributes;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
@Deprecated
@XmlRootElement(name = "attribute")
public class ServerAttribute {

    private String name;
    private String value;
    private Boolean secure;

    public ServerAttribute() {
    }

    public ServerAttribute(String name, String value) {
        this(name, value, null);
    }

    public ServerAttribute(String name, String value, Boolean secure) {
        this.name = name;
        this.value = value;
        this.secure = secure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement(name = "secure")
    public Boolean isSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }
}

