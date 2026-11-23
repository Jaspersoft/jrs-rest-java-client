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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar;

//import com.jaspersoft.jasperserver.dto.job.ClientJobCalendar;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleCalendarOperationsAdapter extends AbstractAdapter {

    public static final String JOBS = "alerts";
    public static final String CALENDARS = "calendars";
    private final String calendarName;
    private final MultivaluedMap<String, String> params;

    public SingleCalendarOperationsAdapter(SessionStorage sessionStorage, String calendarName) {
        super(sessionStorage);
        this.calendarName = calendarName;
        params = new MultivaluedHashMap<>();
    }

    public SingleCalendarOperationsAdapter parameter(CalendarParameter parameter, String value) {
        params.add(parameter.getName(), UrlUtils.encode(value));
        return this;
    }

    public OperationResult<ClientAlertCalendar> getCalendar() {
        //OperationResult<ClientAlertCalendar> result = buildRequest(sessionStorage, ClientAlertCalendar.class, new String[]{JOBS, CALENDARS, calendarName}).get();
        OperationResult<ClientAlertCalendar> result = buildRequest(sessionStorage, ClientAlertCalendar.class, new String[]{JOBS, CALENDARS, calendarName}).get();
        return result;
    }

    public <R> RequestExecution asyncGetCalendar(final Callback<OperationResult<ClientAlertCalendar>, R> callback) {
        final JerseyRequest<ClientAlertCalendar> request = buildRequest(sessionStorage, ClientAlertCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        return buildRequest(sessionStorage, Object.class, new String[]{JOBS, CALENDARS, calendarName}).delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(sessionStorage, Object.class, new String[]{JOBS, CALENDARS, calendarName});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientAlertCalendar> createNewCalendar(ClientAlertCalendar calendarDescriptor) {
        JerseyRequest<ClientAlertCalendar> request = buildRequest(sessionStorage, ClientAlertCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
        request.addParams(params);
        return request.put(calendarDescriptor);
    }

    public <R> RequestExecution asyncCreateNewCalendar(final ClientAlertCalendar calendarDescriptor, final Callback<OperationResult<ClientAlertCalendar>, R> callback) {
        final JerseyRequest<ClientAlertCalendar> request = buildRequest(sessionStorage, ClientAlertCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
        request.addParams(params);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(calendarDescriptor));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

}
