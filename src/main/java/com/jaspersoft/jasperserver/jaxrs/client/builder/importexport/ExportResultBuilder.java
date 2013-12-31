package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;

import java.io.InputStream;

public class ExportResultBuilder {

    private static final String STATE_URI = "/state";

    private String taskId;

    public ExportResultBuilder(String taskId){
        this.taskId = taskId;
    }

    public ExportResultReceiver<StateDto> state(){
        ExportRequestBuilder<Object, StateDto> requestBuilder =
                new ExportRequestBuilder<Object, StateDto>(StateDto.class);
        requestBuilder.setPath(taskId).setPath(STATE_URI);
        return new ExportResultReceiver<StateDto>(requestBuilder);
    }

    public ExportResultReceiver<InputStream> fetch(){
        ExportRequestBuilder<Object, InputStream> requestBuilder =
                new ExportRequestBuilder<Object, InputStream>(InputStream.class);
        requestBuilder.setPath(taskId).setPath("mockFilename");
        return new ExportResultReceiver<InputStream>(requestBuilder);
    }

}
