package com.jaspersoft.jasperserver.jaxrs.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attachment")
public class AttachmentDescriptor {

    private String contentType;
    private String fileName;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttachmentDescriptor that = (AttachmentDescriptor) o;

        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentType != null ? contentType.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AttachmentDescriptor{" +
                "contentType='" + contentType + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
