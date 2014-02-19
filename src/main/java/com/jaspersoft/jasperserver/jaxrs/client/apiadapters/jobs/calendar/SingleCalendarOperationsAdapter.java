package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.ReportJobCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class SingleCalendarOperationsAdapter extends AbstractAdapter {

    private final String calendarName;
    private final MultivaluedMap<String, String> params;

    public SingleCalendarOperationsAdapter(SessionStorage sessionStorage, String calendarName) {
        super(sessionStorage);
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
