package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.job;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSource;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.OutputFormat;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.RepositoryDestination;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.SimpleTrigger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class JobServiceIT extends ClientConfigurationFactory {

    private Session session;
    private Long jobId;

    @BeforeClass
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_schedule_a_job() {

        Job job = new Job();

        job.setLabel("NewReport");
        job.setDescription("My scheduled report");
        job.setBaseOutputFilename("AllAccounts");
        job.setOutputFormats(new HashSet<OutputFormat>() {{
            add(OutputFormat.PDF);
        }});


        RepositoryDestination dest = new RepositoryDestination();
        dest.setFolderURI("/public/Samples/Reports");

        job.setRepositoryDestination(dest);

        SimpleTrigger trigger = new SimpleTrigger();
        trigger.setOccurrenceCount(1);
        trigger.setStartType(2);
        trigger.setStartDate(new Date(2013, 9, 26, 10, 0));


        job.setTrigger(trigger);


        JobSource source = new JobSource();
        source.setReportUnitURI("/public/Samples/Reports/AllAccounts");

        job.setSource(source);

        Job persisted = session
                .jobsService()
                .scheduleReport(job)
                .getEntity();

        jobId = persisted.getId();
        assertNotNull(jobId);
    }

    @Test(dependsOnMethods = "should_schedule_a_job")
    public void should_retrieve_job_by_id() {
        assertNotNull(jobId);
        Job retrieved = session.jobsService().job(jobId).get().entity();
        assertNotNull(retrieved);
        assertNotEquals(retrieved.getLabel(), "WrongLabel");
        assertEquals(retrieved.getLabel(), "NewReport");
    }

    @AfterClass
    public void after() {
        session.jobsService().job(jobId).delete();
        session.logout();
    }
}
