package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles.RolesParameter;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

public class RolesServiceTest extends Assert {

    @Test(priority = 0)
    public void testGetRole() {
        OperationResult<ClientRole> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename("ROLE_ADMINISTRATOR")
                        .get();

        ClientRole role = operationResult.getEntity();
        assertNotEquals(role, null);
        assertEquals(role.getName(), "ROLE_ADMINISTRATOR");
    }

    @Test(priority = 1)
    public void testGetNonexistentRole() {
        OperationResult<ClientRole> operationResult =
                JasperserverRestClient
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
                JasperserverRestClient
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
                JasperserverRestClient
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

        OperationResult<ClientRole> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename(role.getName())
                        .addOrUpdate(role);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddRole"}, enabled = false)
    public void testUpdateRole() {

        ClientRole roleHello = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(false);

        OperationResult<ClientRole> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename(roleHello.getName())
                        .addOrUpdate(roleHello);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = {"testAddRole"})
    public void testDeleteRole() {

        OperationResult<ClientRole> operationResult =
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename("ROLE_HELLO")
                        .delete();
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);
    }

}
