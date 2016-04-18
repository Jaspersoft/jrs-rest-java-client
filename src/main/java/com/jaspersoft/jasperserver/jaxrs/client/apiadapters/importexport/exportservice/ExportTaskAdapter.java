/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.ExportTaskDto;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class ExportTaskAdapter extends AbstractAdapter {

    private final ExportTaskDto exportTaskDto;

    public ExportTaskAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.exportTaskDto = new ExportTaskDto();
        this.exportTaskDto.setParameters(new ArrayList<String>());
        this.exportTaskDto.setRoles(new ArrayList<String>());
        this.exportTaskDto.setUsers(new ArrayList<String>());
        this.exportTaskDto.setUris(new ArrayList<String>());
    }

    public ExportTaskAdapter role(String role) {
        exportTaskDto.getRoles().add(role);
        return this;
    }

    public ExportTaskAdapter roles(List<String> roles) {
        exportTaskDto.getRoles().addAll(roles);
        return this;
    }

    public ExportTaskAdapter user(String user) {
        exportTaskDto.getUsers().add(user);
        return this;
    }

    public ExportTaskAdapter users(List<String> users) {
        exportTaskDto.getUsers().addAll(users);
        return this;
    }

    public ExportTaskAdapter uri(String uri) {
        exportTaskDto.getUris().add(uri);
        return this;
    }

    public ExportTaskAdapter uris(List<String> uris) {
        exportTaskDto.getUris().addAll(uris);
        return this;
    }

    public ExportTaskAdapter parameter(ExportParameter parameter) {
        exportTaskDto.getParameters().add(parameter.getParamName());
        return this;
    }

    public ExportTaskAdapter parameters(List<ExportParameter> parameters) {
        for (ExportParameter exportParameter : parameters) {
            parameter(exportParameter);
        }
        return this;
    }

    public OperationResult<StateDto> create() {
        return buildRequest(sessionStorage, StateDto.class, new String[]{"/export"},
                new DefaultErrorHandler()).post(exportTaskDto);
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult<StateDto>, R> callback) {
        final JerseyRequest<StateDto> request = buildRequest(sessionStorage, StateDto.class, new String[]{"/export"});
        request.setAccept("application/zip");
        // Guarantee that exportTaskDto won't be modified from another thread
        final ExportTaskDto localCopy = new ExportTaskDto(exportTaskDto);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(localCopy));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
