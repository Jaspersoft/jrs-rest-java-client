package com.jaspersoft.jasperserver.jaxrs.client.dto.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "error")
public class ErrorDescriptorListWrapper {

    private List<ErrorDescriptor> errorDescriptorList;

    public ErrorDescriptorListWrapper(){}
    public ErrorDescriptorListWrapper(List<ErrorDescriptor> errorDescriptorList){
        this.errorDescriptorList = errorDescriptorList;
    }

    @XmlElement(name = "error")
    public List<ErrorDescriptor> getErrorDescriptorList() {
        return errorDescriptorList;
    }

    public void setErrorDescriptorList(List<ErrorDescriptor> errorDescriptorList) {
        this.errorDescriptorList = errorDescriptorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorDescriptorListWrapper)) return false;

        ErrorDescriptorListWrapper that = (ErrorDescriptorListWrapper) o;

        if (errorDescriptorList != null ? !errorDescriptorList.equals(that.errorDescriptorList) : that.errorDescriptorList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return errorDescriptorList != null ? errorDescriptorList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ErrorDescriptorListWrapper{" +
                "errorDescriptorList=" + errorDescriptorList +
                '}';
    }
}
