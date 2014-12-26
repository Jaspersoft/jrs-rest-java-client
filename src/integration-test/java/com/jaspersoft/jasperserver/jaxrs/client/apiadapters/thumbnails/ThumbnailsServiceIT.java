package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Integration tests for {@link ThumbnailsService}
 */
public class ThumbnailsServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_0);

        client = new JasperserverRestClient(config);
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    /**
     * Batch thumbnails operation
     */
    public void should_return_list_of_thumbnails() {

        List<ResourceThumbnail> entity = session.thumbnailsService()
                .thumbnails()
                .report("/public/Samples/Reports/5g.AccountsReport")
                .report("/public/Samples/Reports/SalesByMonthReport")
                .parameter(ThumbnailsParameter.DEFAULT_ALLOWED, true)
                .get()
                .getEntity()
                .getThumbnails();

        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.size() == 2);
    }

    @Test
    /**
     * Single thumbnail operation
     */
    public void should_return_single_thumbnail_as_stream() {

        InputStream entity = session.thumbnailsService()
                .thumbnail()
                .report("/public/Samples/Reports/5g.AccountsReport")
                .parameter(ThumbnailsParameter.DEFAULT_ALLOWED, true)
                .get()
                .getEntity();

        Assert.assertNotNull(entity);
    }

    @AfterMethod
    public void after() {
        session.logout();
        client = null;
        config = null;
        session = null;
    }
}