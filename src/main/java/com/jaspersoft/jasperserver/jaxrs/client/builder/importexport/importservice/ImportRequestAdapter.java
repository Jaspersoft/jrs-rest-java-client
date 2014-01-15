package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

public class ImportRequestAdapter {

    private static final String STATE_URI = "/state";
    private final SessionStorage sessionStorage;

    private String taskId;

    public ImportRequestAdapter(SessionStorage sessionStorage, String taskId){
        this.sessionStorage = sessionStorage;
        this.taskId = taskId;
    }

    public OperationResult<StateDto> state(){
        JerseyRequestBuilder<StateDto> builder =
                new JerseyRequestBuilder<StateDto>(sessionStorage, StateDto.class);
        builder
                .setPath("import")
                .setPath(taskId)
                .setPath(STATE_URI);

        return builder.get();
    }

}
