package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.SelectedValuesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Tetyana Matveyeva
 */
public class InputControlsSelectedValuesAdapter extends AbstractAdapter {

    private static final String REPORTS_URI = "reports";
    private static final String INPUT_CONTROLS_URI = "inputControls";
    private static final String SELECTED_VALUES_URI = "selectedValues";
    private String containerUri;
    private Boolean useFreshData = false;
    private Boolean showLabel = null;
    private ArrayList<String> path = new ArrayList<>();


    public InputControlsSelectedValuesAdapter(SessionStorage sessionStorage, String containerUri) {
        super(sessionStorage);
        if (containerUri == null) {
            throw new MandatoryParameterNotFoundException("Uri of container should be specified");
        }
        this.containerUri = containerUri;
    }

    public InputControlsSelectedValuesAdapter useCachedData(Boolean value) {
        useFreshData = !value;
        return this;
    }

    public InputControlsSelectedValuesAdapter withLabel(Boolean value) {
        showLabel = value;
        return this;
    }

    public OperationResult<SelectedValuesListWrapper> get() {
        return buildRequest().get();
    }

    private JerseyRequest<SelectedValuesListWrapper> buildRequest() {
        path.add(REPORTS_URI);
        path.addAll(Arrays.asList(containerUri.split("/")));
        path.add(INPUT_CONTROLS_URI);
        path.add(SELECTED_VALUES_URI);
        JerseyRequest<SelectedValuesListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                SelectedValuesListWrapper.class,
                path.toArray(new String[0]));
        if (useFreshData) {
            request.addParam("freshData", useFreshData.toString());
        }
        if (showLabel != null) {
            request.addParam("withLabel", showLabel.toString());
        }
        return request;
    }


}
