package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportoptions;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class ReportOptionsUtil {

    public static MultivaluedHashMap<String, String> toMap(List<ReportParameter> reportParameters) {

        final MultivaluedHashMap<String, String> reportOptions = new MultivaluedHashMap<>();

        for (ReportParameter reportParameter : reportParameters) {
            reportOptions.addAll(reportParameter.getName(), reportParameter.getValues());
        }
        return reportOptions;
    }

    public static List<ReportParameter> toReportParameters(MultivaluedMap<String, String> params){

        List<ReportParameter> parameters = new ArrayList<ReportParameter>();

        for (MultivaluedMap.Entry<String, List<String>> entry : params.entrySet()){
            ReportParameter parameter = new ReportParameter();
            parameter.setName(entry.getKey());
            parameter.setValues(entry.getValue());
            parameters.add(parameter);
        }
        return parameters;
    }
}
