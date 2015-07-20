package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import junit.framework.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT {

    private Session session;

    @BeforeMethod
    public void before() {
        RestClientConfiguration cfg = new RestClientConfiguration("http://localhost:8085");
        JasperserverRestClient client = new JasperserverRestClient(cfg);
        Session session = client.authenticate("jasperadmin", "jasperadmin");
    }

    @Test
    public void shouldReturnAllUsers() {

        // When
        List<ClientUser> users = session
                .usersService()
                .allUsers()
                .get()
                .getEntity()
                .getUserList();

        // Then
        Assert.assertTrue(users.size() > 3);
    }

    @Test
    public void shouldDeleteAFolder() {

        // Given
        ClientFolder folder = new ClientFolder()
                .setUri("/reports/testFolder")
                .setLabel("Test Folder")
                .setDescription("Test folder description");

        // When
        ClientResource result = session.resourcesService()
                .resource("/reports/testFolder")
                .createNew(folder)
                .getEntity();

        Assert.assertNotNull(result);

        // Then
        result = (ClientResource) session.resourcesService()
                .resource("/reports/testFolder")
                .delete()
                .getEntity();

        Assert.assertNull(result);
    }

    @AfterMethod
    public void after() {
        session.logout();
        session = null;
    }
}