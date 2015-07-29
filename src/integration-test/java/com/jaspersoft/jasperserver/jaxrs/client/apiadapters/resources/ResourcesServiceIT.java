package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexander Krasnyanskiy
 */
public class ResourcesServiceIT {

    private Session session;

    @BeforeMethod
    public void before() {
        RestClientConfiguration cfg = new RestClientConfiguration("http://localhost:8085");
        JasperserverRestClient client = new JasperserverRestClient(cfg);
        session = client.authenticate("jasperadmin", "jasperadmin");

        ClientFolder folder = new ClientFolder()
                .setUri("/reports/testFolder")
                .setLabel("Test Folder")
                .setDescription("Test folder description");

        ClientResource result = session.resourcesService()
                .resource("/reports/testFolder")
                .createNew(folder)
                .getEntity();

    }


    @Test
    public void shouldDeleteAFolder() throws InterruptedException {

        // Given
        TimeUnit.SECONDS.sleep(1);

        // When
        Response resp = session.resourcesService()
                .resource("/reports/testFolder")
                .delete()
                .getResponse();

        // Then
        ByteArrayInputStream is = (ByteArrayInputStream) resp.getEntity();

        Assert.assertEquals(resp.getStatus(), 204);
        Assert.assertNotNull(is);
    }

    @AfterMethod
    public void after() {
        session.logout();
        session = null;
    }
}