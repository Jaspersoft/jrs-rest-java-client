/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlOption;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;

import java.util.*;

/**
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls.InputControlsValuesAdapter}.
 */
public class ReportParametersConverter {

    public static Map<String, Object> getValueMapFromInputControlStates(List<InputControlState> states) {
        Map<String, Object> valueMap = new HashMap<String, Object>(states.size());
        for (InputControlState state : states) {
            if (state != null)
                valueMap.put(state.getId(), getValueFromInputControlState(state));
        }

        return valueMap;
    }

    public static ReportParameters toReportParameters(List<InputControlState> states){
        List<ReportParameter> reportParameterList = new ArrayList<ReportParameter>();
        for (InputControlState inputControlState : states) {
            ReportParameter parameter = new ReportParameter();
            parameter.setName(inputControlState.getId());
            parameter.setValues(Arrays.asList(getValueFromInputControlState(inputControlState)));

            reportParameterList.add(parameter);
        }
        return new ReportParameters(reportParameterList);
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
