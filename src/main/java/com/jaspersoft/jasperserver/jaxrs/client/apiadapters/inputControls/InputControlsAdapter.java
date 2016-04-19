package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.List;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsAdapter extends AbstractAdapter{

    public static final String REPORTS_URI = "reports";
    public static final String INPUT_CONTROLS_URI = "inputControls";
    private String containerUri;
    private Boolean excludeState = false;

    public InputControlsAdapter container(String uri) {
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

    private JerseyRequest<ReportInputControlsListWrapper> buildRequest(){
        if (containerUri == null) {
            throw new MandatoryParameterNotFoundException("Uri of container should be specified");
        }
        JerseyRequest<ReportInputControlsListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                ReportInputControlsListWrapper.class,
                new String[]{REPORTS_URI, containerUri, INPUT_CONTROLS_URI},
                new DefaultErrorHandler());
        if (excludeState) {
            request.addParam("exclude", "state");
        }
        return request;
    }

}
