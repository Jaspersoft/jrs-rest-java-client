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

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls.InputControlsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions.ReportOptionsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions.ReportOptionsUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.List;

public class ReportsAdapter extends AbstractAdapter {

    private final String reportUnitUri;

    public ReportsAdapter(SessionStorage sessionStorage, String reportUnitUri) {
        super(sessionStorage);
        this.reportUnitUri = reportUnitUri;
    }

    public ReportOptionsAdapter reportOptions() {
        return new ReportOptionsAdapter(sessionStorage, reportUnitUri);
    }

    public ReportOptionsAdapter reportOptions(String optionsId) {
        return new ReportOptionsAdapter(sessionStorage, reportUnitUri, optionsId);
    }

    public ReportOptionsAdapter reportOptions(MultivaluedHashMap options) {
        return new ReportOptionsAdapter(sessionStorage, reportUnitUri, options);
    }

    public ReportOptionsAdapter reportOptions(List<ReportParameter> options) {
        return new ReportOptionsAdapter(sessionStorage, reportUnitUri, ReportOptionsUtil.toMap(options));
    }

    public InputControlsAdapter inputControls() {
        return new InputControlsAdapter(sessionStorage, reportUnitUri);
    }

    public RunReportAdapter prepareForRun(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat format, Integer... pages) {
        return new RunReportAdapter(sessionStorage, reportUnitUri, format.toString().toLowerCase(), pages);
    }

    public RunReportAdapter prepareForRun(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat format, com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange range) {
        return new RunReportAdapter(sessionStorage, reportUnitUri, format.toString().toLowerCase(), range);
    }

    public RunReportAdapter prepareForRun(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.ReportOutputFormat format) {
        return new RunReportAdapter(sessionStorage, reportUnitUri, format.toString().toLowerCase());
    }

    public RunReportAdapter prepareForRun(String format, Integer... pages) {
        return new RunReportAdapter(sessionStorage, reportUnitUri, format, pages);
    }

    public RunReportAdapter prepareForRun(String format, com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.util.PageRange range) {
        return new RunReportAdapter(sessionStorage, reportUnitUri, format, range);
    }

    public RunReportAdapter prepareForRun(String format) {
        return new RunReportAdapter(sessionStorage, reportUnitUri, format);
    }


}
