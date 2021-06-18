package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tetyana Matveyeva
 */
public class InputControlsPaginationValuesAdapter extends AbstractAdapter {

    public static final String REPORTS_URI = "reports";
    public static final String INPUT_CONTROLS_URI = "inputControls";
    public static final String VALUES_URI = "values";
    public static final String PAGINATION_URI = "pagination";
    private String containerUri;
    private Boolean useFreshData = false;
    private List<String> path = new ArrayList<>();
    private String inputControlNames;


    public InputControlsPaginationValuesAdapter(SessionStorage sessionStorage, String containerUri, String inputControlNames) {
        super(sessionStorage);
        if (containerUri == null) {
            throw new MandatoryParameterNotFoundException("Uri of container should be specified");
        }
        this.containerUri = containerUri;
        this.inputControlNames = inputControlNames;
    }

    public InputControlsPaginationValuesAdapter useCachedData(Boolean value) {
        useFreshData = !value;
        return this;
    }

    public OperationResult<InputControlStateListWrapper> get(ReportParameters reportParameters) {
        return buildRequest().post(reportParameters);
    }

    private JerseyRequest<InputControlStateListWrapper> buildRequest() {
        path.add(REPORTS_URI);
        path.addAll(Arrays.asList(containerUri.split("/")));
        path.add(INPUT_CONTROLS_URI);
        if (!inputControlNames.isEmpty()) {
            path.add(inputControlNames);
        }
        path.add(VALUES_URI);
        path.add(PAGINATION_URI);
        JerseyRequest<InputControlStateListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                InputControlStateListWrapper.class,
                path.toArray(new String[0]));
        if (useFreshData) {
            request.addParam("freshData", useFreshData.toString());
        }
        return request;
    }


}
