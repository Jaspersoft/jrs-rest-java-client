package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.inputcontrols;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class InputControlsAdapter {

    protected final SessionStorage sessionStorage;
    private final String reportUnitUri;
    protected MultivaluedMap<String, String> params;
    private String idsPathSegment;

    public InputControlsAdapter(SessionStorage sessionStorage, String reportUnitUri){
        params = new MultivaluedHashMap<String, String>();
        this.sessionStorage = sessionStorage;
        this.reportUnitUri = reportUnitUri;
    }

    public InputControlsAdapter(SessionStorage sessionStorage, String reportUnitUri, String idsPathSegment) {
        this(sessionStorage, reportUnitUri);
        this.idsPathSegment = idsPathSegment;
    }

    public InputControlsAdapter parameter(String name, String value){
        params.putSingle(name, value);
        return this;
    }

    public OperationResult<ReportInputControlsListWrapper> get(){
        JerseyRequestBuilder<ReportInputControlsListWrapper> builder =
                new JerseyRequestBuilder<ReportInputControlsListWrapper>(
                        sessionStorage, ReportInputControlsListWrapper.class);
        builder
                .setPath("reports")
                .setPath(reportUnitUri)
                .setPath("inputControls");
        if (idsPathSegment != null){
            builder.setPath(idsPathSegment);
        }
        builder.addParams(params);
        return builder.get();
    }
    public OperationResult<ReportInputControlsListWrapper> secureGet(){
        JerseyRequestBuilder<ReportInputControlsListWrapper> builder =
                new JerseyRequestBuilder<ReportInputControlsListWrapper>(
                        sessionStorage, ReportInputControlsListWrapper.class);
        builder
                .setPath("reports")
                .setPath(reportUnitUri)
                .setPath("inputControls");
        if (idsPathSegment != null){
            builder.setPath(idsPathSegment);
        }
        builder.setContentType(MediaType.APPLICATION_XML);
        return builder.post(InputControlsUtils.toReportParameters(params));
    }

    public InputControlValuesAdapter values(){
        return new InputControlValuesAdapter(sessionStorage, reportUnitUri, idsPathSegment);
    }

}
