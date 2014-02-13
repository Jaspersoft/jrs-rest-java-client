package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.UsersAttributesParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.UsersParameter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Test(groups = {"UsersServiceTests"})
public class UsersServiceTest extends Assert {

    private static JasperserverRestClient client;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @AfterClass
    public static void tearDown() {
        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("jasperadmin")
                .attributes()
                .delete();

        client
                .authenticate("jasperadmin", "jasperadmin")
                .usersService()
                .username("demo")
                .delete();

    }

    @Test(priority = 0)
    public void testGetUser() {
        OperationResult<ClientUser> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .get();

        ClientUser user = operationResult.getEntity();
        assertNotEquals(user, null);
        assertEquals(user.getUsername(), "jasperadmin");
    }

    @Test(priority = 1)
    public void testGetNonexistentUser() {
        OperationResult<ClientUser> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("demo").get();

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 404);
    }

    @Test
    public void testGetAllUsers() {
        OperationResult<UsersListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .allUsers()
                        .get();

        UsersListWrapper usersListWrapper = operationResult.getEntity();
        assertNotEquals(usersListWrapper, null);
        assertEquals(usersListWrapper.getUserList().size(), 3);
    }

    @Test
    public void testGetAllUsersWithQueryParams() {
        OperationResult<UsersListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .allUsers()
                        .param(UsersParameter.REQUIRED_ROLE, "ROLE_USER")
                        .get();

        UsersListWrapper usersListWrapper = operationResult.getEntity();
        assertNotEquals(usersListWrapper, null);
        assertEquals(usersListWrapper.getUserList().size(), 2);
    }

    @Test(dependsOnMethods = {"testGetUser", "testGetAllUsers"})
    public void testAddUser() {

        ClientUser user = new ClientUser()
                .setFullName("Teeeeeeeeessssssssssttttttt")
                .setPassword("demoo")
                .setEmailAddress(null)
                .setExternallyDefined(false)
                .setEnabled(true)
                .setPreviousPasswordChangeTime(new Date(1348142595199L))
                .setUsername("demo");

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .createOrUpdate(user);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddUser"})
    public void testUpdateUser() {

        ClientUser user = new ClientUser()
                .setFullName("Hello")
                .setPassword("demoo")
                .setEmailAddress(null)
                .setExternallyDefined(false)
                .setEnabled(true)
                .setPreviousPasswordChangeTime(new Date(1348142595199L))
                .setUsername("demo");

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .createOrUpdate(user);
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 200);

        OperationResult<ClientUser> operationResult1 =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username(user.getUsername())
                        .get();

        ClientUser demo = operationResult1.getEntity();
        assertNotEquals(demo, null);
        assertEquals(demo.getFullName(), "Hello");
    }

    @Test(dependsOnMethods = {"testUpdateUser"})
    public void testDeleteUser() {

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("demo")
                        .delete();
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);

    }

    @Test
    public void testGetEmptyUserAttributesList() {
        OperationResult<UserAttributesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attributes()
                        .get();

        UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();
        assertEquals(attributesListWrapper, null);
    }

    @Test(dependsOnMethods = {"testGetEmptyUserAttributesList"})
    public void testAddUserAttributeSingle() {
        ClientUserAttribute clientUserAttribute = new ClientUserAttribute();
        clientUserAttribute
                .setName("testAttribute")
                .setValue("hello");

        OperationResult operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attribute(clientUserAttribute.getName())
                        .createOrUpdate(clientUserAttribute);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddUserAttributeSingle"})
    public void testGetUserAttributesList() {
        OperationResult<UserAttributesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attributes()
                        .get();

        UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();

        assertNotEquals(attributesListWrapper, null);
        assertEquals(attributesListWrapper.getProfileAttributes().size(), 1);
    }

    @Test(dependsOnMethods = {"testAddUserAttributeSingle"})
    public void testGetUserAttributeSingle() {
        OperationResult<ClientUserAttribute> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attribute("testAttribute")
                        .get();

        ClientUserAttribute attribute = operationResult.getEntity();
        assertNotEquals(attribute, null);
        assertEquals(attribute.getValue(), "hello");
    }

    @Test(dependsOnMethods = {"testGetUserAttributesList", "testGetUserAttributeSingle"})
    public void testDeleteUserAttributes() {
        OperationResult<UserAttributesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attributes()
                        .delete();

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);
    }

    @Test(dependsOnMethods = {"testDeleteUserAttributes"})
    public void testAddAttributeBatch() {
        List<ClientUserAttribute> userAttributes = new ArrayList<ClientUserAttribute>();
        userAttributes.add(new ClientUserAttribute()
                .setName("attr1")
                .setValue("val1"));
        userAttributes.add(new ClientUserAttribute()
                .setName("attr2")
                .setValue("val2"));

        UserAttributesListWrapper listWrapper = new UserAttributesListWrapper(userAttributes);
        OperationResult<UserAttributesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attributes()
                        .createOrUpdate(listWrapper);

        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = {"testAddAttributeBatch"})
    public void testGetAttributesWithQueryParam() {
        OperationResult<UserAttributesListWrapper> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .usersService()
                        .username("jasperadmin")
                        .attributes()
                        .param(UsersAttributesParameter.NAME, "attr2")
                        .param(UsersAttributesParameter.NAME, "attr1")
                        .get();

        UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();

        assertNotEquals(attributesListWrapper, null);
        assertEquals(attributesListWrapper.getProfileAttributes().size(), 2);
    }

}
