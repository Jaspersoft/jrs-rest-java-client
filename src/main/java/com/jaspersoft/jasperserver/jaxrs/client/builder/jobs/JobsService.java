package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.wrappers.CalendarNameListWrapper;

import static com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder.buildRequest;

public class JobsService {


    private final SessionStorage sessionStorage;

    public JobsService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public BatchJobsOperationsAdapter jobs(){
        return new BatchJobsOperationsAdapter(sessionStorage);
    }

    public SingleJobOperationsAdapter job(long jobId){
        return new SingleJobOperationsAdapter(sessionStorage, String.valueOf(jobId));
    }

    public OperationResult<JobExtension> scheduleReport(JobExtension report){
        JerseyRequestBuilder<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs"});
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.put(report);
    }

    public OperationResult<CalendarNameListWrapper> calendars(){
        return calendars(null);
    }

    public OperationResult<CalendarNameListWrapper> calendars(CalendarType type){
        JerseyRequestBuilder<CalendarNameListWrapper> builder =
                buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{"/jobs", "/calendars"});
        if (type != null)
            builder.addParam("calendarType", type.name().toLowerCase());

        return builder.get();
    }

    public SingleCalendarOperationsAdapter calendar(String name){
        return new SingleCalendarOperationsAdapter(sessionStorage, name);
    }

}
