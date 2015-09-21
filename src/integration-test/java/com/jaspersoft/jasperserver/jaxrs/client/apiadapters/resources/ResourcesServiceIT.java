package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Alexander Krasnyanskiy
 * @author tetiana Iefimenko
 */
public class ResourcesServiceIT {

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

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}