package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.concurrent.Future;

public class ImportTaskBuilder {

    private ImportRequestBuilder importRequestBuilder;

    public ImportTaskBuilder(ImportRequestBuilder importRequestBuilder){
        this.importRequestBuilder = importRequestBuilder;
    }

    public ImportTaskBuilder parameter(ImportParameter parameter, boolean value){
        importRequestBuilder.addParam(parameter.getParamName(), Boolean.toString(value));
        return this;
    }

    public OperationResult<StateDto> post(File zipArchive){
        try {
            AsyncInvoker asyncInvoker = importRequestBuilder.getPath().request(MediaType.APPLICATION_JSON).async();
            Future<Response> responseFuture = asyncInvoker.post(Entity.entity(zipArchive, "application/zip"));
            return new OperationResult<StateDto>(responseFuture.get(), StateDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
