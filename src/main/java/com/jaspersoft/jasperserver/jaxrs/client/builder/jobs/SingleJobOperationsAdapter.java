package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobExtension;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class SingleJobOperationsAdapter {

    private final SessionStorage sessionStorage;
    private final String jobId;

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        this.sessionStorage = sessionStorage;
        this.jobId = jobId;
    }

    public OperationResult<JobExtension> get(){
        JerseyRequestBuilder<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs", jobId});
        builder.setAccept("application/job+json");
        return builder.get();
    }

    public OperationResult<JobState> state(){
        return buildRequest(sessionStorage, JobState.class, new String[]{"/jobs", jobId, "/state"})
                .get();
    }

    public OperationResult<JobExtension> update(JobExtension job){
        JerseyRequestBuilder<JobExtension> builder =
                buildRequest(sessionStorage, JobExtension.class, new String[]{"/jobs", jobId});
        builder.setContentType("application/job+json");
        builder.setAccept("application/job+json");

        return builder.post(job);
    }

    public OperationResult delete() {
        return buildRequest(sessionStorage, Object.class, new String[]{"/jobs", jobId})
                .delete();
    }
}
