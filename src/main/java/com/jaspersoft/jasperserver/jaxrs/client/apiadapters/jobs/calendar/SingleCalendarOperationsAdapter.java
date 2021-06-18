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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar;

import com.jaspersoft.jasperserver.dto.job.ClientJobCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.WithEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.ReportJobCalendar;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleCalendarOperationsAdapter extends AbstractAdapter {

    public static final String JOBS = "jobs";
    public static final String CALENDARS = "calendars";
    private final String calendarName;
    private final MultivaluedMap<String, String> params;

    public SingleCalendarOperationsAdapter(SessionStorage sessionStorage, String calendarName) {
        super(sessionStorage);
        this.calendarName = calendarName;
        params = new MultivaluedHashMap<String, String>();
    }

    public SingleCalendarOperationsAdapter parameter(CalendarParameter parameter, String value) {
        params.add(parameter.getName(), UrlUtils.encode(value));
        return this;
    }

    public OperationResult<ClientJobCalendar> getCalendar() {
        OperationResult<ClientJobCalendar> result = buildRequest(sessionStorage, ClientJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName}).get();
        return result;
    }

    public <R> RequestExecution asyncGetCalendar(final Callback<OperationResult<ClientJobCalendar>, R> callback) {
        final JerseyRequest<ClientJobCalendar> request = buildRequest(sessionStorage, ClientJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
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

    public OperationResult<ClientJobCalendar> createNewCalendar(ClientJobCalendar calendarDescriptor) {
        JerseyRequest<ClientJobCalendar> request = buildRequest(sessionStorage, ClientJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
        request.addParams(params);
        return request.put(calendarDescriptor);
    }

    public <R> RequestExecution asyncCreateNewCalendar(final ClientJobCalendar calendarDescriptor, final Callback<OperationResult<ClientJobCalendar>, R> callback) {
        final JerseyRequest<ClientJobCalendar> request = buildRequest(sessionStorage, ClientJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
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

    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter#getCalendar()} .
     */
    public OperationResult<com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar> get() {
        OperationResult<ReportJobCalendar> result = buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName}).get();
        return convertToLocalCalendarType(result);
    }

    /**
     * @deprecated Replaced by {@link SingleCalendarOperationsAdapter#asyncGetCalendar(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)} .
     */
    public <R> RequestExecution asyncGet(final Callback<OperationResult<com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar>, R> callback) {
        final JerseyRequest<ReportJobCalendar> request = buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(convertToLocalCalendarType(request.get()));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    /**
     * @deprecated Replaced by {@link SingleCalendarOperationsAdapter#createNewCalendar(com.jaspersoft.jasperserver.dto.job.ClientJobCalendar)} .
     */
    public OperationResult<ReportJobCalendar> createNew(com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar calendarDescriptor) {
        JerseyRequest<ReportJobCalendar> request = buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
        request.addParams(params);
        return request.put(calendarDescriptor);
    }
    /**
     * @deprecated Replaced by {@link SingleCalendarOperationsAdapter#asyncCreateNewCalendar(com.jaspersoft.jasperserver.dto.job.ClientJobCalendar, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)} .
     */
    public <R> RequestExecution asyncCreateNew(final com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar calendarDescriptor, final Callback<OperationResult<ReportJobCalendar>, R> callback) {
        final JerseyRequest<ReportJobCalendar> request = buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{JOBS, CALENDARS, calendarName});
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
    /**
     * @deprecated Use server DTO.
     */
    private void setCommonCalendarFields(com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar target, ReportJobCalendar src) {
        target.setCalendarType(CalendarType.valueOf(src.getCalendarType()));
        target.setDescription(src.getDescription());
        target.setTimeZone(src.getTimeZone());
    }
    /**
     * @deprecated Use server DTO.
     */
    private OperationResult<com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar> convertToLocalCalendarType(OperationResult<ReportJobCalendar> source) {
            ReportJobCalendar reportJobCalendar = source.getEntity();
            CalendarType calendarType = CalendarType.valueOf(reportJobCalendar.getCalendarType());
            com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar localCalendar = null;
            switch (calendarType) {
                case annual: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.AnnualCalendar annualCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.AnnualCalendar();
                    setCommonCalendarFields(annualCalendar, reportJobCalendar);
                    annualCalendar.setDataSorted(reportJobCalendar.isDataSorted());
                    annualCalendar.setExcludeDays(reportJobCalendar.getExcludeDays());
                    localCalendar = annualCalendar;
                    break;
                }
                case base: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.BaseCalendar baseCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.BaseCalendar();
                    setCommonCalendarFields(baseCalendar, reportJobCalendar);
                    localCalendar = baseCalendar;
                    break;
                }
                case cron: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.CronCalendar cronCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.CronCalendar();
                    setCommonCalendarFields(cronCalendar, reportJobCalendar);
                    cronCalendar.setCronExpression(reportJobCalendar.getCronExpression());
                    localCalendar = cronCalendar;
                    break;
                }
                case daily: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.DailyCalendar dailyCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.DailyCalendar();
                    setCommonCalendarFields(dailyCalendar, reportJobCalendar);
                    dailyCalendar.setInvertTimeRange(reportJobCalendar.isInvertTimeRange());
                    dailyCalendar.setRangeEndingCalendar(reportJobCalendar.getRangeEndingCalendar());
                    dailyCalendar.setRangeStartingCalendar(reportJobCalendar.getRangeStartingCalendar());
                    localCalendar = dailyCalendar;
                    break;
                }
                case holiday: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.HolidayCalendar holidayCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.HolidayCalendar();
                    setCommonCalendarFields(holidayCalendar, reportJobCalendar);
                    holidayCalendar.setDataSorted(reportJobCalendar.isDataSorted());
                    holidayCalendar.setExcludeDays(reportJobCalendar.getExcludeDays());
                    localCalendar = holidayCalendar;
                    break;
                }
                case monthly: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.MonthlyCalendar monthlyCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.MonthlyCalendar();
                    setCommonCalendarFields(monthlyCalendar, reportJobCalendar);
                    monthlyCalendar.setExcludeDaysFlags(reportJobCalendar.getExcludeDaysFlags());
                    localCalendar = monthlyCalendar;
                    break;
                }
                case weekly: {
                    com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.WeeklyCalendar weeklyCalendar = new com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.WeeklyCalendar();
                    setCommonCalendarFields(weeklyCalendar, reportJobCalendar);
                    weeklyCalendar.setExcludeDaysFlags(reportJobCalendar.getExcludeDaysFlags());
                    localCalendar = weeklyCalendar;
                    break;
                }
            }
            final com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar finalLocalCalendar = localCalendar;
            return new WithEntityOperationResult<com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar>(source.getResponse(), com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.Calendar.class) {{
                this.entity = finalLocalCalendar;
            }};
        }


}
