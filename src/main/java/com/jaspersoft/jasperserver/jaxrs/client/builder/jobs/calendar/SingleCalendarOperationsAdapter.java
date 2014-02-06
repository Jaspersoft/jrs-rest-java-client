package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs.calendar;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

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

    public OperationResult get(){
        throw new UnsupportedOperationException();
    }

    public OperationResult delete(){
        throw new UnsupportedOperationException();
    }

    public OperationResult createOrUpdate(Object calendarDescriptor){
        throw new UnsupportedOperationException();
    }

}
