package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;
import org.apache.commons.lang3.StringUtils;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsValuesAdapter extends AbstractAdapter {

    public static final String REPORTS_URI = "reports";
    public static final String INPUT_CONTROLS_URI = "inputControls";
    public static final String VALUES_URI = "values";
    private String containerUri;
    private Boolean useFreshData = false;
    private Boolean includeFullStructure = false;
    private MultivaluedHashMap<String, String> inputControlsValues = new MultivaluedHashMap<>();
    private StringBuilder ids = new StringBuilder("");
    private ArrayList<String> path = new ArrayList<>();


    public InputControlsValuesAdapter(SessionStorage sessionStorage, String containerUri) {
        super(sessionStorage);
        if (containerUri == null) {
            throw new MandatoryParameterNotFoundException("Uri of container should be specified");
        }
        this.containerUri = containerUri;
    }

    public InputControlsValuesAdapter useCachedData(Boolean value) {
        useFreshData = !value;
        return this;
    }

    public InputControlsValuesAdapter includeFullStructure(Boolean value) {
        includeFullStructure = value;
        return this;
    }

    public InputControlsValuesAdapter forInputControl(String name) {
        this.inputControlsValues.add(name, null);
        return this;
    }

    public InputControlsValuesAdapter forInputControl(String name, String value) {
        this.inputControlsValues.add(name, value);
        return this;
    }

    public InputControlsValuesAdapter forInputControl(String name, String... values) {
        this.inputControlsValues.addAll(name, values);
        return this;
    }

    public InputControlsPaginationValuesAdapter pagination() {
        ids.append(StringUtils.join(inputControlsValues.keySet(), ";"));
        return new InputControlsPaginationValuesAdapter(sessionStorage, containerUri, ids.toString());
    }

    public OperationResult<InputControlStateListWrapper> update(MultivaluedHashMap<String, String> inputControlsValues) {
        this.inputControlsValues = inputControlsValues;
        return this.update();
    }

    public OperationResult<ReportInputControlsListWrapper> updateAndReorder(MultivaluedHashMap<String, String> inputControlsValues) {
        this.inputControlsValues = inputControlsValues;
        path.add(REPORTS_URI);
        path.addAll(Arrays.asList(containerUri.split("/")));
        path.add(INPUT_CONTROLS_URI);
        JerseyRequest<ReportInputControlsListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                ReportInputControlsListWrapper.class,
                path.toArray(new String[0]));
        return request.post(inputControlsValues);
    }

    public OperationResult<InputControlStateListWrapper> update() {
        if (inputControlsValues.size() == 0) {
            throw new MandatoryParameterNotFoundException();
        }
        if (!includeFullStructure) {
            ids.append(StringUtils.join(inputControlsValues.keySet(), ";"));
        }
        return buildRequest().post(inputControlsValues);
    }

    public OperationResult<InputControlStateListWrapper> get() {
        return buildRequest().get();
    }

    private JerseyRequest<InputControlStateListWrapper> buildRequest() {
        path.add(REPORTS_URI);
        path.addAll(Arrays.asList(containerUri.split("/")));
        path.add(INPUT_CONTROLS_URI);
        if (!ids.toString().isEmpty()) {
            path.add(ids.toString());
        }
        path.add(VALUES_URI);
        JerseyRequest<InputControlStateListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                InputControlStateListWrapper.class,
                path.toArray(new String[0]));
        if (useFreshData) {
            request.addParam("freshData", useFreshData.toString());
        }
        return request;
    }

    private Map<String, String[]> valuesToArrays() {
        Map<String, String[]> result = new LinkedHashMap<String, String[]>();
        List<String> listValues;
        String[] arrayValues;
        for (String key : inputControlsValues.keySet()) {
            listValues = inputControlsValues.get(key);
            arrayValues = new String[listValues.size()];
            result.put(key, listValues.toArray(arrayValues));
        }
        return result;
    }

}
