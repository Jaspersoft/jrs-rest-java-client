package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.dto.job.JobIdListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.JobsParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.*;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.ReportJobModel;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.JobSummaryListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.OutputFormatsListWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class JobsServiceTest extends Assert {

    private JasperserverRestClient client;
    private JobExtension job;

    public JobsServiceTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testGetAllJobs(){
        OperationResult<JobSummaryListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .get();

        int status = result.getResponse().getStatus();
        assertTrue(status == 200 || status == 204);
        JobSummaryListWrapper jobSummaryListWrapper = result.getEntity();
        assertTrue(jobSummaryListWrapper.getJobsummary().size() == 1);

    }

    @Test
    public void testViewJobDefinition(){
        OperationResult<JobExtension> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job(489)
                .get();

        job = result.getEntity();
        assertNotNull(job);
    }

    @Test(dependsOnMethods = "testViewJobDefinition")
    public void testEditJobDefinition(){
        String label = "updatedLabel";
        Long jobId = job.getId();
        job.setLabel(label);

        OperationResult<JobExtension> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job(jobId)
                .update(job);
        assertNotNull(result);
        JobExtension job = result.getEntity();
        assertNotNull(job);
        this.job = job;
        assertEquals(job.getLabel(), label);
    }

    @Test(dependsOnMethods = "testEditJobDefinition")
    public void testExtendedJobSearch(){

        JobExtension criteria = new JobExtension();
        criteria.setLabel("updatedLabel");
        criteria.setAlert(new JobAlert());        //tests nested objects

        OperationResult<JobSummaryListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.SEARCH_LABEL, "hello")
                .search(criteria);

        JobSummaryListWrapper jobSummaryListWrapper = result.getEntity();
        assertNotNull(jobSummaryListWrapper);
    }

    @Test(dependsOnMethods = "testEditJobDefinition")
    public void testScheduleJob(){
        job.setLabel("NewScheduledReport");
        job.setDescription("blablabla");
        JobSource source = job.getSource();
        source.setReportUnitURI("/reports/samples/Employees");

        OperationResult<JobExtension> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .scheduleReport(job);

        assertNotNull(result);
        job = result.getEntity();
        assertNotNull(job);
    }

    @Test(dependsOnMethods = "testScheduleJob")
    public void testDeleteJob(){
        OperationResult result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job(job.getId())
                .delete();

        assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    public void testViewJobStatus(){
        OperationResult<JobState> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job(489)
                .state();
        assertNotNull(result);
        JobState jobState = result.getEntity();
        assertNotNull(jobState);
    }

    @Test(dependsOnMethods = "testScheduleJob")
    public void testUpdateJobsInBulk(){
        ReportJobModel jobModel = new ReportJobModel();
        jobModel.setDescription("Bulk update description");

        OperationResult<JobIdListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.UPDATE_JOB_ID, "489")
                .update(jobModel);

        assertNotNull(result);
        assertEquals(result.getResponse().getStatus(), ResponseStatus.OK);
    }

    @Test
    public void testPauseJobs(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.valueOf("jobId"), "jobId")
                .pause();
    }

    @Test
    public void testResumeJobs(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.valueOf("jobId"), "jobId")
                .resume();
    }

    @Test
    public void testRestartJobs(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.valueOf("jobId"), "jobId")
                .restart();
    }

    /*@Test
    public void testSpecifyFtpOutput(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .scheduleReport(new Object());
    }*/

    @Test
    public void testListAllRegisteredCalendarNames(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendars(CalendarType.valueOf("someType"));
    }

    @Test
    public void testViewExclusionCalendar(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendar("")
                .get();
    }

    @Test
    public void testDeleteExclusionCalendar(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendar("")
                .delete();
    }

    @Test
    public void testAddOrUpdateExclusionCalendar(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendar("")
                .parameter(CalendarParameter.valueOf("someParam"), "")
                .createOrUpdate(new Object());
    }

}
