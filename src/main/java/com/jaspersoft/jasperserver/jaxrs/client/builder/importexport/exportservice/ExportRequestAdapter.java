package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.concurrent.Future;

public class ExportRequestAdapter {

    private static final String STATE_URI = "/state";
    private final SessionStorage sessionStorage;

    private String taskId;

    public ExportRequestAdapter(SessionStorage sessionStorage, String taskId){
        this.sessionStorage = sessionStorage;
        this.taskId = taskId;
    }

    public OperationResult<StateDto> state(){
        JerseyRequestBuilder<StateDto> builder =
                new JerseyRequestBuilder<StateDto>(sessionStorage, StateDto.class);
        builder
                .setPath("export")
                .setPath(taskId)
                .setPath(STATE_URI);

        return builder.get();
    }

    public OperationResult<InputStream> fetch(){
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(sessionStorage, InputStream.class);
        builder
                .setPath("export")
                .setPath(taskId)
                .setPath("mockFilename");

        try {
            AsyncInvoker asyncInvoker = builder.getPath().request("application/zip").async();
            Future<Response> responseFuture = asyncInvoker.get();
            OperationResult<InputStream> result =
                    new OperationResult<InputStream>(responseFuture.get(), InputStream.class);
            sessionStorage.setSessionId(result.getSessionId());
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
