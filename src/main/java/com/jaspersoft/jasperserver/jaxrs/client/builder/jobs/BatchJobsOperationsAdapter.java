package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobSummaryListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder.buildRequest;

public class BatchJobsOperationsAdapter {

    private final MultivaluedMap<String, String> params;
    private final SessionStorage sessionStorage;

    public BatchJobsOperationsAdapter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        params = new MultivaluedHashMap<String, String>();
    }

    public BatchJobsOperationsAdapter parameter(JobsParameter parameter, String value){
        params.add(parameter.getName(), value);
        return this;
    }

    public OperationResult<JobSummaryListWrapper> get(){
        return buildRequest(sessionStorage, JobSummaryListWrapper.class, new String[]{"/jobs"})
                .get();
    }

    public OperationResult search(Object criteria){
        throw new UnsupportedOperationException();
    }

    public OperationResult update(Object param){
        throw new UnsupportedOperationException();
    }

    public OperationResult pause(){
        throw new UnsupportedOperationException();
    }

    public OperationResult resume(){
        throw new UnsupportedOperationException();
    }

    public OperationResult restart(){
        throw new UnsupportedOperationException();
    }

}
