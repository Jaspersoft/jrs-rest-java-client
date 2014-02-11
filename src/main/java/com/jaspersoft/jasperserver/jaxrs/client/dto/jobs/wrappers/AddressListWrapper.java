package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@XmlRootElement
public class AddressListWrapper extends StringListWrapper {

    public AddressListWrapper() {
        super();
    }

    public AddressListWrapper(List<String> strings) {
        super(strings);
    }

    @Override
    @XmlElement(name = "address")
    protected List<String> getStrings() {
        return super.getStrings();
    }

    @Override
    protected void setStrings(List<String> strings) {
        super.setStrings(strings);
    }

    @XmlTransient
    public List<String> getAddress() {
        return this.getStrings();
    }

    @XmlTransient
    public void setAddress(List<String> adresses) {
        this.setStrings(strings);
    }

    @Override
    public String toString() {
        return "AddressListWrapper{" +
                "address=" + strings +
                '}';
    }
}
