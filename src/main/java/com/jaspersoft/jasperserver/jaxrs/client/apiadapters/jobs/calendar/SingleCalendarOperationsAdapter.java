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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.WithEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.ReportJobCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.calendars.*;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class SingleCalendarOperationsAdapter extends AbstractAdapter {

    private final String calendarName;
    private final MultivaluedMap<String, String> params;

    public SingleCalendarOperationsAdapter(SessionStorage sessionStorage, String calendarName) {
        super(sessionStorage);
        this.calendarName = calendarName;
        params = new MultivaluedHashMap<String, String>();
    }

    public SingleCalendarOperationsAdapter parameter(CalendarParameter parameter, String value) {
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<Calendar> get() {
        OperationResult<ReportJobCalendar> result =
                buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{"/jobs", "/calendars", calendarName})
                        .get();
        return convertToLocalCalendarType(result);
    }

    private OperationResult<Calendar> convertToLocalCalendarType(OperationResult<ReportJobCalendar> source) {
        ReportJobCalendar reportJobCalendar = source.getEntity();
        CalendarType calendarType = CalendarType.valueOf(reportJobCalendar.getCalendarType());

        Calendar localCalendar = null;

        switch (calendarType) {
            case annual: {
                AnnualCalendar annualCalendar = new AnnualCalendar();
                setCommonCalendarFields(annualCalendar, reportJobCalendar);
                annualCalendar.setDataSorted(reportJobCalendar.isDataSorted());
                annualCalendar.setExcludeDays(reportJobCalendar.getExcludeDays());
                localCalendar = annualCalendar;
                break;
            }
            case base: {
                BaseCalendar baseCalendar = new BaseCalendar();
                setCommonCalendarFields(baseCalendar, reportJobCalendar);
                localCalendar = baseCalendar;
                break;
            }
            case cron: {
                CronCalendar cronCalendar = new CronCalendar();
                setCommonCalendarFields(cronCalendar, reportJobCalendar);
                cronCalendar.setCronExpression(reportJobCalendar.getCronExpression());
                localCalendar = cronCalendar;
                break;
            }
            case daily: {
                DailyCalendar dailyCalendar = new DailyCalendar();
                setCommonCalendarFields(dailyCalendar, reportJobCalendar);
                dailyCalendar.setInvertTimeRange(reportJobCalendar.isInvertTimeRange());
                dailyCalendar.setRangeEndingCalendar(reportJobCalendar.getRangeEndingCalendar());
                dailyCalendar.setRangeStartingCalendar(reportJobCalendar.getRangeStartingCalendar());
                localCalendar = dailyCalendar;
                break;
            }
            case holiday: {
                HolidayCalendar holidayCalendar = new HolidayCalendar();
                setCommonCalendarFields(holidayCalendar, reportJobCalendar);
                holidayCalendar.setDataSorted(reportJobCalendar.isDataSorted());
                holidayCalendar.setExcludeDays(reportJobCalendar.getExcludeDays());
                localCalendar = holidayCalendar;
                break;
            }
            case monthly: {
                MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
                setCommonCalendarFields(monthlyCalendar, reportJobCalendar);
                monthlyCalendar.setExcludeDaysFlags(reportJobCalendar.getExcludeDaysFlags());
                localCalendar = monthlyCalendar;
                break;
            }
            case weekly: {
                WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
                setCommonCalendarFields(weeklyCalendar, reportJobCalendar);
                weeklyCalendar.setExcludeDaysFlags(reportJobCalendar.getExcludeDaysFlags());
                localCalendar = weeklyCalendar;
                break;
            }
        }

        final Calendar finalLocalCalendar = localCalendar;
        return new WithEntityOperationResult<Calendar>(source.getResponse(), Calendar.class) {{
                    this.entity = finalLocalCalendar;
                }};
    }

    private void setCommonCalendarFields(Calendar target, ReportJobCalendar src) {
        target.setCalendarType(CalendarType.valueOf(src.getCalendarType()));
        target.setDescription(src.getDescription());
        target.setTimeZone(src.getTimeZone());
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<Calendar>, R> callback) {
        final JerseyRequest<ReportJobCalendar> request =
                buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{"/jobs", "/calendars", calendarName});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(convertToLocalCalendarType(request.get()));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        return buildRequest(sessionStorage, Object.class, new String[]{"/jobs", "/calendars", calendarName})
                .delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request =
                buildRequest(sessionStorage, Object.class, new String[]{"/jobs", "/calendars", calendarName});

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ReportJobCalendar> createNew(Calendar calendarDescriptor) {
        JerseyRequest<ReportJobCalendar> request =
                buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{"/jobs", "/calendars", calendarName});
        request.addParams(params);

        return request.put(calendarDescriptor);
    }

    public <R> RequestExecution asyncCreateNew(final Calendar calendarDescriptor, final Callback<OperationResult<ReportJobCalendar>, R> callback) {
        final JerseyRequest<ReportJobCalendar> request =
                buildRequest(sessionStorage, ReportJobCalendar.class, new String[]{"/jobs", "/calendars", calendarName});
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
