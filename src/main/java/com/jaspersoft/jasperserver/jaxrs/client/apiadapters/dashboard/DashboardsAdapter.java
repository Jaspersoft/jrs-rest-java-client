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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.dashboard;

import com.jaspersoft.jasperserver.dto.dashboard.DashboardExportExecution;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

public class DashboardsAdapter extends AbstractAdapter {

    private final String dashboardUri;

    public DashboardsAdapter(SessionStorage sessionStorage, String dashboardUri) {
        super(sessionStorage);
        this.dashboardUri = dashboardUri;
    }

    public RunDashboardsAdapter prepareForRun(DashboardExportExecution.ExportFormat format) {
        return new RunDashboardsAdapter(sessionStorage, dashboardUri, format.toString());
    }

    public RunDashboardsAdapter prepareForRun(String format) {
        return new RunDashboardsAdapter(sessionStorage, dashboardUri, format);
    }

}
