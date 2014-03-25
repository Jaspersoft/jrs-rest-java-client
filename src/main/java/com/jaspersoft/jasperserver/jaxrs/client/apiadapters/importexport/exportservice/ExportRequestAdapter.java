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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.CommonExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.ExceptionHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;

import java.io.InputStream;
import java.util.Arrays;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class ExportRequestAdapter extends AbstractAdapter {

    private static final String STATE_URI = "/state";
    private String taskId;
    private ExceptionHandler exceptionHandler;

    public ExportRequestAdapter(SessionStorage sessionStorage, String taskId) {
        super(sessionStorage);
        this.taskId = taskId;
        this.exceptionHandler = new CommonExceptionHandler();
    }

    public OperationResult<StateDto> state() {
        return buildRequest(sessionStorage, StateDto.class, new String[]{"/export", taskId, STATE_URI}, exceptionHandler)
                .get();
    }

    public OperationResult<InputStream> fetch() {

        StateDto state;
        while (!"finished".equals((state = state().getEntity()).getPhase())) {

            if ("failed".equals(state.getPhase())){
                if (state.getErrorDescriptor() != null)
                    throw new ExportFailedException(state.getErrorDescriptor().getMessage(), Arrays.asList(state.getErrorDescriptor()));
                else
                    throw new ExportFailedException(state.getMessage());
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        JerseyRequestBuilder<InputStream> builder =
                buildRequest(sessionStorage, InputStream.class, new String[]{"/export", taskId, "/mockFilename"}, exceptionHandler);
        builder.setAccept("application/zip");

        OperationResult<InputStream> result = builder.get();
        sessionStorage.setSessionId(result.getSessionId());

        return result;
    }

}
