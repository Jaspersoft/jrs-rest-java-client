package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice.ImportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ImportServiceTest extends Assert {

    private static JasperserverRestClient client;
    private StateDto stateDto;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testCreateImportTask() throws URISyntaxException {
        URL url = ImportService.class.getClassLoader().getResource("testExportArchive.zip");
        OperationResult<StateDto> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .importService()
                        .newTask()
                        .parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, true)
                        .create(new File(url.toURI()));

        stateDto = operationResult.getEntity();
        assertNotEquals(stateDto, null);
    }

    @Test(dependsOnMethods = {"testCreateImportTask"})
    public void testGetImportTaskState() {
        OperationResult<StateDto> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .importService()
                        .task(stateDto.getId())
                        .state();

        StateDto state = operationResult.getEntity();
        assertNotEquals(state, null);
    }

}
