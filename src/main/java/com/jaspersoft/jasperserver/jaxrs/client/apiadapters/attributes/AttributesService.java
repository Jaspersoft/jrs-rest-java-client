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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.util.Collection;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1-ALPHA
 */
public class AttributesService extends AbstractAdapter {
    private  StringBuilder holderUri = new StringBuilder("/");

    public AttributesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public AttributesService forOrganization(String organizationName) {
        this.holderUri.append("organizations/").append(organizationName).append("/");
        return this;
    }

    public AttributesService forOrganization(ClientTenant organization) {
        this.holderUri.append("organizations/").append(organization.getId()).append("/");
        return this;
    }

    public AttributesService forUser(ClientUser user) {
        this.holderUri.append("users/").append(user.getUsername()).append("/");
        return this;
    }


    public AttributesService forUser(String userName) {
        this.holderUri.append("users/").append(userName).append("/");
        return this;
    }

    public SingleAttributeAdapter attribute(String attributeName) {
        return new SingleAttributeAdapter(holderUri.toString(), sessionStorage, attributeName);
    }

    public BatchAttributeAdapter allAttributes() {
        return new BatchAttributeAdapter(holderUri.toString(), sessionStorage);
    }

    public BatchAttributeAdapter attributes(Collection<String> attributesNames) {
        return new BatchAttributeAdapter(holderUri.toString(), sessionStorage, attributesNames);
    }

    public BatchAttributeAdapter attributes(String... attributesNames) {
        return new BatchAttributeAdapter(holderUri.toString(), sessionStorage, attributesNames);
    }
}
