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

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

/**
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls.InputControlsValuesAdapter}.
 */
public class ReportParametersUtils {

    public static ReportParameters toReportParameters(MultivaluedMap<String, String> params){

        List<ReportParameter> parameters = new ArrayList<ReportParameter>();

        for (MultivaluedMap.Entry<String, List<String>> entry : params.entrySet()){
            ReportParameter parameter = new ReportParameter();
            parameter.setName(entry.getKey());
            parameter.setValues(entry.getValue());
            parameters.add(parameter);
        }

        return new ReportParameters(parameters);
    }

    public static String toPathSegment(List<String> inputControlsIds){
        StringBuilder request = new StringBuilder();
        for (String id : inputControlsIds)
            request.append(id)
                    .append(";");

        return request.toString();
    }

}
