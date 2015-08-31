package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.core.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Alexander Krasnyanskiy
 */
public class ResourcesServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_1);
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

    @AfterMethod
    public void after() {
        session.logout();
        session = null;
    }
}