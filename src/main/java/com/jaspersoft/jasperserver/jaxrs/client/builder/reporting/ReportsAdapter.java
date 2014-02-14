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

package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.reportparameters.ReorderingReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.reportparameters.ReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.reportparameters.ReportParametersUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class ReportsAdapter {

    private final SessionStorage sessionStorage;
    private final String reportUnitUri;

    public ReportsAdapter(SessionStorage sessionStorage, String reportUnitUri){
        this.sessionStorage = sessionStorage;
        this.reportUnitUri = reportUnitUri;
    }

    public ReorderingReportParametersAdapter reportParameters(){
        return new ReorderingReportParametersAdapter(sessionStorage, reportUnitUri);
    }

    public ReportParametersAdapter reportParameters(String mandatoryId, String... otherIds){
        List<String> ids = new ArrayList<String>(Arrays.asList(otherIds));
        ids.add(0, mandatoryId);
        return new ReportParametersAdapter(sessionStorage, reportUnitUri, ReportParametersUtils.toPathSegment(ids));
    }

    public RunReportAdapter prepareForRun(ReportOutputFormat format, Integer... pages){
        return new RunReportAdapter(sessionStorage, reportUnitUri, format, pages);
    }

    public class RunReportAdapter{

        private final MultivaluedMap<String, String> params;
        private final SessionStorage sessionStorage;
        private final String reportUnitUri;
        private final ReportOutputFormat format;
        private final Integer[] pages;

        public RunReportAdapter(SessionStorage sessionStorage, String reportUnitUri,
                                ReportOutputFormat format, Integer[] pages){

            this.params = new MultivaluedHashMap<String, String>();
            this.sessionStorage = sessionStorage;
            this.reportUnitUri = reportUnitUri;
            this.format = format;
            this.pages = pages;
        }

        public RunReportAdapter parameter(String name, String value){
            params.add(name, value);
            return this;
        }

        public OperationResult<InputStream> run(){
            JerseyRequestBuilder<InputStream> builder =
                    buildRequest(sessionStorage, InputStream.class,
                            new String[]{"/reports", reportUnitUri + "." + format.toString().toLowerCase()});
            builder.addParams(params);

            if (pages.length == 1)
                builder.addParam("page", pages[0].toString());
            if (pages.length > 1)
                builder.addParam("pages", toStringArray(pages));
            return builder.get();
        }

        private String[] toStringArray(Integer[] ints){
            String[] strings = new String[ints.length];
            for (int i = 0; i < ints.length; i++)
                strings[i] = ints[i].toString();
            return strings;
        }

    }

}
