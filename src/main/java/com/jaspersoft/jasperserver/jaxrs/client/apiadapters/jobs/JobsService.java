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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.CalendarNameListWrapper;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class JobsService extends AbstractAdapter {

    public JobsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchJobsOperationsAdapter jobs(){
        return new BatchJobsOperationsAdapter(sessionStorage);
    }

    public SingleJobOperationsAdapter job(long jobId){
        return new SingleJobOperationsAdapter(sessionStorage, String.valueOf(jobId));
    }

    public OperationResult<JobExtension> scheduleReport(JobExtension report){
        JerseyRequest<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs"}, new JobValidationErrorHandler());
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.put(report);
    }

    public <R> RequestExecution asyncScheduleReport(final JobExtension report, final Callback<OperationResult<JobExtension>, R> callback) {
        final JerseyRequest<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs"}, new JobValidationErrorHandler());
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.put(report));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<CalendarNameListWrapper> calendars(){
        return calendars(null);
    }

    public <R> RequestExecution asyncCalendars(final Callback<OperationResult<CalendarNameListWrapper>, R> callback) {
        return asyncCalendars(null, callback);
    }

    public OperationResult<CalendarNameListWrapper> calendars(CalendarType type){
        JerseyRequest<CalendarNameListWrapper> builder =
                buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{"/jobs", "/calendars"});
        if (type != null)
            builder.addParam("calendarType", type.name().toLowerCase());

        return builder.get();
    }

    public <R> RequestExecution asyncCalendars(final CalendarType type, final Callback<OperationResult<CalendarNameListWrapper>, R> callback) {
        final JerseyRequest<CalendarNameListWrapper> builder =
                buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{"/jobs", "/calendars"});
        if (type != null)
            builder.addParam("calendarType", type.name().toLowerCase());

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(builder.get());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public SingleCalendarOperationsAdapter calendar(String calendarName){
        if ("".equals(calendarName) || "/".equals(calendarName))
            throw new  IllegalArgumentException("'calendarName' mustn't be an empty string");
        return new SingleCalendarOperationsAdapter(sessionStorage, calendarName);
    }

}
