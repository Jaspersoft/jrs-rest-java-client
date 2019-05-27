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
import java.util.Collections;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

/**
 * @Deprecated use @Link {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter}
 * and setters of @Link {@link com.jaspersoft.jasperserver.dto.importexport.ExportTask}
 * */
@Deprecated
public class ExportTaskAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "export";
    private final ExportTask exportTask;

    public ExportTaskAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        this.exportTask = new ExportTask();
    }

    public ExportTaskAdapter role(String role) {
        if (role != null && !role.isEmpty()) {
            if (exportTask.getRoles() == null) {
                exportTask.setRoles(new ArrayList<String>());
            }
            exportTask.getRoles().add(role);
        }
        return this;
    }

    public ExportTaskAdapter roles(List<String> roles) {
        if (roles != null && !roles.isEmpty()) {
            for (String singleRole : roles) {
                this.role(singleRole);
            }
        }
        return this;
    }


    public ExportTaskAdapter allRoles() {
        exportTask.setRoles(Collections.<String>emptyList());
        return this;
    }

    public ExportTaskAdapter user(String user) {
        if (user != null && !user.isEmpty()) {
            if (exportTask.getUsers() == null) {
                exportTask.setUsers(new ArrayList<String>());
            }
            exportTask.getUsers().add(user);
        }
        return this;
    }

    public ExportTaskAdapter users(List<String> users) {
        if (users != null && !users.isEmpty()) {
            for (String singleUser : users) {
                this.user(singleUser);
            }
        }
        return this;
    }

    public ExportTaskAdapter allUsers() {
        exportTask.setUsers(new ArrayList<String>());
        return this;
    }

    public ExportTaskAdapter uri(String uri) {
        if (uri != null && !uri.isEmpty()) {
            if (exportTask.getUris() == null) {
                exportTask.setUris(new ArrayList<String>());
            }
            exportTask.getUris().add(uri);
        }
        return this;
    }

    public ExportTaskAdapter uris(List<String> uris) {
        if (uris != null && !uris.isEmpty() ) {
            for (String singleUri : uris) {
                this.uri(singleUri);
            }
        }
        return this;
    }

    public ExportTaskAdapter scheduledJob(String scheduledJob) {

        if (scheduledJob != null && !scheduledJob.isEmpty()) {
            if (exportTask.getScheduledJobs() == null) {
                exportTask.setScheduledJobs(new ArrayList<String>());
            }
            exportTask.getScheduledJobs().add(scheduledJob);
        }
        return this;
    }

    public ExportTaskAdapter scheduledJobs(List<String> scheduledJobs) {
        if (scheduledJobs != null && !scheduledJobs.isEmpty()) {
            for (String singleJob : scheduledJobs) {
                this.scheduledJob(singleJob);
            }
        }
        return this;
    }

    public ExportTaskAdapter resourceType(String resourceType) {
        if (resourceType != null && !resourceType.isEmpty()) {
            if (exportTask.getResourceTypes() == null) {
                exportTask.setResourceTypes(new ArrayList<String>());
            }
            exportTask.getResourceTypes().add(resourceType);
        }
        return this;
    }

    public ExportTaskAdapter resourceTypes(List<String> resourceTypes) {
        if (resourceTypes != null && !resourceTypes.isEmpty()) {
            for (String singleResourceType : resourceTypes) {
                this.resourceType(singleResourceType);
            }
        }
        return this;
    }

    public ExportTaskAdapter parameter(ExportParameter parameter) {
        if (exportTask.getParameters() == null) {
            exportTask.setParameters(new ArrayList<String>());
        }
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
        return buildRequest(sessionStorage, State.class, new String[]{SERVICE_URI},
                new DefaultErrorHandler()).post(exportTask);
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult<State>, R> callback) {
        final JerseyRequest<State> request = buildRequest(sessionStorage, State.class, new String[]{SERVICE_URI});
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
