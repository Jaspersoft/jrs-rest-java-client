package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsAdapter extends AbstractAdapter{

    public static final String REPORTS_URI = "reports";
    public static final String INPUT_CONTROLS_URI = "inputControls";
    public ArrayList<String> path = new ArrayList<String>();
    private String containerUri;
    private Boolean excludeState = false;

    public InputControlsAdapter(SessionStorage sessionStorage, String reportUnitUri) {
        super(sessionStorage);
        this.container(reportUnitUri);
    }
@Deprecated
    public InputControlsAdapter container(String uri) {
        if (uri == null) {
            throw new MandatoryParameterNotFoundException("Uri of container should be specified");
        }
        this.containerUri = uri;
        return  this;
    }

    public InputControlsAdapter forReport(String uri) {
        if (uri == null) {
            throw new MandatoryParameterNotFoundException("Uri of container should be specified");
        }
        this.containerUri = uri;
        return  this;
    }

    public InputControlsAdapter excludeState(Boolean value) {
        this.excludeState = value;
        return this;
    }

    public InputControlsAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public InputControlsValuesAdapter values() {
        return new InputControlsValuesAdapter(sessionStorage, containerUri);
    }

    public OperationResult<ReportInputControlsListWrapper> get(){
        return buildRequest().get();
    }

    public OperationResult<ReportInputControlsListWrapper> reorder(List<ReportInputControl> inputControls){
        ReportInputControlsListWrapper wrapper = new ReportInputControlsListWrapper(inputControls);
        return buildRequest().put(wrapper);
    }

    public OperationResult<ReportInputControlsListWrapper> reorder(ReportInputControlsListWrapper inputControls){
        return buildRequest().put(inputControls);
    }

    private JerseyRequest<ReportInputControlsListWrapper> buildRequest(){
        path.add(REPORTS_URI);
        path.addAll(Arrays.asList(containerUri.split("/")));
        path.add(INPUT_CONTROLS_URI);
        JerseyRequest<ReportInputControlsListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                ReportInputControlsListWrapper.class,
                path.toArray(new String[path.size()]));
        if (excludeState) {
            request.addParam("exclude", "state");
        }
        return request;
    }

}
