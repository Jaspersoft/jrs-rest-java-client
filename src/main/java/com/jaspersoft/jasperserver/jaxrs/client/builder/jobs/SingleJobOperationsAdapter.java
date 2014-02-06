package com.jaspersoft.jasperserver.jaxrs.client.builder.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class SingleJobOperationsAdapter {

    private final SessionStorage sessionStorage;
    private final String jobId;

    public SingleJobOperationsAdapter(SessionStorage sessionStorage, String jobId) {
        this.sessionStorage = sessionStorage;
        this.jobId = jobId;
    }

    public OperationResult get(){
        throw new UnsupportedOperationException();
    }

    public OperationResult state(){
        throw new UnsupportedOperationException();
    }

    public OperationResult update(){
        throw new UnsupportedOperationException();
    }
}
