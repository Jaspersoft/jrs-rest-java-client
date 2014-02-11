package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;

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

    public OperationResult<Job> scheduleReport(Job report){
        JerseyRequestBuilder<Job> builder =
                buildRequest(sessionStorage, Job.class, new String[]{"/jobs"});
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.put(report);
    }

    public OperationResult calendars(){
        return calendars(null);
    }

    public OperationResult calendars(CalendarType type){
        throw new UnsupportedOperationException();
    }

    public SingleCalendarOperationsAdapter calendar(String name){
        return new SingleCalendarOperationsAdapter(sessionStorage, name);
    }

}
