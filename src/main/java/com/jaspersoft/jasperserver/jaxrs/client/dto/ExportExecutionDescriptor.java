package com.jaspersoft.jasperserver.jaxrs.client.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exportExecution")
public class ExportExecutionDescriptor {

    private String id;
    private String status;
    private OutputResourceDescriptor outputResource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OutputResourceDescriptor getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(OutputResourceDescriptor outputResource) {
        this.outputResource = outputResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportExecutionDescriptor that = (ExportExecutionDescriptor) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (outputResource != null ? !outputResource.equals(that.outputResource) : that.outputResource != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (outputResource != null ? outputResource.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportExecutionDescriptor{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", outputResource=" + outputResource +
                '}';
    }
}
