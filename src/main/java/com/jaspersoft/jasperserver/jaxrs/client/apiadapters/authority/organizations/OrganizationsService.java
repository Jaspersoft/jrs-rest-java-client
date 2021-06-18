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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

public class OrganizationsService extends AbstractAdapter {

    public OrganizationsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleOrganizationAdapter organization(ClientTenant organization) {
        if (organization != null && ((organization.getAlias() != null && !organization.getAlias().isEmpty())
                ||(organization.getId() != null && !organization.getId().isEmpty()) )) {
            return new SingleOrganizationAdapter(sessionStorage, organization);
        }
        throw new IllegalArgumentException("Organization is not valid.");
    }

    public SingleOrganizationAdapter organization(String organizationId) {
        return this.organization(new ClientTenant().setId(organizationId));
    }

    public BatchOrganizationsAdapter allOrganizations() {
        return new BatchOrganizationsAdapter(sessionStorage);
    }

    /**
     * @deprecated Replaced by {@link OrganizationsService#allOrganizations()}.
     */
@Deprecated
    public BatchOrganizationsAdapter organizations() {
        return new BatchOrganizationsAdapter(sessionStorage);
    }

}
