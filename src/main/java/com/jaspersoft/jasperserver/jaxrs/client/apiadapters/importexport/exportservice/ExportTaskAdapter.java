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
import com.jaspersoft.jasperserver.dto.importexport.ExportTask;
import com.jaspersoft.jasperserver.dto.importexport.State;

import java.util.ArrayList;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class ExportTaskAdapter extends AbstractAdapter {

    private final ExportTask exportTask;

    public ExportTaskAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.exportTask = new ExportTask();
        this.exportTask.setParameters(new ArrayList<String>());
        this.exportTask.setRoles(new ArrayList<String>());
        this.exportTask.setUsers(new ArrayList<String>());
        this.exportTask.setUris(new ArrayList<String>());
        this.exportTask.setResourceTypes(new ArrayList<String>());
        this.exportTask.setScheduledJobs(new ArrayList<String>());
    }

    public ExportTaskAdapter role(String role) {
        exportTask.getRoles().add(role);
        return this;
    }

    public ExportTaskAdapter roles(List<String> roles) {
        exportTask.getRoles().addAll(roles);
        return this;
    }

    public ExportTaskAdapter user(String user) {
        exportTask.getUsers().add(user);
        return this;
    }

    public ExportTaskAdapter users(List<String> users) {
        exportTask.getUsers().addAll(users);
        return this;
    }

    public ExportTaskAdapter uri(String uri) {
        exportTask.getUris().add(uri);
        return this;
    }

    public ExportTaskAdapter uris(List<String> uris) {
        exportTask.getUris().addAll(uris);
        return this;
    }

    public ExportTaskAdapter scheduledJob(String uri) {
        exportTask.getScheduledJobs().add(uri);
        return this;
    }

    public ExportTaskAdapter scheduledJobs(List<String> uris) {
        exportTask.getScheduledJobs().addAll(uris);
        return this;
    }

    public ExportTaskAdapter resourceType(String uri) {
        exportTask.getResourceTypes().add(uri);
        return this;
    }

    public ExportTaskAdapter resourceTypes(List<String> uris) {
        exportTask.getResourceTypes().addAll(uris);
        return this;
    }

    public ExportTaskAdapter parameter(ExportParameter parameter) {
        exportTask.getParameters().add(parameter.getParamName());
        return this;
    }

    public ExportTaskAdapter parameters(List<ExportParameter> parameters) {
        for (ExportParameter exportParameter : parameters) {
            parameter(exportParameter);
        }
        return this;
    }

    public ExportTaskAdapter organization(String organizationId) {
        exportTask.setOrganization(organizationId);
        return this;
    }

    public OperationResult<State> create() {
        return buildRequest(sessionStorage, State.class, new String[]{"/export"},
                new DefaultErrorHandler()).post(exportTask);
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult<State>, R> callback) {
        final JerseyRequest<State> request = buildRequest(sessionStorage, State.class, new String[]{"/export"});
        request.setAccept("application/zip");
        // Guarantee that exportTask won't be modified from another thread
        final ExportTask localCopy = new ExportTask(exportTask);
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
