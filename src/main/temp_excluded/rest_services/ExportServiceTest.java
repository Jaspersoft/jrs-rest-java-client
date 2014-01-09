package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

public class ExportServiceTest extends Assert {

    private StateDto stateDto;

    @Test
    public void testCreateExportTask(){
        OperationResult<StateDto> operationResult =
                Export.newTask()
                        .user("jasperadmin")
                        .role("ROLE_USER")
                        //.parameter(ExportParameter.EVERYTHING)
                        .post();

        stateDto = operationResult.getEntity();

        assertNotEquals(stateDto, null);
    }

    @Test(dependsOnMethods = {"testCreateExportTask"})
    public void testCreateExportTaskAndGet(){
        OperationResult<StateDto> operationResult =
                Export.task(stateDto.getId()).state().get();
        StateDto state = operationResult.getEntity();
        assertNotEquals(state, null);
    }

    @Test(dependsOnMethods = {"testCreateExportTask", "testCreateExportTaskAndGet"})
    public void testGetExportInputStream() throws InterruptedException, IOException {
        StateDto state;

        while (true){
            OperationResult<StateDto> operationResult =
                    Export.task(stateDto.getId()).state().get();
            state = operationResult.getEntity();
            if ("finished".equals(state.getPhase())){
                OperationResult<InputStream> operationResult1 =
                        Export.task(stateDto.getId()).fetch().get();
                InputStream inputStream = operationResult1.getEntity();
                assertNotEquals(inputStream, null);
                inputStream.close();
                break;
            }
            else {
                Thread.sleep(500);
            }
        }

    }

}
