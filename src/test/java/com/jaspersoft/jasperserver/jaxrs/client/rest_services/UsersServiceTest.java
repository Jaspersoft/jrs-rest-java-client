package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
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
        Response response = Users.username("jasperadmin").attributes().delete();
        assertEquals(response.getStatus(), 204);
    }

    @Test(priority = 0)
    public void testGetUser() {
        ClientUser user = Users.username("jasperadmin").get();
        assertNotEquals(user, null);
        assertEquals(user.getUsername(), "jasperadmin");
    }

    @Test(priority = 1)
    public void testGetNonexistentUser() {
        ClientUser user = Users.username("demo").get();
        assertEquals(user, null);
    }

    @Test
    public void testGetAllUsers() {
        UsersListWrapper usersListWrapper = Users.allUsers().get();
        assertNotEquals(usersListWrapper, null);
        assertEquals(usersListWrapper.getUserList().size(), 3);
    }

    @Test
    public void testGetAllUsersWithQueryParams() {
        UsersListWrapper usersListWrapper = Users.allUsers().addParam("requiredRole", "ROLE_USER").get();
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

        Response response = Users.username(user.getUsername()).put(user);
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

        Response response = Users.username(user.getUsername()).put(user);
        assertEquals(response.getStatus(), 200);
        ClientUser demo = Users.username("demo").get();
        assertNotEquals(demo, null);
        assertEquals(demo.getFullName(), "Hello");
    }

    @Test(dependsOnMethods = {"testUpdateUser"})
    public void testDeleteUser() {

        Response response = Users.username("demo").delete();
        assertEquals(response.getStatus(), 204);
        ClientUser user = Users.username("demo").get();
        assertEquals(user, null);
    }

    @Test
    public void testGetEmptyUserAttributesList() {
        UserAttributesListWrapper attributesListWrapper =
                Users.username("jasperadmin").attributes().get();
        assertEquals(attributesListWrapper, null);
    }

    @Test(dependsOnMethods = {"testGetEmptyUserAttributesList"})
    public void testAddUserAttributeSingle() {
        ClientUserAttribute clientUserAttribute = new ClientUserAttribute();
        clientUserAttribute
                .setName("testAttribute")
                .setValue("hello");

        Response response =
                Users.username("jasperadmin")
                        .attribute(clientUserAttribute.getName())
                        .put(clientUserAttribute);

        assertEquals(response.getStatus(), 201);
    }

    @Test(dependsOnMethods = {"testAddUserAttributeSingle"})
    public void testGetUserAttributesList() {
        UserAttributesListWrapper attributesListWrapper =
                Users.username("jasperadmin").attributes().get();
        assertNotEquals(attributesListWrapper, null);
        assertEquals(attributesListWrapper.getProfileAttributes().size(), 1);
    }

    @Test(dependsOnMethods = {"testAddUserAttributeSingle"})
    public void testGetUserAttributeSingle() {
        ClientUserAttribute attribute =
                Users.username("jasperadmin").attribute("testAttribute").get();
        assertNotEquals(attribute, null);
        assertEquals(attribute.getValue(), "hello");
    }

    @Test(dependsOnMethods = {"testGetUserAttributesList", "testGetUserAttributeSingle"})
    public void testDeleteUserAttributes() {
        Response response = Users.username("jasperadmin").attributes().delete();
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
        Response response = Users.username("jasperadmin").attributes().put(listWrapper);
        assertEquals(response.getStatus(), 200);
    }

    @Test(dependsOnMethods = {"testAddAttributeBatch"})
    public void testGetAttributesWithQueryParam() {
        UserAttributesListWrapper attributesListWrapper =
                Users.username("jasperadmin").attributes().addParam("name", "attr2").addParam("name", "attr1").get();
        assertNotEquals(attributesListWrapper, null);
        assertEquals(attributesListWrapper.getProfileAttributes().size(), 2);
    }

}
