package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

public class RolesServiceTest extends Assert {

    @Test(priority = 0)
    public void testGetRole(){
        ClientRole role = Roles.rolename("ROLE_ADMINISTRATOR").get();
        assertNotEquals(role, null);
        assertEquals(role.getName(), "ROLE_ADMINISTRATOR");
    }

    @Test(priority = 1)
    public void testGetNonexistentRole(){
        ClientRole role = Roles.rolename("ROLE_HELLO").get();
        assertEquals(role, null);
    }

    @Test
    public void testGetAllRoles(){
        RolesListWrapper rolesListWrapper = Roles.allRoles().get();
        assertNotEquals(rolesListWrapper, null);
        assertEquals(rolesListWrapper.getRoleList().size(), 3);
    }

    @Test
    public void testGetAllRolesWithQueryParams(){
        RolesListWrapper rolesListWrapper = Roles.allRoles().addParam("user", "jasperadmin").get();
        assertNotEquals(rolesListWrapper, null);
        assertEquals(rolesListWrapper.getRoleList().size(), 2);
    }

    @Test(dependsOnMethods = {"testGetRole", "testGetNonexistentRole"})
    public void testAddRole(){

        ClientRole role = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(true);

        Response response = Roles.rolename(role.getName()).put(role);

        assertEquals(response.getStatus(), 201);
        assertNotEquals(Roles.rolename("ROLE_HELLO").get(), null);
    }

    @Test(dependsOnMethods = {"testAddRole"}, enabled = false)
    public void testUpdateRole(){

        ClientRole roleHello = new ClientRole()
                .setName("ROLE_HELLO")
                .setExternallyDefined(false);

        Response response = Roles.rolename(roleHello.getName()).put(roleHello);

        assertEquals(response.getStatus(), 200);
        ClientRole role = Roles.rolename("ROLE_HELLO").get();
        assertNotEquals(role, null);
        assertFalse(role.isExternallyDefined());
    }

    @Test(dependsOnMethods = {"testAddRole"})
    public void testDeleteRole(){

        Response response = Roles.rolename("ROLE_HELLO").delete();
        assertEquals(response.getStatus(), 204);
        ClientRole role = Roles.rolename("ROLE_HELLO").get();
        assertEquals(role, null);
    }

}
