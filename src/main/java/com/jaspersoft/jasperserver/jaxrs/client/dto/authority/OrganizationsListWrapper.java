package com.jaspersoft.jasperserver.jaxrs.client.dto.authority;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "organizations")
public class OrganizationsListWrapper {

    private List<Organization> list;

    public OrganizationsListWrapper(){}

    public OrganizationsListWrapper(List<Organization> roles){
       list = roles;
    }

    @XmlElement(name = "organization")
    public List<Organization> getList() {
        return list;
    }

    public void setList(List<Organization> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationsListWrapper that = (OrganizationsListWrapper) o;

        if (list != null ? !list.equals(that.list) : that.list != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrganizationsListWrapper{" +
                "list=" + list +
                '}';
    }
}
