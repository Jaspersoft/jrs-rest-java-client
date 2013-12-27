package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.importexport.ExportTaskDto;
import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.ExportRequestBuilder;

import javax.ws.rs.core.Response;

public class Export {

    public static StateDto createNewTask(ExportTaskDto exportTaskDto){
        ExportRequestBuilder builder = new ExportRequestBuilder(StateDto.class);
        Response response = builder.post(exportTaskDto);
        return response.readEntity(StateDto.class);
    }
}
