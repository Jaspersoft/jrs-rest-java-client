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

package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class ExportRequestAdapter {

    private static final String STATE_URI = "/state";
    private final SessionStorage sessionStorage;

    private String taskId;

    public ExportRequestAdapter(SessionStorage sessionStorage, String taskId) {
        this.sessionStorage = sessionStorage;
        this.taskId = taskId;
    }

    public OperationResult<StateDto> state() {
        return buildRequest(sessionStorage, StateDto.class, new String[]{"/export", taskId, STATE_URI}).get();
    }

    public OperationResult<InputStream> fetch() {

        while (!"finished".equals(state().getEntity().getPhase())) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        JerseyRequestBuilder<InputStream> builder =
                buildRequest(sessionStorage, InputStream.class, new String[]{"/export", taskId, "/mockFilename"});
        builder.setAccept("application/zip");

        OperationResult<InputStream> result = builder.get();
        sessionStorage.setSessionId(result.getSessionId());

        return result;
    }

}
