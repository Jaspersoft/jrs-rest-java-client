package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class RunReportAdapter extends AbstractAdapter {

    private final MultivaluedMap<String, String> params;
    private final String reportUnitUri;
    private final ReportOutputFormat format;
    private final Integer[] pages;

    public RunReportAdapter(SessionStorage sessionStorage, String reportUnitUri,
                            ReportOutputFormat format, Integer[] pages){

        super(sessionStorage);
        this.params = new MultivaluedHashMap<String, String>();
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