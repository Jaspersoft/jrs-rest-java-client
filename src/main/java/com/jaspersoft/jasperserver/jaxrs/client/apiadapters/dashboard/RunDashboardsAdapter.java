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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.RunReportErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunDashboardsAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "dashboards";
    private final MultivaluedMap<String, String> params;
    private final String dashboardUri;
    private final String format;
    private final ArrayList<String> path = new ArrayList<>();

    public RunDashboardsAdapter(SessionStorage sessionStorage, String dashboardUri, String format) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<>();
        this.dashboardUri = dashboardUri;
        this.format = format.toLowerCase();
    }

    public RunDashboardsAdapter parameter(String name, String... value) {
        params.addAll(name, UrlUtils.encode(Arrays.asList(value)));
        return this;
    }

    public RunDashboardsAdapter parameter(String name, List<String> values) {
        params.addAll(name, UrlUtils.encode(values));
        return this;

    }

    public OperationResult<InputStream> run() {
        JerseyRequest<InputStream> request = prepareRunRequest();
        return request.get();
    }

    public <R> RequestExecution asyncRun(final Callback<OperationResult<InputStream>, R> callback) {
        final JerseyRequest<InputStream> request = prepareRunRequest();
        RequestExecution task = new RequestExecution(() -> callback.execute(request.get()));
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<InputStream> prepareRunRequest() {
        path.add(SERVICE_URI);
        path.addAll(Arrays.asList((dashboardUri + "." + format).split("/")));
        JerseyRequest<InputStream> request = JerseyRequest.buildRequest(
                sessionStorage,
                InputStream.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        request.addParams(params);
        return request;
    }

}