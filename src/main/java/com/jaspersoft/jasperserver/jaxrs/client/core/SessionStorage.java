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

package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.OrganizationsListWrapper;

import java.util.List;

public class SessionStorage {

    private RestClientConfiguration configuration;
    private AuthenticationCredentials credentials;

    public RestClientConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(RestClientConfiguration configuration) {
        this.configuration = configuration;
    }

    public AuthenticationCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public String getSessionId() {
        return credentials.getSessionId();
    }

    public void setSessionId(String sessionId) {
        if (sessionId != null)
            credentials.setSessionId(sessionId);
    }


    public static void main(String[] args) {
        RestClientConfiguration configuration1 = new RestClientConfiguration("http://localhost:4444/jasperserver-pro/");
        JasperserverRestClient client = new JasperserverRestClient(configuration1);

        Session session = client.authenticate("jasperadmin|organization_1", "jasperadmin");

        /*ClientTenant organization = new ClientTenant();
        organization.setAlias("fromRestOrg1");

        OperationResult<ClientTenant> result = session
                .organizationsService()
                .organizations()
                .parameter(OrganizationParameter.CREATE_DEFAULT_USERS, "false")
                .create(organization);

        System.out.println(result.getEntity());*/

        ClientTenant organization = new ClientTenant();
        organization.setTheme("pods_summer");

        OperationResult result = session
                .organizationsService()
                .organization("fromRestOrg1")
                .delete();

        System.out.println(result.getEntity());
    }

}
