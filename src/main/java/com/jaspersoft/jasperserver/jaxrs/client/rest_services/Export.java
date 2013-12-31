package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.importexport.ExportTaskDto;
import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ExportRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ExportResultBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ExportTaskBuilder;

public class Export {

    public static ExportTaskBuilder newTask(){
        return new ExportTaskBuilder(new ExportRequestBuilder<ExportTaskDto, StateDto>(StateDto.class));
    }

    public static ExportResultBuilder task(String taskId){
        return new ExportResultBuilder(taskId);
    }


}
