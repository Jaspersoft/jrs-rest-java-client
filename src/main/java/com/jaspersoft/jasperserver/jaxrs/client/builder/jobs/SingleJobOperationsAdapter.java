package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;

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

    public OperationResult<JobState> state(){
        return buildRequest(sessionStorage, JobState.class, new String[]{"/jobs", jobId, "/state"})
                .get();
    }

    public OperationResult<Job> update(Job job){
        JerseyRequestBuilder<Job> builder =
                buildRequest(sessionStorage, Job.class, new String[]{"/jobs", jobId});
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.post(job);
    }

    public OperationResult delete() {
        return buildRequest(sessionStorage, Object.class, new String[]{"/jobs", jobId})
                .delete();
    }
}
