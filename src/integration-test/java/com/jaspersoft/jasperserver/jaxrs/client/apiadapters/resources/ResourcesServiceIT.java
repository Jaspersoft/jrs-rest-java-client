package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.ByteArrayInputStream;
import javax.ws.rs.core.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 * @author tetiana Iefimenko
 */
public class ResourcesServiceIT extends RestClientTestUtil {

    @BeforeClass
    public void before() {
        initClient();
        initSession();
    }


    @Test
    public void should_delete_folder() throws InterruptedException {

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

    @Test
    public void should_return_resource_details() throws InterruptedException {

        // When
        ClientResource clientResource = session.resourcesService()
                .resource("/organizations/organization_1/Domains/supermartDomain")
                .details()
                .getEntity();

        Assert.assertNotNull(clientResource);
        Assert.assertNotNull(clientResource.getCreationDate());
    }



    @Test
    public void should_return_resource() throws InterruptedException {

        // When
        OperationResult<ClientFolder> clientFolderOperationResult = session.resourcesService()
                .resource("/").get(ClientFolder.class);
        Assert.assertTrue(clientFolderOperationResult.getResponse().getStatus() == 200);
        assertNotNull(clientFolderOperationResult.getEntity());
        assertNotNull(clientFolderOperationResult.getEntity().getVersion());

        OperationResult<ClientResource> clientResource = session.resourcesService()
                .resource("/").get(ClientResource.class);
        Assert.assertTrue(clientResource.getResponse().getStatus() == 200);
        assertNotNull(clientResource.getEntity());
        assertNotNull(clientResource.getEntity().getVersion());



    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}