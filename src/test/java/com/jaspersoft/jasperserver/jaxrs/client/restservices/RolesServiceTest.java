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

package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.RolesParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.JSClientWebException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

public class RolesServiceTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test(priority = 0)
    public void testGetRole() {
        OperationResult<ClientRole> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename("ROLE_ADMINISTRATOR")
                        .get();

        ClientRole role = operationResult.getEntity();
        assertNotEquals(role, null);
        assertEquals(role.getName(), "ROLE_ADMINISTRATOR");
    }

    @Test(priority = 1, expectedExceptions = {JSClientWebException.class})
    public void testGetNonexistentRole() {
        OperationResult<ClientRole> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename("ROLE_HELLO")
                        .get();

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 404);
    }

    @Test
    public void testGetAllRoles() {
        OperationResult<RolesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .allRoles()
                        .get();

        RolesListWrapper rolesListWrapper = operationResult.getEntity();
        assertNotEquals(rolesListWrapper, null);
        assertEquals(rolesListWrapper.getRoleList().size(), 3);
    }

    @Test
    public void testGetAllRolesWithQueryParams() {
        OperationResult<RolesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .allRoles()
                        .param(RolesParameter.USER, "jasperadmin")
                        .get();

        RolesListWrapper rolesListWrapper = operationResult.getEntity();
        assertNotEquals(rolesListWrapper, null);
        assertEquals(rolesListWrapper.getRoleList().size(), 2);
    }

    @Test(dependsOnMethods = {"testGetRole", "testGetNonexistentRole"})
    public void testAddRole() {

        ClientRole role = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(true);

        OperationResult<RolesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename(role.getName())
                        .createOrUpdate(role);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddRole"}, enabled = false)
    public void testUpdateRole() {

        ClientRole roleHello = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(false);

        OperationResult<RolesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename(roleHello.getName())
                        .createOrUpdate(roleHello);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = {"testAddRole"})
    public void testDeleteRole() {

        OperationResult<ClientRole> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename("ROLE_HELLO")
                        .delete();
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);
    }

}
