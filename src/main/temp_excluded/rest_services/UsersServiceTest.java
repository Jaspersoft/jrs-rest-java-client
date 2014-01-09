package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersServiceTest extends Assert {

    @AfterClass
    public void tearDown(){
        OperationResult<UserAttributesListWrapper> operationResult =
                Users.username("jasperadmin").attributes().delete();
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);
    }

    @Test(priority = 0)
    public void testGetUser() {
        OperationResult<ClientUser> operationResult =
                Users.username("jasperadmin").get();
        ClientUser user = operationResult.getEntity();
        assertNotEquals(user, null);
        assertEquals(user.getUsername(), "jasperadmin");
    }

    @Test(priority = 1)
    public void testGetNonexistentUser() {
        OperationResult<ClientUser> operationResult =
                Users.username("demo").get();
        ClientUser user = operationResult.getEntity();
        assertEquals(user, null);
    }

    @Test
    public void testGetAllUsers() {
        OperationResult<UsersListWrapper> operationResult =
                Users.allUsers().get();
        UsersListWrapper usersListWrapper = operationResult.getEntity();
        assertNotEquals(usersListWrapper, null);
        assertEquals(usersListWrapper.getUserList().size(), 3);
    }

    @Test
    public void testGetAllUsersWithQueryParams() {
        OperationResult<UsersListWrapper> operationResult =
                Users.allUsers().addParam("requiredRole", "ROLE_USER").get();
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

        OperationResult<ClientUser> operationResult =
                Users.username(user.getUsername()).put(user);
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 201);
        assertNotEquals(Users.username("demo").get(), null);
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

        OperationResult<ClientUser> operationResult =
                Users.username(user.getUsername()).put(user);
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 200);

        OperationResult<ClientUser> operationResult1 =
                Users.username("demo").get();
        ClientUser demo = operationResult1.getEntity();
        assertNotEquals(demo, null);
        assertEquals(demo.getFullName(), "Hello");
    }

    @Test(dependsOnMethods = {"testUpdateUser"})
    public void testDeleteUser() {

        OperationResult<ClientUser> operationResult =
                Users.username("demo").delete();
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 204);

        OperationResult<ClientUser> operationResult1 =
                Users.username("demo").get();
        ClientUser user = operationResult1.getEntity();
        assertEquals(user, null);
    }

    @Test
    public void testGetEmptyUserAttributesList() {
        OperationResult<UserAttributesListWrapper> operationResult =
                Users.username("jasperadmin").attributes().get();
        UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();
        assertEquals(attributesListWrapper, null);
    }

    @Test(dependsOnMethods = {"testGetEmptyUserAttributesList"})
    public void testAddUserAttributeSingle() {
        ClientUserAttribute clientUserAttribute = new ClientUserAttribute();
        clientUserAttribute
                .setName("testAttribute")
                .setValue("hello");

        OperationResult<ClientUserAttribute> operationResult =
                Users.username("jasperadmin")
                .attribute(clientUserAttribute.getName())
                .put(clientUserAttribute);

        Response response = operationResult.getResponse();


        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddUserAttributeSingle"})
    public void testGetUserAttributesList() {
        OperationResult<UserAttributesListWrapper> operationResult =
                Users.username("jasperadmin").attributes().get();

        UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();

        assertNotEquals(attributesListWrapper, null);
        assertEquals(attributesListWrapper.getProfileAttributes().size(), 1);
    }

    @Test(dependsOnMethods = {"testAddUserAttributeSingle"})
    public void testGetUserAttributeSingle() {
        OperationResult<ClientUserAttribute> operationResult =
                Users.username("jasperadmin").attribute("testAttribute").get();
        ClientUserAttribute attribute = operationResult.getEntity();
        assertNotEquals(attribute, null);
        assertEquals(attribute.getValue(), "hello");
    }

    @Test(dependsOnMethods = {"testGetUserAttributesList", "testGetUserAttributeSingle"})
    public void testDeleteUserAttributes() {
        OperationResult<UserAttributesListWrapper> operationResult =
                Users.username("jasperadmin").attributes().delete();
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
                Users.username("jasperadmin").attributes().put(listWrapper);
        Response response = operationResult.getResponse();
        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = {"testAddAttributeBatch"})
    public void testGetAttributesWithQueryParam() {
        OperationResult<UserAttributesListWrapper> operationResult =
                Users.username("jasperadmin").attributes().addParam("name", "attr2").addParam("name", "attr1").get();
        UserAttributesListWrapper attributesListWrapper = operationResult.getEntity();

        assertNotEquals(attributesListWrapper, null);
        assertEquals(attributesListWrapper.getProfileAttributes().size(), 2);
    }

}
