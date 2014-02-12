package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.ReportJobCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder.buildRequest;

public class SingleCalendarOperationsAdapter {

    private final SessionStorage sessionStorage;
    private final String calendarName;
    private final MultivaluedMap<String, String> params;

    public SingleCalendarOperationsAdapter(SessionStorage sessionStorage, String calendarName) {
        this.sessionStorage = sessionStorage;
        this.calendarName = calendarName;
        params = new MultivaluedHashMap<String, String>();
    }

    public SingleCalendarOperationsAdapter parameter(CalendarParameter parameter, String value){
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<ReportJobCalendar> get(){
        return buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{"/jobs", "/calendars", calendarName})
                .get();
    }

    public OperationResult delete(){
        return buildRequest(sessionStorage, Object.class, new String[]{"/jobs", "/calendars", calendarName})
                .delete();
    }

    public OperationResult createOrUpdate(Calendar calendarDescriptor){
        JerseyRequestBuilder<Object> builder =
                buildRequest(sessionStorage, Object.class, new String[]{"/jobs", "/calendars", calendarName});
        builder.addParams(params);

        return builder.put(calendarDescriptor);
    }

}
