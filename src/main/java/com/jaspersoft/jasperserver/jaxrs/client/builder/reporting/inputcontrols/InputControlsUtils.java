package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.inputcontrols;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.ReportParameters;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class InputControlsUtils {

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
        StringBuilder builder = new StringBuilder();
        for (String id : inputControlsIds)
            builder.append(id)
                    .append(";");

        return builder.toString();
    }

}
