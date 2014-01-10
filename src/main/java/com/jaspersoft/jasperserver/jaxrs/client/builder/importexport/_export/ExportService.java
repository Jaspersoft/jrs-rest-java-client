package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._export;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;

public class ExportService {

    private final AuthenticationCredentials credentials;

    public ExportService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public ExportTaskBuilder newTask(){
        return new ExportTaskBuilder(credentials);
    }

    public ExportRequestAdapter task(String taskId){
        return new ExportRequestAdapter(credentials, taskId);
    }


}
