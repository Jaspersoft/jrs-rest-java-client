package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportParameter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ImportServiceTest extends Assert {

    private StateDto stateDto;

    @Test
    public void testCreateImportTask() throws URISyntaxException {
        URL url = Import.class.getClassLoader().getResource("myzip.zip");
        OperationResult<StateDto> operationResult =
                Import.newTask()
                        .parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, true)
                        .post(new File(url.toURI()));

        stateDto = operationResult.getEntity();
        assertNotEquals(stateDto, null);
    }

    @Test(dependsOnMethods = {"testCreateImportTask"})
    public void testGetImportTaskState() {
        OperationResult<StateDto> operationResult =
                Import.task(stateDto.getId()).state().get();
        StateDto state = operationResult.getEntity();

        assertNotEquals(state, null);
    }

}
