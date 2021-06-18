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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.jaxb.wrappers.CalendarNameListWrapper;

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
@Deprecated
    public OperationResult<ClientReportJob> scheduleReport(ClientReportJob report) {
        JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        return request.put(report);
    }
@Deprecated
    public <R> RequestExecution asyncScheduleReport(final ClientReportJob report, final Callback<OperationResult<ClientReportJob>, R> callback) {
        final JerseyRequest<ClientReportJob> request = buildRequest(sessionStorage, ClientReportJob.class, new String[]{SERVICE_URI}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(report));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientCalendarNameListWrapper> allCalendars() {
        return calendar((ClientJobCalendar.Type )null);
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


    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService#scheduleReport(com.jaspersoft.jasperserver.dto.job.ClientReportJob)}.
     */
    public OperationResult<Job> scheduleReport(Job report) {
        JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{SERVICE_URI}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        return request.put(report);
    }
    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService#asyncScheduleReport(com.jaspersoft.jasperserver.dto.job.ClientReportJob, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncScheduleReport(final Job report, final Callback<OperationResult<Job>, R> callback) {
        final JerseyRequest<Job> request = buildRequest(sessionStorage, Job.class, new String[]{SERVICE_URI}, new JobValidationErrorHandler());
        if (sessionStorage.getConfiguration().getJrsVersion().compareTo(JRSVersion.v5_5_0) > 0) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/job+{mime}"));
        } else {
            request.setContentType("application/job+json");
            request.setAccept("application/job+json");
        }
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(report));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
    /**
     * @deprecated Replaced by {@link JobsService#allCalendars()}.
     */
    public OperationResult<CalendarNameListWrapper> calendars() {
        return calendars(null);
    }

    /**
     * @deprecated Replaced by {@link JobsService#asyncCalendar(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncCalendars(final Callback<OperationResult<CalendarNameListWrapper>, R> callback) {
        return asyncCalendars(null, callback);
    }
    /**
     * @deprecated Replaced by {@link JobsService#calendar(com.jaspersoft.jasperserver.dto.job.ClientJobCalendar.Type)}.
     */
    public OperationResult<CalendarNameListWrapper> calendars(CalendarType type) {
        JerseyRequest<CalendarNameListWrapper> request = buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{SERVICE_URI, CALENDARS});
        if (type != null) {
            request.addParam("calendarType", type.name().toLowerCase());
        }
        return request.get();
    }

    /**
     * @deprecated Replaced by {@link JobsService#asyncCalendar(com.jaspersoft.jasperserver.dto.job.ClientJobCalendar.Type, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}.
     */
    public <R> RequestExecution asyncCalendars(final CalendarType type, final Callback<OperationResult<CalendarNameListWrapper>, R> callback) {
        final JerseyRequest<CalendarNameListWrapper> request = buildRequest(sessionStorage, CalendarNameListWrapper.class, new String[]{SERVICE_URI, CALENDARS});
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
}
