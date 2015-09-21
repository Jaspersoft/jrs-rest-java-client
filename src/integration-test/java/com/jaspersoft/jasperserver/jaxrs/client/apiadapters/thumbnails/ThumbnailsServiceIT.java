package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientUnitTest;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.RequestMethod;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;

/**
* Integration tests for {@link ThumbnailsService}
*/
public class ThumbnailsServiceIT extends RestClientUnitTest {

    private Session session;

    @BeforeClass
    public void before() {
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    /**
     * Batch thumbnails operation
     */
    public void should_return_list_of_thumbnails_with_default_request_method() {
        List<ResourceThumbnail> entity = session.thumbnailsService()
                .thumbnails()
                .reports("/public/Samples/Reports/08g.UnitSalesDetailReport",
                        "/public/Samples/Reports/11g.SalesByMonthReport")
                .defaultAllowed(true)
                .get()
                .getEntity()
                .getThumbnails();
        Assert.assertNotNull(entity);
        Assert.assertTrue(entity.size() == 2);
    }

    @Test
    /**
     * Batch thumbnails operation
     */
    public void should_return_list_of_thumbnails_with_get_request_method() {
        List<ResourceThumbnail> entity = session.thumbnailsService()
                .thumbnails()
                .reports(asList("/public/Samples/Reports/08g.UnitSalesDetailReport",
                        "/public/Samples/Reports/11g.SalesByMonthReport"))
                .defaultAllowed(true).requestMethod(RequestMethod.GET)
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
    public void should_return_single_thumbnail_as_stream() throws IOException {
        InputStream entity = session.thumbnailsService()
                .thumbnail()
                .report("/public/Samples/Reports/08g.UnitSalesDetailReport")
                .defaultAllowed(true)
                .get()
                .getEntity();
        Assert.assertNotNull(entity);
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}