package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._import.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._import.ImportParameter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ImportServiceTest extends Assert {

    private StateDto stateDto;

    @Test
    public void testCreateImportTask() throws URISyntaxException {
        URL url = ImportService.class.getClassLoader().getResource("myzip.zip");
        OperationResult<StateDto> operationResult =
                JasperserverRestClient
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
                JasperserverRestClient
                        .authenticate("jasperadmin", "jasperadmin")
                        .importService()
                        .task(stateDto.getId())
                        .state();

        StateDto state = operationResult.getEntity();
        assertNotEquals(state, null);
    }

}
