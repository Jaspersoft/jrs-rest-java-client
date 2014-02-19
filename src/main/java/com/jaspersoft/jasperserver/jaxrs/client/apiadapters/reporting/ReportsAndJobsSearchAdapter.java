package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;


public class ReportsAndJobsSearchAdapter extends AbstractAdapter {

    private final MultivaluedMap<String, String> params;

    public ReportsAndJobsSearchAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<String, String>();
    }

    public ReportsAndJobsSearchAdapter parameter(ReportAndJobSearchParameter param, String value) {
        params.add(param.getName(), value);
        return this;
    }

    public OperationResult<ReportExecutionListWrapper> find(){
        JerseyRequestBuilder<ReportExecutionListWrapper> builder =
                buildRequest(sessionStorage, ReportExecutionListWrapper.class, new String[]{"/reportExecutions"});
        builder.addParams(params);
        return builder.get();
    }

}
