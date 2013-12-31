package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionRecipient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PermissionsServiceTest extends Assert {

    @BeforeClass
    public void setUp(){
        Permissions.resource("/themes").
                permissionRecipient(PermissionRecipient.USER, "joeuser").delete();
        Permissions.resource("/").
                permissionRecipient(PermissionRecipient.USER, "joeuser").delete();
    }

    @AfterClass
    public void tearDown(){
        setUp();
    }

    @Test
    public void testGetPermissionForResourceBatch() {
        OperationResult<RepositoryPermissionListWrapper> operationResult =
                Permissions.resource("/datasources").get();
        RepositoryPermissionListWrapper permissions = operationResult.getEntity();
        assertNotEquals(permissions, null);
    }

    @Test(enabled = true)
    public void testAddPermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/themes", "user:/joeuser", 30));
        //permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_ADMINISTRATOR", 30));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        OperationResult operationResult = Permissions.createPermissions(permissionListWrapper);
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionBatch"}, enabled = true)
    public void testUpdatePermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/themes", "user:/joeuser", 1));
        //permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_ADMINISTRATOR", 1));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        OperationResult<RepositoryPermissionListWrapper> operationResult =
                Permissions.resource("/themes").put(permissionListWrapper);
        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionBatch", enabled = false)
    public void testDeletePermissionBatch() {
        OperationResult<RepositoryPermissionListWrapper> operationResult =
                Permissions.resource("/themes").delete();
        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testGetPermissionForResourceWithPermissionRecipientSingle() {
        OperationResult<RepositoryPermission> operationResult =
                Permissions.resource("/datasources").permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER").get();
        RepositoryPermission permission = operationResult.getEntity();
        assertNotEquals(permission, null);
    }

    @Test(enabled = true)
    public void testAddPermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("user:/joeuser")
                .setMask(30);

        OperationResult operationResult = Permissions.createPermissions(permission);
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionSingle"}, enabled = true)
    public void testUpdatePermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("user:/joeuser")
                .setMask(1);

        OperationResult<RepositoryPermission> operationResult =
                Permissions.resource("/").permissionRecipient(PermissionRecipient.USER, "joeuser").put(permission);
        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionSingle", enabled = false)
    public void testDeletePermissionSingle() {
        OperationResult<RepositoryPermission> operationResult =
                Permissions.resource("/datasources").
                permissionRecipient(PermissionRecipient.ROLE, "ROLE_ADMINISTRATOR").delete();
        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 204);
    }

}
