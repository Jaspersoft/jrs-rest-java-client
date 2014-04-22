package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlOption;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportParametersConverter {

    public static Map<String, Object> getValueMapFromInputControlStates(List<InputControlState> states) {
        Map<String, Object> valueMap = new HashMap<String, Object>(states.size());
        for (InputControlState state : states) {
            if (state != null)
                valueMap.put(state.getId(), getValueFromInputControlState(state));
        }

        return valueMap;
    }

    protected static String[] getValueFromInputControlState(InputControlState state) {
        if (state.getValue() != null) {
            return new String[]{state.getValue()};
        } else if (state.getOptions() != null) {
            List<String> values = new ArrayList<String>(state.getOptions().size());
            for (InputControlOption option : state.getOptions()) {
                if (option.isSelected()) {
                    values.add(option.getValue());
                }
            }
            return values.toArray(new String[values.size()]);
        } else {
            return new String[0];
        }
    }

}
