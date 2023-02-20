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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.dto.job.ClientJobCalendar;
import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.dto.job.wrappers.ClientCalendarNameListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class JobsService extends AbstractAdapter {

    public static final String SERVICE_URI = "jobs";
    public static final String CALENDARS = "calendars";

    public JobsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchJobsOperationsAdapter jobs() {
        return new BatchJobsOperationsAdapter(sessionStorage);
    }

    public BatchJobsOperationsAdapter jobs(Long... ids) {
        return new BatchJobsOperationsAdapter(sessionStorage, ids);
    }

    public SingleJobOperationsAdapter job(long jobId) {
        return new SingleJobOperationsAdapter(sessionStorage, String.valueOf(jobId));
    }

    public SingleJobOperationsAdapter job(ClientReportJob reportJob) {
        return new SingleJobOperationsAdapter(sessionStorage, reportJob);
    }

    public OperationResult<ClientCalendarNameListWrapper> allCalendars() {
        return calendar((ClientJobCalendar.Type) null);
    }

    public <R> RequestExecution asyncCalendar(final Callback<OperationResult<ClientCalendarNameListWrapper>, R> callback) {
        return asyncCalendar(null, callback);
    }

    public OperationResult<ClientCalendarNameListWrapper> calendar(ClientJobCalendar.Type type) {
        JerseyRequest<ClientCalendarNameListWrapper> request = buildRequest(sessionStorage, ClientCalendarNameListWrapper.class, new String[]{SERVICE_URI, CALENDARS});
        if (type != null) {
            request.addParam("calendarType", type.name().toLowerCase());
        }
        return request.get();
    }

    public <R> RequestExecution asyncCalendar(final ClientJobCalendar.Type type, final Callback<OperationResult<ClientCalendarNameListWrapper>, R> callback) {
        final JerseyRequest<ClientCalendarNameListWrapper> request = buildRequest(sessionStorage, ClientCalendarNameListWrapper.class, new String[]{SERVICE_URI, CALENDARS});
        if (type != null) {
            request.addParam("calendarType", type.name().toLowerCase());
        }
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public SingleCalendarOperationsAdapter calendar(String calendarName) {
        if ("".equals(calendarName) || "/".equals(calendarName)) {
            throw new IllegalArgumentException("'calendarName' mustn't be an empty string");
        }
        return new SingleCalendarOperationsAdapter(sessionStorage, calendarName);
    }

}
