package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeClass
    public void before() {
        config = RestClientConfiguration.loadConfiguration("test_config.properties");
        client = new JasperserverRestClient(config);
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