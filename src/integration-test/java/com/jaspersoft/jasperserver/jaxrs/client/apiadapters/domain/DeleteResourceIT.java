package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander Krasnyanskiy
 */
public class DeleteResourceIT {

    private static final String USERNAME = "superuser";
    private static final String PASSWORD = "superuser";

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() throws IOException, URISyntaxException, InterruptedException {
        config = new RestClientConfiguration(ConfigType.YML);
        client = new JasperserverRestClient(config);
        session = client.authenticate(USERNAME, PASSWORD);

        URI uri = getClass().getResource("/data/jrs-rest-client-integration-test-data.zip").toURI();
        File file = new File(uri);

        RequestExecution execution = session.importService().newTask().asyncCreate(file, new Callback<OperationResult<StateDto>, Integer>() {
            @Override
            public Integer execute(OperationResult<StateDto> data) {
                return 100500;
            }
        });

        execution.getFuture();
        while (!execution.getFuture().isDone()){
            sleep(500);
        }
    }

    @Test
    public void test() throws InterruptedException {
        assertTrue(1 == 1);
    }

    @AfterMethod
    public void after() throws InterruptedException {
        RequestExecution execution = session.resourcesService().resource("/integrationTestFolder").asyncDelete(new Callback<OperationResult, Integer>() {
            @Override
            public Integer execute(OperationResult data) {
                return 100500;
            }
        });

        execution.getFuture();
        while (!execution.getFuture().isDone()){
            sleep(500);
        }
    }
}
