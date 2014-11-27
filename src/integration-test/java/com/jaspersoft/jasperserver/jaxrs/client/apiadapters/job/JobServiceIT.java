package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.job;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class JobServiceIT extends ClientConfigurationFactory {

    private Session session;
    private Long jobId;

    @BeforeMethod
    public void before() {
        session = getClientSession(ConfigType.YML);
    }


    @Test
    public void should_schedule_a_job() {

        Job newJob = new Job();
        newJob.setLabel("NewReport");
        newJob.setDescription("My new cool scheduled report");
        JobSource source = newJob.getSource();
        source.setReportUnitURI("/reports/samples/Employees");

        Job persisted = session.jobsService()
                .scheduleReport(newJob)
                .getEntity();

        jobId = persisted.getId();
        assertNotNull(jobId);
    }


    @Test (dependsOnMethods = "should_schedule_a_job")
    public void should_() {
        Job retrieved = session.jobsService().job(jobId).get().entity();
        assertNotNull(retrieved);
        assertNotEquals(retrieved.getLabel(), "WrongLabel");
        assertEquals(retrieved.getLabel(), "NewReport");
    }


    @AfterMethod
    public void after() {
        session.logout();
        session = null;
    }
}
