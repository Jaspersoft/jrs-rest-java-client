package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._export;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.concurrent.Future;

public class ExportRequestAdapter {

    private static final String STATE_URI = "/state";
    private final AuthenticationCredentials credentials;

    private String taskId;

    public ExportRequestAdapter(AuthenticationCredentials credentials, String taskId){
        this.credentials = credentials;
        this.taskId = taskId;
    }

    public OperationResult<StateDto> state(){
        JerseyRequestBuilder<StateDto> builder =
                new JerseyRequestBuilder<StateDto>(credentials, StateDto.class);
        builder
                .setPath("export")
                .setPath(taskId)
                .setPath(STATE_URI);

        return builder.get();
    }

    public OperationResult<InputStream> fetch(){
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(credentials, InputStream.class);
        builder
                .setPath("export")
                .setPath(taskId)
                .setPath("mockFilename");

        try {
            AsyncInvoker asyncInvoker = builder.getPath().request("application/zip").async();
            Future<Response> responseFuture = asyncInvoker.get();
            return new OperationResult<InputStream>(responseFuture.get(), InputStream.class);
        } catch (Exception e) {
            return null;
        }
    }

}
