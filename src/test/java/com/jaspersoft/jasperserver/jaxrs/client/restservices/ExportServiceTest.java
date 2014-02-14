package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice.ExportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

public class ExportServiceTest extends Assert {

    private static JasperserverRestClient client;
    private StateDto stateDto;

    @BeforeClass
    public static void setUp() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testCreateExportTask() {
        OperationResult<StateDto> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .exportService()
                        .newTask()
                        .user("jasperadmin")
                        .role("ROLE_USER")
                        .parameter(ExportParameter.EVERYTHING)
                        .create();

        stateDto = operationResult.getEntity();

        assertNotEquals(stateDto, null);
    }

    @Test(dependsOnMethods = {"testCreateExportTask"})
    public void testCreateExportTaskAndGet() {
        OperationResult<StateDto> operationResult =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .exportService()
                        .task(stateDto.getId())
                        .state();

        StateDto state = operationResult.getEntity();
        assertNotEquals(state, null);
    }

    @Test(dependsOnMethods = {"testCreateExportTask", "testCreateExportTaskAndGet"})
    public void testGetExportInputStream() throws InterruptedException, IOException {
        OperationResult<InputStream> operationResult1 =
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .exportService()
                        .task(stateDto.getId())
                        .fetch();

        InputStream inputStream = operationResult1.getEntity();
        assertNotEquals(inputStream, null);
        inputStream.close();
    }

}
