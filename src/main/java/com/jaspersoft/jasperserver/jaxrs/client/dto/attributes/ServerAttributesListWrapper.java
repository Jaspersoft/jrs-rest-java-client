package com.jaspersoft.jasperserver.jaxrs.client.dto.attributes;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
@Deprecated
@XmlRootElement(name = "attributes")
@SuppressWarnings("deprecation")
public class ServerAttributesListWrapper {

    private List<ServerAttribute> attributes;

    public ServerAttributesListWrapper() {
    }

    public ServerAttributesListWrapper(List<ServerAttribute> attributes) {
        this.attributes = attributes;
    }

    @XmlElement(name = "attribute")
    public List<ServerAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ServerAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerAttributesListWrapper that = (ServerAttributesListWrapper) o;

        if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return attributes != null ? attributes.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ServerAttributesListWrapper{" +
                "attributes=" + attributes +
                '}';
    }
}