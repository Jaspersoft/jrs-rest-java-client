package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.importexport.ExportTaskDto;
import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import java.util.ArrayList;
import java.util.List;

public class ExportTaskBuilder {

    private final SessionStorage sessionStorage;
    private final ExportTaskDto exportTaskDto;

    public ExportTaskBuilder(SessionStorage sessionStorage){
        this.sessionStorage = sessionStorage;

        this.exportTaskDto = new ExportTaskDto();
        this.exportTaskDto.setParameters(new ArrayList<String>());
        this.exportTaskDto.setRoles(new ArrayList<String>());
        this.exportTaskDto.setUsers(new ArrayList<String>());
        this.exportTaskDto.setUris(new ArrayList<String>());
    }

    public ExportTaskBuilder role(String role){
        exportTaskDto.getRoles().add(role);
        return this;
    }

    public ExportTaskBuilder roles(List<String> roles){
        exportTaskDto.getRoles().addAll(roles);
        return this;
    }

    public ExportTaskBuilder user(String user){
        exportTaskDto.getUsers().add(user);
        return this;
    }

    public ExportTaskBuilder users(List<String> users){
        exportTaskDto.getUsers().addAll(users);
        return this;
    }

    public ExportTaskBuilder uri(String uri){
        exportTaskDto.getUris().add(uri);
        return this;
    }

    public ExportTaskBuilder uris(List<String> uris){
        exportTaskDto.getUris().addAll(uris);
        return this;
    }

    public ExportTaskBuilder parameter(ExportParameter parameter){
        exportTaskDto.getParameters().add(parameter.getParamName());
        return this;
    }

    public ExportTaskBuilder parameters(List<ExportParameter> parameters){
        for (ExportParameter exportParameter : parameters)
            parameter(exportParameter);
        return this;
    }

    public OperationResult<StateDto> create(){
        JerseyRequestBuilder<StateDto> builder =
                new JerseyRequestBuilder<StateDto>(sessionStorage, StateDto.class);
        builder.setPath("export");
        return builder.post(exportTaskDto);
    }

}
