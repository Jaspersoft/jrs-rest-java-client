package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientUnitTest;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT extends RestClientUnitTest{

    private Session session;

    @BeforeClass
    public void before() {
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    public void shouldReturnAllUsers() {

         //When
        List<ClientUser> users = session
                .usersService()
                .allUsers()
                .get()
                .getEntity()
                .getUserList();

         //Then
        Assert.assertTrue(users.size() > 3);
    }

    @Test
    public void should_return_list_users_by_role() {

        //When
        List<ClientUser> users = session
                .usersService()
                .allUsers()
                .get()
                .getEntity()
                .getUserList();

        //Then

        Assert.assertTrue(users.size() > 3);
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}