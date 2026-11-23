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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.dto.importexport.ImportTask;
import com.jaspersoft.jasperserver.dto.importexport.State;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class ImportRequestAdapter extends AbstractAdapter {

    private static final String STATE_URI = "state";
    public static final String IMPORT_SERVICE_URI = "import";
    private Boolean useMultiPart;
    private MultivaluedMap<String, String> params;
    private String taskId;
    private File file;
    private ImportTask importTask;

    public ImportRequestAdapter(SessionStorage sessionStorage, String taskId) {
        super(sessionStorage);
        this.taskId = taskId;
        this.importTask = new ImportTask();
        importTask.setParameters(new LinkedList<String>());
    }

    public ImportRequestAdapter(SessionStorage sessionStorage, File file, Boolean useMultiPart) {
        super(sessionStorage);
        this.file = file;
        this.params = new MultivaluedHashMap<>();
        this.useMultiPart = useMultiPart;
    }

    public ImportRequestAdapter parameter(ImportParameter parameter, boolean value) {
        params.add(useMultiPart ? parameter.getMultiPartParamName() : parameter.getParamName(), Boolean.toString(value));
        return this;
    }

    public ImportRequestAdapter parameter(ImportParameter parameter, String value) {
        params.add(useMultiPart ? parameter.getMultiPartParamName() : parameter.getParamName(), UrlUtils.encode(value));
        return this;
    }

    public ImportRequestAdapter parameter(ImportParameter parameter, BrokenDependenciesParameter value) {
        params.add(useMultiPart ? parameter.getMultiPartParamName() : parameter.getParamName(), value.getValueName());
        return this;
    }

    public OperationResult<State> create() {
        return (useMultiPart) ? buildMultiPartRequest().post(prepareForm()) : buildCreateRequest().post(file);
    }

    private FormDataMultiPart prepareForm() {
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", file);
        for (Map.Entry<String, List<String>> param : params.entrySet()) {
            formDataMultiPart.field(param.getKey(), param.getValue().get(0));
        }
        formDataMultiPart.bodyPart(fileDataBodyPart);
        return formDataMultiPart;
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult<State>, R> callback) {
        final JerseyRequest<State> request = buildCreateRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(file));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<State> buildCreateRequest() {
        JerseyRequest<State> request = buildRequest(sessionStorage, State.class, new String[]{IMPORT_SERVICE_URI});
        request.setContentType("application/zip").addParams(params);
        return request;
    }

    private JerseyRequest<State> buildMultiPartRequest() {
        JerseyRequest<State> request = JerseyRequest.buildRequest(sessionStorage,
                State.class,
                new String[]{IMPORT_SERVICE_URI});
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        return request;
    }


    public OperationResult<State> state() {
        return buildRequest(sessionStorage, State.class,
                new String[]{IMPORT_SERVICE_URI, taskId, STATE_URI}, new DefaultErrorHandler())
                .get();
    }

    public OperationResult<ImportTask> getTask() {
        return buildTaskRequest().get();
    }

    protected JerseyRequest<ImportTask> buildTaskRequest() {
        return buildRequest(sessionStorage, ImportTask.class,
                new String[]{IMPORT_SERVICE_URI, taskId});
    }

    public OperationResult<ImportTask> restartTask(ImportTask task) {
        return buildTaskRequest().put(task);
    }

    public OperationResult cancel() {
        return buildRequest(sessionStorage, Object.class, new String[]{IMPORT_SERVICE_URI, taskId}).delete();
    }

    public <R> RequestExecution asyncState(final Callback<OperationResult<State>, R> callback) {
        final JerseyRequest<State> request = buildRequest(sessionStorage, State.class, new String[]{IMPORT_SERVICE_URI, taskId, STATE_URI});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}
