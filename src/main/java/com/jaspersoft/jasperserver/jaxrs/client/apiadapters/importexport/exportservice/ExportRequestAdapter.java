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
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.importexport.State;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;

public class ExportRequestAdapter extends AbstractAdapter {

    private ExecutorService service = Executors.newFixedThreadPool(4);

    private static final String STATE_URI = "/state";
    private final String taskId;

    public ExportRequestAdapter(SessionStorage sessionStorage, String taskId) {
        super(sessionStorage);
        this.taskId = taskId;
    }

    public OperationResult<State> state() {
        return buildRequest(sessionStorage, State.class, new String[]{"/export", taskId, STATE_URI}).get();
    }

    public <R> RequestExecution asyncState(final Callback<OperationResult<State>, R> callback) {
        final JerseyRequest<State> request = buildRequest(sessionStorage, State.class, new String[]{"/export", taskId, STATE_URI});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<InputStream> fetch() {
        State state;
        while (!"finished".equals((state = state().getEntity()).getPhase())) {
            if ("failed".equals(state.getPhase())) {
                if (state.getError() != null) {
                    throw new ExportFailedException(state.getError().getMessage(), Arrays.asList(state.getError()));
                } else {
                    throw new ExportFailedException(state.getMessage());
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
                // NOP
            }
        }
        JerseyRequest<InputStream> request = buildRequest(sessionStorage, InputStream.class, new String[]{"/export", taskId, "/exportFile"});
        request.setAccept("application/zip");
        return request.get();
    }

    public <R> RequestExecution asyncFetch(final Callback<OperationResult<InputStream>, R> callback) {
        final JerseyRequest<InputStream> request = buildRequest(sessionStorage, InputStream.class, new String[]{"/export", taskId, "/exportFile"});
        request.setAccept("application/zip");
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                State state;
                while (!"finished".equals((state = state().getEntity()).getPhase())) {
                    if ("failed".equals(state.getPhase())) {
                        if (state.getError() != null) {
                            throw new ExportFailedException(state.getError().getMessage(), Arrays.asList(state.getError()));
                        } else {
                            throw new ExportFailedException(state.getMessage());
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                        // NOP
                    }
                }
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }
}