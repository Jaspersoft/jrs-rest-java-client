package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionRecipient;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

public class PermissionsServiceTest extends Assert {

    @Test
    public void testGetPermissionForResource() {
        RepositoryPermissionListWrapper permissions =
                Permissions.resource("datasources").get();

        assertNotEquals(permissions, null);
    }

    @Test
    public void testGetPermissionForResourceWithPermissionRecipient() {
        RepositoryPermission permission =
                Permissions.resource("datasources").permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER").get();

        assertNotEquals(permission, null);
    }

    @Test(enabled = false)
    public void testAddPermission() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/datasources")
                .setRecipient("role:/ROLE_ADMINISTRATOR")
                .setMask(30);

        Response response = Permissions.createPermission(permission);
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermission"}, enabled = false)
    public void testUpdatePermission() {
        /*RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("role:/ROLE_ADMINISTRATOR")
                .setMask(240693);

        Response response = Permissions.createPermission(permission);
        assertEquals(response.getStatus(), 201);*/
    }

    @Test(dependsOnMethods = "testUpdatePermission", enabled = false)
    public void testDeletePermission() {
        Response response =
                Permissions.resource("/datasources").
                        permissionRecipient(PermissionRecipient.ROLE, "ROLE_ADMINISTRATOR").delete();
        assertEquals(response.getStatus(), 204);
    }

}
