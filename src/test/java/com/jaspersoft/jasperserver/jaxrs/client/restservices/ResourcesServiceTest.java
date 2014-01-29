package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.*;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourceSearchParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourceServiceParameter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/properties/GlobalPropertiesList")
                .details();

        ClientListOfValues listOfValues = (ClientListOfValues) result.getEntity();
        assertNotNull(listOfValues);
    }

    @Test
    public void testGetRootFolderDetails(){
        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/")
                .details();

        assertTrue(result.getEntity() instanceof ClientFolder);
        ClientFolder rootFolder = (ClientFolder) result.getEntity();
        assertNotNull(rootFolder);
    }

    @Test
    public void testGetFileDetails() {
        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/images/JRLogo")
                .details();

        ClientFile jrLogo = (ClientFile) result.getEntity();
        assertNotNull(jrLogo);
    }

    @Test
    public void testGetResourceDetailsExpanded() {
        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/interactive/CustomersReport")
                .parameter(ResourceServiceParameter.EXPANDED, "true")
                .details();

        ClientReportUnit reportUnit = (ClientReportUnit) result.getEntity();
        assertNotNull(reportUnit);
        assertTrue(reportUnit.getJrxml() instanceof ClientFile);
    }

    @Test
    public void testDownloadBinaryFile() throws IOException {
        OperationResult<InputStream> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/themes/default/buttons.css")
                .downloadBinary();

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

        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource(folder.getUri())
                .createOrUpdate(folder);

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = (ClientFolder) result.getEntity();
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

        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource(folder.getUri())
                .createNew(folder);

        assertEquals(result.getResponse().getStatus(), 201);
        ClientFolder resultFolder = (ClientFolder) result.getEntity();
        assertNotNull(resultFolder);
    }

    @Test(dependsOnMethods = "testCreateResourceWithExplicitId")
    public void testPatchResourceWithSpecifiedType() {

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

    @Test(dependsOnMethods = "testPatchResourceWithSpecifiedType", expectedExceptions = UnsupportedOperationException.class)
    public void testPatchResourceWithoutSpecifiedType() {

        PatchDescriptor patchDescriptor = new PatchDescriptor();
        patchDescriptor.setVersion(0);
        patchDescriptor.field("label", "Patch Label");

        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/testFolder")
                .patchResource(patchDescriptor);
    }

    @Test(dependsOnMethods = "testPatchResourceWithoutSpecifiedType")
    public void testMoveResource() {

        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/datasources")
                .moveFrom("/reports/testFolder");

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = (ClientFolder) result.getEntity();
        assertNotNull(resultFolder);
        assertEquals(resultFolder.getLabel(), "Patch Label");
        assertEquals(resultFolder.getUri(), "/datasources/testFolder");
    }

    @Test(dependsOnMethods = "testMoveResource")
    public void testCopyResource() {

        OperationResult<ClientResource> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports")
                .copyFrom("/datasources/testFolder");

        assertEquals(result.getResponse().getStatus(), 200);
        ClientFolder resultFolder = (ClientFolder) result.getEntity();
        assertNotNull(resultFolder);
        assertEquals(resultFolder.getLabel(), "Patch Label");
        assertEquals(resultFolder.getUri(), "/reports/testFolder");
    }

    @Test(dependsOnMethods = "testCopyResource")
    public void testUploadFile() throws URISyntaxException {
        URL url = ResourcesServiceTest.class.getClassLoader().getResource("stateChart.png");

        OperationResult<ClientFile> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .resourcesService()
                .resource("/reports/testFolder")
                .uploadFile(new File(url.toURI()), ClientFile.FileType.img, "testFile", "testFileDesc");

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
