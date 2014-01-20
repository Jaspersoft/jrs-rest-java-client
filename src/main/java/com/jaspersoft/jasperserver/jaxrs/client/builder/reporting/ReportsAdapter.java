package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.inputcontrols.InputControlsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.inputcontrols.InputControlsUtils;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.inputcontrols.ReorderingInputControlsAdapter;

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

    public ReorderingInputControlsAdapter inputControls(){
        return new ReorderingInputControlsAdapter(sessionStorage, reportUnitUri);
    }

    public InputControlsAdapter inputControls(String mandatoryId, String... otherIds){
        List<String> ids = new ArrayList<String>(Arrays.asList(otherIds));
        ids.add(0, mandatoryId);
        return new InputControlsAdapter(sessionStorage, reportUnitUri, InputControlsUtils.toPathSegment(ids));
    }

}
