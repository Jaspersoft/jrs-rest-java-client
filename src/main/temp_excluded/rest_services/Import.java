package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.jaxrs.client.builder.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportResultBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportTaskBuilder;

public class Import {

    private final AuthenticationCredentials credentials;

    public Import(AuthenticationCredentials credentials) {
        this.credentials = credentials;
    }

    public static ImportTaskBuilder newTask(){
        return new ImportTaskBuilder(new ImportRequestBuilder());
    }

    public static ImportResultBuilder task(String taskId){
        return new ImportResultBuilder(taskId);
    }

}
