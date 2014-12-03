package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;

import static java.lang.Thread.sleep;

/**
* @author Alexander Krasnyanskiy
*/
public class ImportServiceIT extends ClientConfigurationFactory {


    private Session session;
    private StateDto state;
    private InputStream importData;

    @BeforeClass
    public void before() {
        String fileName = "jsrc-it-data-for-import-service-test.zip";
        session = getClientSession(ConfigType.YML);
        importData = ImportServiceIT.class.getResourceAsStream("/data/" + fileName);
    }

    @Test
    public void should_upload_import_file_to_JRS() throws InterruptedException {
        state = session.importService()
                .newTask()
                .create(importData)
                .entity();

        Assert.assertNotNull(state);
        waitForUpload();
        Assert.assertEquals(getPhase(), "finished");
    }

    private void waitForUpload() throws InterruptedException {
        String currentPhase = "undefined";
        do {
            currentPhase = getPhase();
            if (currentPhase.equals("finished")) {
                break;
            } else {
                sleep(500);
            }
        } while (true);
    }

    private String getPhase() {
        if (state != null) {
            return session.exportService().task(state.getId()).state().entity().getPhase();
        }
        throw new RuntimeException("state is null");
    }

    @AfterClass
    public void after() {

        session.resourcesService().resource("/public/Samples/TestReportResource").delete();
        session.logout();
        importData = null;
        state = null;
    }
}
