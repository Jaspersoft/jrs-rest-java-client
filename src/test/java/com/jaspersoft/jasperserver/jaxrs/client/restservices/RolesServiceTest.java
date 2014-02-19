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

        OperationResult<ClientRole> operationResult =
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

        OperationResult<ClientRole> operationResult =
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
