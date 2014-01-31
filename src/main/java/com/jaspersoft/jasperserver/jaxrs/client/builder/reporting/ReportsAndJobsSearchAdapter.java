package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourceServiceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;


public class ReportsAndJobsSearchAdapter {

    private final SessionStorage sessionStorage;
    private final MultivaluedMap<String, String> params;

    public ReportsAndJobsSearchAdapter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        this.params = new MultivaluedHashMap<String, String>();
    }

    public ReportsAndJobsSearchAdapter parameter(ReportAndJobSearchParameter param, String value) {
        params.add(param.getName(), value);
        return this;
    }

    public OperationResult<ReportExecutionListWrapper> find(){
        JerseyRequestBuilder<ReportExecutionListWrapper> builder =
                new JerseyRequestBuilder<ReportExecutionListWrapper>(sessionStorage, ReportExecutionListWrapper.class);
        builder.setPath("reportExecutions");
        builder.addParams(params);

        return builder.get();
    }

}
