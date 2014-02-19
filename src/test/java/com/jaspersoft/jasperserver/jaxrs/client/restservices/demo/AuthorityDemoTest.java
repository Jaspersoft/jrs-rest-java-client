package com.jaspersoft.jasperserver.jaxrs.client.restservices.demo;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionMask;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionRecipient;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

@Test(groups = "AuthorityDemo", dependsOnGroups = "UsersServiceTests")
public class AuthorityDemoTest extends Assert {

    private static JasperserverRestClient client;
    private static ClientUser user;


    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);

        user = new ClientUser()
                .setUsername("john.doe")
                .setPassword("12345678")
                .setEmailAddress("john.doe@email.net")
                .setEnabled(true)
                .setExternallyDefined(false)
                .setFullName("John Doe");
    }

    @AfterClass
    public static void tearDown() {
        try {
            client
                    .authenticate("jasperadmin", "jasperadmin")
                    .usersService()
                    .username(user.getUsername())
                    .delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateUser() {
        OperationResult result =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .createOrUpdate(user);

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.CREATED);
    }

    @Test(dependsOnMethods = "testCreateUser", enabled = true)
    public void testUpdateUserToAdminRole() {

        ClientRole role =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .rolesService()
                        .rolename("ROLE_ADMINISTRATOR")
                        .get()
                        .getEntity();

        assertNotEquals(role, null);

        Set<ClientRole> roles = new HashSet<ClientRole>();
        roles.add(role);
        user.setRoleSet(roles);

        OperationResult result =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .createOrUpdate(user);

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.UPDATED);
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testAddUserAttribute() {

        ClientUserAttribute attribute = new ClientUserAttribute()
                .setName("someAttribute")
                .setValue("hello");

        OperationResult result =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .attribute(attribute.getName())
                        .createOrUpdate(attribute);

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.CREATED);
    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testAddPermissionForUser() {

        RepositoryPermission permission = new RepositoryPermission()
                .setRecipient("user:/john.doe")
                .setUri("/")
                .setMask(PermissionMask.READ_ONLY);

        OperationResult result = client
                .authenticate("jasperadmin", "jasperadmin")
                .permissionsService()
                .createNew(permission);

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.CREATED);
    }

    @Test(dependsOnMethods = {"testAddPermissionForUser", "testUpdateUserToAdminRole"}, enabled = true)
    public void testLoginAsNewUserAndGetPermission() {

        OperationResult<RepositoryPermission> result =
                client
                        .authenticate("john.doe", "12345678")
                        .permissionsService()
                        .resource("/")
                        .permissionRecipient(PermissionRecipient.USER, "john.doe")
                        .get();

        RepositoryPermission permission = result.getEntity();
        assertEquals(permission.getMask().intValue(), PermissionMask.READ_ONLY);
    }

    @Test(dependsOnMethods = {"testAddUserAttribute"})
    public void testDeleteUserAttribute() {
        OperationResult result =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .attribute("someAttribute")
                        .delete();

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.NO_CONTENT);

    }

    @Test(dependsOnMethods = {"testLoginAsNewUserAndGetPermission"})
    public void testDeleteUserPermission() {
        OperationResult result =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .permissionsService()
                        .resource("/")
                        .permissionRecipient(PermissionRecipient.USER, "john.doe")
                        .delete();

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.NO_CONTENT);

    }

    @Test(dependsOnMethods = {"testAddPermissionForUser", "testAddUserAttribute",
            "testDeleteUserPermission", "testDeleteUserAttribute"})
    public void testDeleteUser() {
        OperationResult result =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .delete();

        Response response = result.getResponse();
        assertEquals(response.getStatus(), ResponseStatus.NO_CONTENT);

    }

}
