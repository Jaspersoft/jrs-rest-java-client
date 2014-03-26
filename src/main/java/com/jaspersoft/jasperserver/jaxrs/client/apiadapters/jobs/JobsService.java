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
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.CalendarNameListWrapper;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class JobsService extends AbstractAdapter {

    private ErrorHandler errorHandler;

    public JobsService(SessionStorage sessionStorage) {
        super(sessionStorage);
        errorHandler = new DefaultErrorHandler();
    }

    public BatchJobsOperationsAdapter jobs(){
        return new BatchJobsOperationsAdapter(sessionStorage);
    }

    public SingleJobOperationsAdapter job(long jobId){
        return new SingleJobOperationsAdapter(sessionStorage, String.valueOf(jobId));
    }

    public OperationResult<JobExtension> scheduleReport(JobExtension report){
        JerseyRequestBuilder<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs"}, new JobValidationErrorHandler());
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.put(report);
    }

    public OperationResult<CalendarNameListWrapper> calendars(){
        return calendars(null);
    }

    public OperationResult<CalendarNameListWrapper> calendars(CalendarType type){
        JerseyRequestBuilder<CalendarNameListWrapper> builder =
                buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{"/jobs", "/calendars"}, errorHandler);
        if (type != null)
            builder.addParam("calendarType", type.name().toLowerCase());

        return builder.get();
    }

    public SingleCalendarOperationsAdapter calendar(String calendarName){
        if ("".equals(calendarName) || "/".equals(calendarName))
            throw new  IllegalArgumentException("'calendarName' mustn't be an empty string");
        return new SingleCalendarOperationsAdapter(sessionStorage, calendarName);
    }

}
