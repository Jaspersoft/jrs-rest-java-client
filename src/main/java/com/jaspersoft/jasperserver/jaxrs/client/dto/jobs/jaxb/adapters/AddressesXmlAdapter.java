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

package com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.adapters;

import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.AddressesListWrapper;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: AddressesXmlAdapter.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 * @deprecated (use server adapter).
 */
public class AddressesXmlAdapter extends XmlAdapter<AddressesListWrapper, List<String>> {
    @Override
    public List<String> unmarshal(AddressesListWrapper v) throws Exception {
        return v != null ? v.getAddresses() : null;
    }

    @Override
    public AddressesListWrapper marshal(List<String> v) throws Exception {
        return v != null ? new AddressesListWrapper(v) : null;
    }
}
