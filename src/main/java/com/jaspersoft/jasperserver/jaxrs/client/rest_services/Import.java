package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportResultBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportTaskBuilder;

public class Import {

    public static ImportTaskBuilder newTask(){
        return new ImportTaskBuilder(new ImportRequestBuilder());
    }

    public static ImportResultBuilder task(String taskId){
        return new ImportResultBuilder(taskId);
    }

}
