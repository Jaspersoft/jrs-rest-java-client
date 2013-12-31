package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

public class RolesServiceTest extends Assert {

    @Test(priority = 0)
    public void testGetRole(){
        OperationResult<ClientRole> operationResult =
                Roles.rolename("ROLE_ADMINISTRATOR").get();
        ClientRole role = operationResult.getEntity();
        assertNotEquals(role, null);
        assertEquals(role.getName(), "ROLE_ADMINISTRATOR");
    }

    @Test(priority = 1)
    public void testGetNonexistentRole(){
        OperationResult<ClientRole> operationResult =
                Roles.rolename("ROLE_HELLO").get();
        ClientRole role = operationResult.getEntity();
        assertEquals(role, null);
    }

    @Test
    public void testGetAllRoles(){
        OperationResult<RolesListWrapper> operationResult =
                Roles.allRoles().get();
        RolesListWrapper rolesListWrapper = operationResult.getEntity();
        assertNotEquals(rolesListWrapper, null);
        assertEquals(rolesListWrapper.getRoleList().size(), 3);
    }

    @Test
    public void testGetAllRolesWithQueryParams(){
        OperationResult<RolesListWrapper> operationResult =
                Roles.allRoles().addParam("user", "jasperadmin").get();
        RolesListWrapper rolesListWrapper = operationResult.getEntity();
        assertNotEquals(rolesListWrapper, null);
        assertEquals(rolesListWrapper.getRoleList().size(), 2);
    }

    @Test(dependsOnMethods = {"testGetRole", "testGetNonexistentRole"})
    public void testAddRole(){

        ClientRole role = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(true);

        OperationResult<ClientRole> operationResult =
                Roles.rolename(role.getName()).put(role);
        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 201);
        assertNotEquals(Roles.rolename("ROLE_HELLO").get(), null);
    }

    @Test(dependsOnMethods = {"testAddRole"}, enabled = false)
    public void testUpdateRole(){

        ClientRole roleHello = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(false);

        OperationResult<ClientRole> operationResult =
                Roles.rolename(roleHello.getName()).put(roleHello);
        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);

        OperationResult<ClientRole> operationResult1 =
                Roles.rolename("ROLE_HELLO").get();
        ClientRole role = operationResult1.getEntity();
        assertNotEquals(role, null);
        assertFalse(role.isExternallyDefined());
    }

    @Test(dependsOnMethods = {"testAddRole"})
    public void testDeleteRole(){

        OperationResult<ClientRole> operationResult =
                Roles.rolename("ROLE_HELLO").delete();
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);

        OperationResult<ClientRole> operationResult1 =
                Roles.rolename("ROLE_HELLO").get();
        ClientRole role = operationResult1.getEntity();
        assertEquals(role, null);
    }

}
