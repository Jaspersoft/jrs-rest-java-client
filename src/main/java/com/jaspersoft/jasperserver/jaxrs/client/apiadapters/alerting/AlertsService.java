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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendar;
import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientCalendarNameListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class AlertsService extends AbstractAdapter {

    public static final String SERVICE_URI = "alerts";
    public static final String CALENDARS = "calendars";

    public AlertsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchAlertsOperationsAdapter alerts() {
        return new BatchAlertsOperationsAdapter(sessionStorage);
    }

    public BatchAlertsOperationsAdapter alerts(Long... ids) {
        return new BatchAlertsOperationsAdapter(sessionStorage, ids);
    }

    public SingleAlertOperationsAdapter alert(long id) {
        return new SingleAlertOperationsAdapter(sessionStorage, String.valueOf(id));
    }

    public SingleAlertOperationsAdapter alert(ClientReportAlert reportAlert) {
        return new SingleAlertOperationsAdapter(sessionStorage, reportAlert);
    }

    public OperationResult<ClientCalendarNameListWrapper> allCalendars() {
        return calendar((ClientAlertCalendar.Type) null);
    }

    public <R> RequestExecution asyncCalendar(final Callback<OperationResult<ClientCalendarNameListWrapper>, R> callback) {
        return asyncCalendar(null, callback);
    }

    public OperationResult<ClientCalendarNameListWrapper> calendar(ClientAlertCalendar.Type type) {
        JerseyRequest<ClientCalendarNameListWrapper> request = buildRequest(sessionStorage, ClientCalendarNameListWrapper.class, new String[]{SERVICE_URI, CALENDARS});
        if (type != null) {
            request.addParam("calendarType", type.name().toLowerCase());
        }
        return request.get();
    }

    public <R> RequestExecution asyncCalendar(final ClientAlertCalendar.Type type, final Callback<OperationResult<ClientCalendarNameListWrapper>, R> callback) {
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
