package com.jaspersoft.jasperserver.jaxrs.client.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Date;

public class UsersServiceTest extends Assert {

    @Test(priority = 0)
    public void testGetUser(){
        ClientUser user = Users.username("jasperadmin").get();
        assertNotEquals(user, null);
        assertEquals(user.getUsername(), "jasperadmin");
    }

    @Test(priority = 1, expectedExceptions = NotFoundException.class)
    public void testGetNonexistentUser(){
        ClientUser user = Users.username("demo").get();
    }

    @Test
    public void testGetAllUsers(){
        UsersListWrapper usersListWrapper = Users.allUsers().get();
        assertNotEquals(usersListWrapper, null);
        assertEquals(usersListWrapper.getUserList().size(), 3);
    }

    @Test
    public void testGetAllUsersWithQueryParams(){
        UsersListWrapper usersListWrapper = Users.allUsers().addParam("requiredRole", "ROLE_USER").get();
        assertNotEquals(usersListWrapper, null);
        assertEquals(usersListWrapper.getUserList().size(), 2);
    }

    @Test(dependsOnMethods = {"testGetUser", "testGetAllUsers"})
    public void testAddUser(){

        Response response = Users.addOrUpdateUser(new ClientUser()
                .setFullName("Teeeeeeeeessssssssssttttttt")
                .setPassword("demoo")
                .setEmailAddress(null)
                .setExternallyDefined(false)
                .setEnabled(true)
                .setPreviousPasswordChangeTime(new Date(1348142595199L))
                .setUsername("demo"));
        assertEquals(response.getStatus(), 201);
        assertNotEquals(Users.username("demo").get(), null);
    }

    @Test(dependsOnMethods = {"testAddUser"})
    public void testUpdateUser(){

        Response response = Users.addOrUpdateUser(new ClientUser()
                .setFullName("Hello")
                .setPassword("demoo")
                .setEmailAddress(null)
                .setExternallyDefined(false)
                .setEnabled(true)
                .setPreviousPasswordChangeTime(new Date(1348142595199L))
                .setUsername("demo"));
        assertEquals(response.getStatus(), 200);
        ClientUser user = Users.username("demo").get();
        assertNotEquals(user, null);
        assertEquals(user.getFullName(), "Hello");
    }

    @Test(dependsOnMethods = {"testUpdateUser"}, expectedExceptions = NotFoundException.class)
    public void testDeleteUser(){

        Response response = Users.username("demo").delete();
        assertEquals(response.getStatus(), 204);
        ClientUser user = Users.username("demo").get();
    }

}
