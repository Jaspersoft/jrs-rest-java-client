package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_1);
        config.setLogHttp(true);

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

    @AfterMethod
    public void after() {
        session.logout();
        session = null;
    }
}