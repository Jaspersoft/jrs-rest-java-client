package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._import;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;

public class ImportRequestAdapter {

    private static final String STATE_URI = "/state";
    private final AuthenticationCredentials credentials;

    private String taskId;

    public ImportRequestAdapter(AuthenticationCredentials credentials, String taskId){
        this.credentials = credentials;
        this.taskId = taskId;
    }

    public OperationResult<StateDto> state(){
        JerseyRequestBuilder<StateDto> builder =
                new JerseyRequestBuilder<StateDto>(credentials, StateDto.class);
        builder
                .setPath("import")
                .setPath(taskId)
                .setPath(STATE_URI);

        return builder.get();
    }

}
