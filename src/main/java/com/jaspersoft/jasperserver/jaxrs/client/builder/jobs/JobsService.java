package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar.SingleCalendarOperationsAdapter;

public class JobsService {


    private final SessionStorage sessionStorage;

    public JobsService(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public BatchJobsOperationsAdapter jobs(){
        return new BatchJobsOperationsAdapter(sessionStorage);
    }

    public SingleJobOperationsAdapter job(String jobId){
        return new SingleJobOperationsAdapter(sessionStorage, jobId);
    }

    public void scheduleReport(Object report){
        throw new UnsupportedOperationException();
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
