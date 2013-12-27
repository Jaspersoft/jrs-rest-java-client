package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionRecipient;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PermissionsServiceTest extends Assert {

    @Test
    public void testGetPermissionForResourceBatch() {
        RepositoryPermissionListWrapper permissions =
                Permissions.resource("datasources").get();

        assertNotEquals(permissions, null);
    }

    @Test(enabled = false)
    public void testAddPermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_USER", 30));
        permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_ADMINISTRATOR", 30));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        Response response = Permissions.createPermissions(permissionListWrapper);
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionBatch"}, enabled = false)
    public void testUpdatePermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_USER", 1));
        permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_ADMINISTRATOR", 1));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        Response response =
                Permissions.resource("/themes").put(permissionListWrapper);

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionBatch", enabled = false)
    public void testDeletePermissionBatch() {
        Response response =
                Permissions.resource("/themes").delete();
        assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testGetPermissionForResourceWithPermissionRecipientSingle() {
        RepositoryPermission permission =
                Permissions.resource("datasources").permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER").get();

        assertNotEquals(permission, null);
    }

    @Test(enabled = false)
    public void testAddPermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("user:/jasperadmin")
                .setMask(30);

        Response response = Permissions.createPermissions(permission);
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermission"}, enabled = false)
    public void testUpdatePermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("user:/jasperadmin")
                .setMask(1);

        Response response =
                Permissions.resource("/").permissionRecipient(PermissionRecipient.USER, "jasperadmin").put(permission);

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermission", enabled = false)
    public void testDeletePermissionSingle() {
        Response response =
                Permissions.resource("/datasources").
                        permissionRecipient(PermissionRecipient.ROLE, "ROLE_ADMINISTRATOR").delete();
        assertEquals(response.getStatus(), 204);
    }

}
