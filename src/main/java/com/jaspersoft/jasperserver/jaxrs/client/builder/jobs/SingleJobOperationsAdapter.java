package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;

import static com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder.buildRequest;

public class SingleJobOperationsAdapter {

    private final SessionStorage sessionStorage;
    private final String jobId;

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        this.sessionStorage = sessionStorage;
        this.jobId = jobId;
    }

    public OperationResult<Job> get(){
        JerseyRequestBuilder<Job> builder = buildRequest(sessionStorage, Job.class, new String[]{"/jobs", jobId});
        builder.setAccept("application/job+json");
        return builder.get();
    }

    public OperationResult state(){
        throw new UnsupportedOperationException();
    }

    public OperationResult update(){
        throw new UnsupportedOperationException();
    }
}
