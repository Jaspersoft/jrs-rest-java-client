package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.*;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourceFilesMimeType;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourceSearchParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourceServiceParameter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourcesServiceTest extends Assert {

    private JasperserverRestClient client;

    public ResourcesServiceTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testResourcesSearchWithoutParams() {
        OperationResult<ClientResourceListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resources()
                .search();
        ClientResourceListWrapper resourceListWrapper = result.getEntity();
        assertNotNull(resourceListWrapper);
    }

    @Test
    public void testResourcesSearchWithParams() {
        OperationResult<ClientResourceListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resources()
                .parameter(ResourceSearchParameter.FOLDER_URI, "/reports/samples")
                .parameter(ResourceSearchParameter.LIMIT, "5")
                .search();
        ClientResourceListWrapper resourceListWrapper = result.getEntity();
        assertNotNull(resourceListWrapper);
        assertTrue(resourceListWrapper.getResourceLookups().size() <= 5);
    }

    @Test
    public void testGetResourceDetails() {
        OperationResult<ClientListOfValues> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/properties/GlobalPropertiesList")
                .details(ClientListOfValues.class);

        ClientListOfValues listOfValues = result.getEntity();
        assertNotNull(listOfValues);
    }

    @Test
    public void testGetResourceDetailsExpanded() {
        OperationResult<ClientReportUnit> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/interactive/CustomersReport")
                .parameter(ResourceServiceParameter.EXPANDED, "true")
                .details(ClientReportUnit.class);

        ClientReportUnit reportUnit = result.getEntity();
        assertNotNull(reportUnit);
        assertTrue(reportUnit.getJrxml() instanceof ClientFile);
    }

    @Test
    public void testDownloadBinaryFile() throws IOException {
        OperationResult<InputStream> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/themes/default/buttons.css")
                .downloadBinary(ResourceFilesMimeType.CSS);

        InputStream inputStream = result.getEntity();
        assertNotNull(inputStream);
    }

    @Test
    public void testCreateResourceWithExplicitId() {

        ClientFolder folder = new ClientFolder();
        folder
                .setUri("/reports/testFolder")
                .setLabel("Test Folder")
                .setDescription("Test folder description")
                .setPermissionMask(0)
                .setCreationDate("2014-01-24 16:27:47")
                .setUpdateDate("2014-01-24 16:27:47")
                .setVersion(0);

        OperationResult<ClientFolder> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource(folder.getUri())
                .createOrUpdate(false, ClientFolder.class, folder);

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = result.getEntity();
        assertNotNull(resultFolder);
    }

    @Test(dependsOnMethods = "testCreateResourceWithExplicitId")
    public void testCreateResourceWithImplicitId() {

        ClientFolder folder = new ClientFolder();
        folder
                .setUri("/reports/testFolder")
                .setLabel("Test Folder Auto Id")
                .setDescription("Test folder description")
                .setPermissionMask(0)
                .setCreationDate("2014-01-24 16:27:47")
                .setUpdateDate("2014-01-24 16:27:47")
                .setVersion(0);

        OperationResult<ClientFolder> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource(folder.getUri())
                .createOrUpdate(true, ClientFolder.class, folder);

        assertEquals(result.getResponse().getStatus(), 201);
        ClientFolder resultFolder = result.getEntity();
        assertNotNull(resultFolder);
    }

    @Test(dependsOnMethods = "testCreateResourceWithExplicitId")
    public void testPatchResource() {

        PatchDescriptor patchDescriptor = new PatchDescriptor();
        patchDescriptor.setVersion(0);
        patchDescriptor.field("label", "Patch Label");

        OperationResult<ClientFolder> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/testFolder")
                .patchResource(ClientFolder.class, patchDescriptor);

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = result.getEntity();
        assertNotNull(resultFolder);
        assertEquals(resultFolder.getLabel(), "Patch Label");
    }

    @Test(dependsOnMethods = "testPatchResource")
    public void testMoveResource() {

        OperationResult<ClientFolder> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/datasources")
                .move(ClientFolder.class, "/reports/testFolder");

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = result.getEntity();
        assertNotNull(resultFolder);
        assertEquals(resultFolder.getLabel(), "Patch Label");
        assertEquals(resultFolder.getUri(), "/datasources/testFolder");
    }

    @Test(dependsOnMethods = "testMoveResource")
    public void testCopyResource() {

        OperationResult<ClientFolder> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports")
                .copy(ClientFolder.class, "/datasources/testFolder");

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = result.getEntity();
        assertNotNull(resultFolder);
        assertEquals(resultFolder.getLabel(), "Patch Label");
        assertEquals(resultFolder.getUri(), "/reports/testFolder");
    }

    @Test(dependsOnMethods = "testCopyResource")
    public void testUploadFile() throws URISyntaxException {

        ClientFile file = new ClientFile();
        file
                .setUri("/reports/testFolder")
                .setLabel("Test Upload Image")
                .setDescription("Test image description")
                .setPermissionMask(0)
                .setCreationDate("2014-01-24 16:27:47")
                .setUpdateDate("2014-01-24 16:27:47")
                .setVersion(0)
                .setType(ClientFile.FileType.img);
        URL url = ResourcesServiceTest.class.getClassLoader().getResource("stateChart.png");


        OperationResult<ClientFile> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/testFolder")
                .uploadFile(file, new File(url.toURI()));

        assertEquals(result.getResponse().getStatus(), ResponseStatus.CREATED);
        ClientFile clientFile = result.getEntity();
        assertNotNull(clientFile);
    }

    @Test(dependsOnMethods = "testUploadFile")
    public void testDeleteResource() {

        OperationResult result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/testFolder")
                .delete();

        OperationResult result1 = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/datasources/testFolder")
                .delete();

        assertEquals(result.getResponse().getStatus(), ResponseStatus.NO_CONTENT);
        assertEquals(result1.getResponse().getStatus(), ResponseStatus.NO_CONTENT);
    }


}
