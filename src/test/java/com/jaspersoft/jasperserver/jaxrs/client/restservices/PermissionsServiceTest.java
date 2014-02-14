package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionMask;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionRecipient;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PermissionsServiceTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public void setUp() {

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);

        try {
            client
                    .authenticate("jasperadmin", "jasperadmin")
                    .permissionsService()
                    .resource("/datasources")
                    .permissionRecipient(PermissionRecipient.USER, "joeuser")
                    .delete();

            client
                    .authenticate("jasperadmin", "jasperadmin")
                    .permissionsService()
                    .resource("/")
                    .permissionRecipient(PermissionRecipient.USER, "joeuser")
                    .delete();

            client
                    .authenticate("jasperadmin", "jasperadmin")
                    .permissionsService()
                    .resource("/themes")
                    .delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public void tearDown() {
        setUp();
    }

    @Test
    public void testGetPermissionForResourceBatch() {
        OperationResult<RepositoryPermissionListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/datasources")
                        .get();

        RepositoryPermissionListWrapper permissions = operationResult.getEntity();
        assertNotEquals(permissions, null);
    }

    @Test(enabled = true)
    public void testAddPermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/themes", "user:/joeuser", 30));
        //permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_ADMINISTRATOR", 30));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .createNew(permissionListWrapper);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionBatch"}, enabled = true)
    public void testUpdatePermissionBatch() {
        List<RepositoryPermission> permissionList = new ArrayList<RepositoryPermission>();
        permissionList.add(new RepositoryPermission("/themes", "user:/joeuser", 1));
        //permissionList.add(new RepositoryPermission("/themes", "role:/ROLE_ADMINISTRATOR", 1));

        RepositoryPermissionListWrapper permissionListWrapper = new RepositoryPermissionListWrapper(permissionList);

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/themes")
                        .createOrUpdate(permissionListWrapper);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionBatch", enabled = true)
    public void testDeletePermissionBatch() {
        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/themes")
                        .delete();

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 204);
    }

    @Test
    public void testGetPermissionForResourceWithPermissionRecipientSingle() {
        OperationResult<RepositoryPermission> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/datasources")
                        .permissionRecipient(PermissionRecipient.ROLE, "ROLE_USER")
                        .get();

        RepositoryPermission permission = operationResult.getEntity();
        assertNotEquals(permission, null);
    }

    @Test
    public void testAddPermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("user:/joeuser")
                .setMask(PermissionMask.READ_WRITE_DELETE);

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .createNew(permission);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddPermissionSingle"})
    public void testUpdatePermissionSingle() {
        RepositoryPermission permission = new RepositoryPermission();
        permission
                .setUri("/")
                .setRecipient("user:/joeuser")
                .setMask(1);

        OperationResult<RepositoryPermission> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/")
                        .permissionRecipient(PermissionRecipient.USER, "joeuser")
                        .createOrUpdate(permission);

        Response response = operationResult.getResponse();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = "testUpdatePermissionSingle")
    public void testDeletePermissionSingle() {
        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/")
                        .permissionRecipient(PermissionRecipient.USER, "joeuser")
                        .delete();

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);
    }

}
