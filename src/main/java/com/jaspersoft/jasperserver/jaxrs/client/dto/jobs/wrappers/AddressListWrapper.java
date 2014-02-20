/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

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
