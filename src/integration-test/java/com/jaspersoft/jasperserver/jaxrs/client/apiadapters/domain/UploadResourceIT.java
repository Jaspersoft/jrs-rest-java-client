package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportParameter.UPDATE;
import static java.lang.Thread.sleep;

/**
 * @author Alexander Krasnyanskiy
 */
public class UploadResourceIT {

    // todo:  (run_by_group)  mvn integration-test -Dgroups=pro.version

    private static final String USERNAME = "superuser";
    private static final String PASSWORD = "superuser";

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeClass(groups = {"pro.version"})
    public void before() throws IOException, URISyntaxException, InterruptedException {
        config = new RestClientConfiguration(ConfigType.YML);
        client = new JasperserverRestClient(config);
        session = client.authenticate(USERNAME, PASSWORD);

        URI uri = getClass().getResource("/data/jrs-rest-client-integration-test-data.zip").toURI();
        File file = new File(uri);

        StateDto state = session.importService().newTask().parameter(UPDATE, true).create(file).getEntity();
        while (!state.getPhase().equals("finished")) {
            sleep(500);
            state = session.importService().task(state.getId()).state().getEntity();
        }
    }

    @Test(groups = {"pro.version"})
    public void should_upload_() throws InterruptedException {
        System.out.println("Yes!");
    }

    @Test(groups = {"ce.version"})
    public void should_upload() {
        System.out.println("No!");
    }

    @AfterClass(groups = {"pro.version"})
    public void after() throws InterruptedException {
        session.resourcesService().resource("/integrationTestFolder").delete();
        session = null;
        client = null;
        config = null;
    }
}
