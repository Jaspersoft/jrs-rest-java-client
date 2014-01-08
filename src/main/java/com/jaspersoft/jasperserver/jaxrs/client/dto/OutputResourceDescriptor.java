package com.jaspersoft.jasperserver.jaxrs.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "outputResource")
public class OutputResourceDescriptor {

    private String contentType;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputResourceDescriptor that = (OutputResourceDescriptor) o;

        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return contentType != null ? contentType.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OutputResourceDescriptor{" +
                "contentType='" + contentType + '\'' +
                '}';
    }
}
