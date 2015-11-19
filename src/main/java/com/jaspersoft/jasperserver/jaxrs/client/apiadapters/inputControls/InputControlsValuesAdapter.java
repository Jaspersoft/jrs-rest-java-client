package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.MultivaluedHashMap;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsValuesAdapter extends AbstractAdapter{

    private String containerUri;
    private Boolean useFreshData = false;
    private MultivaluedHashMap<String, String> inputControlsValues = new MultivaluedHashMap<String, String>();
    private StringBuilder ids = new StringBuilder("");

    public InputControlsValuesAdapter(SessionStorage sessionStorage, String containerUri) {
        super(sessionStorage);
        this.containerUri = containerUri;
    }

    public InputControlsValuesAdapter useCashedData(Boolean value) {
        useFreshData = !value;
        return this;
    }

    public InputControlsValuesAdapter parameter(String name, String value) {
        this.inputControlsValues.add(name, value);
        return this;
    }

    public InputControlsValuesAdapter parameter(String name, String... values) {
        this.inputControlsValues.addAll(name, values);
        return this;
    }

    public OperationResult<InputControlStateListWrapper> run() {
        if (inputControlsValues.size() == 0) {
            throw new MandatoryParameterNotFoundException();
        }
        ids.append("/");
        Set<String> keySet = inputControlsValues.keySet();
        String[] idsArray = keySet.toArray(new String[keySet.size()]);
        ids.append(StringUtils.join(idsArray, ";"));
        return buildRequest().post(valuesToArrays());
    }

    public OperationResult<InputControlStateListWrapper> get(){
        return buildRequest().get();
    }

    private JerseyRequest<InputControlStateListWrapper> buildRequest(){

        JerseyRequest<InputControlStateListWrapper> request = JerseyRequest.buildRequest(sessionStorage,
                InputControlStateListWrapper.class,
                new String[]{"/reports", containerUri, "/inputControls",ids.toString(), "/values"},
                new DefaultErrorHandler());
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
