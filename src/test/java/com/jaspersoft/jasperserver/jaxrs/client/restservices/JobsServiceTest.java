/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.restservices;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.ResponseStatus;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.*;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.WeeklyCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.CalendarNameListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.JobSummaryListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.reportjobmodel.ReportJobModel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.TimeZone;

@Test
public class JobsServiceTest extends Assert {

    private static JasperserverRestClient client;
    private Job job;

    public JobsServiceTest() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("url.properties");
        client = new JasperserverRestClient(configuration);
    }

    @AfterClass
    public static void tearDown(){
        OperationResult<JobSummaryListWrapper> allJobs = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService().jobs().search();
        for (JobSummary job : allJobs.getEntity().getJobsummary()){
            if (job.getId() != 21281)
                client
                        .authenticate("jasperadmin", "jasperadmin")
                        .jobsService()
                        .job(job.getId())
                        .delete();
        }
    }

    @Test
    public void testGetAllJobs(){
        OperationResult<JobSummaryListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .search();

        int status = result.getResponse().getStatus();
        assertTrue(status == 200 || status == 204);
        JobSummaryListWrapper jobSummaryListWrapper = result.getEntity();
        assertTrue(jobSummaryListWrapper.getJobsummary().size() == 1);

    }

    @Test
    public void testViewJobDefinition(){
        OperationResult<Job> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job(21281)
                .get();

        job = result.getEntity();
        assertNotNull(job);
    }

    @Test(dependsOnMethods = "testViewJobDefinition")
    public void testEditJobDefinition(){
        String label = "updatedLabel";
        Long jobId = job.getId();
        job.setLabel(label);

        OperationResult<Job> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .job(jobId)
                .update(job);
        assertNotNull(result);
        Job job = result.getEntity();
        assertNotNull(job);
        this.job = job;
        assertEquals(job.getLabel(), label);
    }

    @Test(dependsOnMethods = "testEditJobDefinition")
    public void testExtendedJobSearch(){

        Job criteria = new Job();
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

        OperationResult<Job> result = client
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
                .job(21281)
                .state();
        assertNotNull(result);
        JobState jobState = result.getEntity();
        assertNotNull(jobState);
    }

    @Test(dependsOnMethods = "testScheduleJob", enabled = false)
    public void testUpdateJobsInBulkWithJobModel(){
        ReportJobModel jobModel = new ReportJobModel();
        jobModel.setDescription("Bulk update description");

        OperationResult<JobIdListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.JOB_ID, "21281")
                .updateWithProcessedParameters(jobModel);

        assertNotNull(result);
        assertEquals(result.getResponse().getStatus(), ResponseStatus.OK);
    }

    @Test//(dependsOnMethods = "testScheduleJob")
    public void testUpdateJobsInBulkWithJobDescriptor(){
        ReportJobModel jobDescriptor = new ReportJobModel();
        jobDescriptor.setDescription("Bulk update description");

        OperationResult<JobIdListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.JOB_ID, "21281")
                .update(jobDescriptor);

        assertNotNull(result);
        assertEquals(result.getResponse().getStatus(), ResponseStatus.OK);
    }

    @Test(dependsOnMethods = "testUpdateJobsInBulkWithJobDescriptor")
    public void testResumeJobs(){
        OperationResult<JobIdListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.JOB_ID, "21281")
                .resume();
        assertNotNull(result);
        assertNotNull(result.getEntity());
    }

    @Test(dependsOnMethods = "testResumeJobs")
    public void testPauseJobs(){
        OperationResult<JobIdListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.JOB_ID, "21281")
                .pause();
        assertNotNull(result);
        assertNotNull(result.getEntity());
    }

    private static List<Long> restartedJobId;

    @Test(dependsOnMethods = {"testPauseJobs", "testGetAllJobs"})
    public void testRestartJobs(){
        OperationResult<JobIdListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .jobs()
                .parameter(JobsParameter.JOB_ID, "21281")
                .restart();
        assertNotNull(result);
        JobIdListWrapper entity = result.getEntity();
        assertNotNull(entity);

        restartedJobId = entity.getIds();
    }

    /*@Test
    public void testSpecifyFtpOutput(){
        client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .scheduleReport(new Object());
    }*/

    @Test
    public void testListAllRegisteredCalendars(){
        OperationResult<CalendarNameListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendars();
        assertNotNull(result);
        CalendarNameListWrapper calendarNameListWrapper = result.getEntity();
        assertNotNull(calendarNameListWrapper);
    }

    @Test
    public void testListAllRegisteredCalendarNamesWithType(){
        OperationResult<CalendarNameListWrapper> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendars(CalendarType.holiday);
        assertNotNull(result);
        CalendarNameListWrapper calendarNameListWrapper = result.getEntity();
        assertNotNull(calendarNameListWrapper);
    }

    @Test
    public void testAddOrUpdateCalendar(){

        WeeklyCalendar calendar = new WeeklyCalendar();
        calendar.setDescription("lalala");
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+03:00"));
        calendar.setExcludeDaysFlags(new boolean[]{true, false, false, false, false, true, true});

        OperationResult result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendar("testCalendar")
                .createNew(calendar);
        assertNotNull(result);
        assertEquals(result.getResponse().getStatus(), ResponseStatus.UPDATED);
    }

    @Test(dependsOnMethods = "testAddOrUpdateCalendar")
    public void testViewCalendar(){
        OperationResult<Calendar> result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendar("testCalendar")
                .get();
        assertNotNull(result);
        Calendar jobCalendar = result.getEntity();
        assertNotNull(jobCalendar);
        assertTrue(jobCalendar instanceof WeeklyCalendar);
    }

    @Test(dependsOnMethods = "testViewCalendar")
    public void testDeleteCalendar(){
        OperationResult result = client
                .authenticate("jasperadmin", "jasperadmin")
                .jobsService()
                .calendar("testCalendar")
                .delete();
        assertNotNull(result);
        assertEquals(result.getResponse().getStatus(), ResponseStatus.OK);
    }

}
