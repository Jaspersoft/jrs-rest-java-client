package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.EVERYTHING;
import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class ExportServiceIT extends ClientConfigurationFactory {

    private Session session;
    private StateDto state;

    @BeforeClass
    public void before() {
        session = getClientSession(ConfigType.YML);
    }


    @Test
    public void should_return_state_instance() {
        state = session.exportService()
                .newTask()
                .user("superuser")
                .role("SUPERUSER")
                .parameter(EVERYTHING)
                .create()
                .entity();
        assertNotNull(state);
    }


    @Test(dependsOnMethods = "should_return_state_instance")
    public void should_return_state_instance_with_job_state() {
        state = session.exportService()
                .task(state.getId())
                .state()
                .entity();
        assertNotNull(state);
    }


//    @Test(dependsOnMethods = "should_return_state_instance_with_job_state")
//    public void should_return_zip_file_with_export_result() {
//        InputStream zipFile = session.exportService()
//                .task(state.getId())
//                .fetch()
//                .entity();
//        assertNotNull(zipFile);
//    }


    @AfterClass
    public void after() {
        session.logout();
    }
}
