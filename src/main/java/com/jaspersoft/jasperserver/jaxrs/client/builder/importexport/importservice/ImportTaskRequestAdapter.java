package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Future;

public class ImportTaskRequestAdapter {

    private final SessionStorage sessionStorage;
    private JerseyRequestBuilder<StateDto> builder;

    public ImportTaskRequestAdapter(SessionStorage sessionStorage){
        this.sessionStorage = sessionStorage;
        this.builder = new JerseyRequestBuilder<StateDto>(sessionStorage, StateDto.class);
        builder.setPath("import");
    }

    public ImportTaskRequestAdapter parameter(ImportParameter parameter, boolean value){
        builder.addParam(parameter.getParamName(), Boolean.toString(value));
        return this;
    }

    public OperationResult<StateDto> create(File zipArchive){
        return createImport(zipArchive);
    }

    public OperationResult<StateDto> create(InputStream zipArchive){
        return createImport(zipArchive);
    }

    private OperationResult<StateDto> createImport(Object zipArchive){
        try {
            AsyncInvoker asyncInvoker = builder.getPath().request(MediaType.APPLICATION_JSON).async();
            Future<Response> responseFuture = asyncInvoker.post(Entity.entity(zipArchive, "application/zip"));
            OperationResult<StateDto> result =
                    new OperationResult<StateDto>(responseFuture.get(), StateDto.class);
            sessionStorage.setSessionId(result.getSessionId());
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
