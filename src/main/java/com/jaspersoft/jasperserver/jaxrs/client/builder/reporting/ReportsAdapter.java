package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.reportparameters.ReorderingReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.reportparameters.ReportParametersAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.reportparameters.ReportParametersUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        private final JerseyRequestBuilder<InputStream> builder;

        public RunReportAdapter(SessionStorage sessionStorage, String reportUnitUri,
                                ReportOutputFormat format, Integer[] pages){

            builder = new JerseyRequestBuilder<InputStream>(sessionStorage, InputStream.class);
            builder
                    .setPath("reports")
                    .setPath(reportUnitUri + "." + format.toString().toLowerCase());

            if (pages.length == 1)
                builder.addParam("page", pages[0].toString());
            if (pages.length > 1)
                builder.addParam("pages", toStringArray(pages));
        }

        public RunReportAdapter parameter(String name, String value){
            builder.addParam(name, value);
            return this;
        }

        public OperationResult<InputStream> run(){
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
