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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

public class UsersService extends AbstractAdapter {

    private String organizationId;

    public UsersService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public UsersService forOrganization(String organizationId) {
        if (organizationId == null || organizationId.equals("")) {
            throw new IllegalArgumentException("Organization is not valid.");
        }
        this.organizationId = organizationId;
        return this;
    }

    public UsersService forOrganization(ClientTenant organization) {
        if (organization == null) {
            throw new IllegalArgumentException("Organization is not valid.");
        }
        return this.forOrganization(organization.getId());
    }

    public SingleUserRequestAdapter user(ClientUser user) {
        if (user == null || user.getUsername() == null || user.getUsername().equals("")) {
            throw new IllegalArgumentException("User is not valid.");
        }
        return new SingleUserRequestAdapter(sessionStorage,
                (organizationId != null && user.getTenantId() == null) ?
                        new ClientUser(user).setTenantId(organizationId) : user);
    }

    public SingleUserRequestAdapter user(String userName)
    {
        return this.user(new ClientUser().setUsername(userName));
    }

    public BatchUsersRequestAdapter allUsers() {

        return new BatchUsersRequestAdapter(sessionStorage, organizationId);
    }

}


