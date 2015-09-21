package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.RestClientUnitTest;
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
public class ResourcesServiceIT extends RestClientUnitTest {

    private Session session;

    @BeforeClass
    public void before() {
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