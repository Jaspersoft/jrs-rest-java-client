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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

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
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class RunReportAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "reports";
    private final MultivaluedMap<String, String> params;
    private final String reportUnitUri;
    private final String format;
    private TimeZone timeZone;
    private String[] pages = new String[0];
    private final ArrayList<String> path = new ArrayList<>();

    public RunReportAdapter(SessionStorage sessionStorage, String reportUnitUri, String format) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<>();
        this.reportUnitUri = reportUnitUri;
        this.format = format.toLowerCase();
    }

    public RunReportAdapter(SessionStorage sessionStorage, String reportUnitUri, String format,
                            Integer[] pages) {
        this(sessionStorage, reportUnitUri, format);
        if (pages != null && !(pages.length == 1 && pages[0] == 0)) {
            this.pages = toStringArray(validArray(pages));
        }
    }

    private Integer[] validArray(Integer[] pages) {
        List<Integer> list = new LinkedList<>(Arrays.asList(pages));
        list.removeIf(next -> next <= 0);
        return list.toArray(new Integer[0]);
    }

    public RunReportAdapter(SessionStorage sessionStorage, String reportUnitUri, String format,
                            com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange range) {
        this(sessionStorage, reportUnitUri, format);
        this.pages = new String[]{range.getRange()};
    }

    public RunReportAdapter forTimeZone(TimeZone timeZone) {
        if (timeZone != null) {
            this.timeZone = timeZone;
        }
        return this;
    }


    public RunReportAdapter forTimeZone(String timeZoneId) {
        return this.forTimeZone(TimeZone.getTimeZone(timeZoneId));
    }

    public RunReportAdapter parameter(String name, String... value) {
        params.addAll(name, UrlUtils.encode(Arrays.asList(value)));
        return this;
    }

    public RunReportAdapter parameter(String name, List<String> values) {

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
        path.addAll(Arrays.asList((reportUnitUri + "." + format).split("/")));
        JerseyRequest<InputStream> request = JerseyRequest.buildRequest(
                sessionStorage,
                InputStream.class,
                path.toArray(new String[0]),
                new RunReportErrorHandler());

        request.addParams(params);

        if (pages.length > 0) {
            if (pages.length == 1) {
                Pattern pattern = compile("^(\\d+)-(\\d+)$");
                Matcher matcher = pattern.matcher(pages[0]);
                request.addParam(matcher.matches() ? "pages" : "page", pages[0]);
            }
            if (pages.length > 1) {
                request.addParam("pages", pages);
            }
        }
        if (timeZone != null) {
            request.addHeader("Accept-Timezone", timeZone.getID());
        }
        return request;
    }

    private String[] toStringArray(Integer[] ints) {
        String[] strings = new String[ints.length];
        for (int i = 0; i < ints.length; i++) {
            strings[i] = ints[i].toString();
        }
        return strings;
    }
}