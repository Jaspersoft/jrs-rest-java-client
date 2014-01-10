package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._import;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;

public class ImportService {

    private final AuthenticationCredentials credentials;

    public ImportService(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public ImportTaskRequestAdapter newTask(){
        return new ImportTaskRequestAdapter(credentials);
    }

    public ImportRequestAdapter task(String taskId){
        return new ImportRequestAdapter(credentials, taskId);
    }

}
