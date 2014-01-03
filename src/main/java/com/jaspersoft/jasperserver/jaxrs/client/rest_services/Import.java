package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportResultBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ImportTaskBuilder;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Import {

    public static ImportTaskBuilder newTask(){
        return new ImportTaskBuilder(new ImportRequestBuilder());
    }

    public static ImportResultBuilder task(String taskId){
        return new ImportResultBuilder(taskId);
    }

    public static void main(String[] args) throws URISyntaxException {
        URL url = Import.class.getClassLoader().getResource("myzip.zip");
        OperationResult<StateDto> operationResult =
                Import.newTask()
                        .parameter(ImportParameter.INCLUDE_ACCESS_EVENTS, true)
                        .post(new File(url.toURI()));

        StateDto state = operationResult.getEntity();
        System.out.println(state.getId());
        System.out.println(state.getMessage());
        System.out.println(state.getPhase());
    }

}
