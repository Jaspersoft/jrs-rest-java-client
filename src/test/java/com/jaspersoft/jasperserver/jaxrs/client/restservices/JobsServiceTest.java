package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.jaxrs.client.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.JobsParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSummaryListWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class JobsServiceTest extends Assert {

    private JasperserverRestClient client;

    public JobsServiceTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @Test
    public void testSchedulingReport(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .scheduleReport(new Object());
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

    }

    @Test
    public void testViewJobDefinition(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job("")
                .get();
    }

    @Test
    public void testExtendedJobSearch(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .search(new Object());
    }

    @Test
    public void testViewJobStatus(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job("")
                .state();
    }

    @Test
    public void testEditJobDefinition(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job("")
                .update();
    }

    @Test
    public void testUpdateJobsInBulk(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.valueOf("someParam"), "")
                .update(new Object());
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

    @Test
    public void testSpecifyFtpOutput(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .scheduleReport(new Object());
    }

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
