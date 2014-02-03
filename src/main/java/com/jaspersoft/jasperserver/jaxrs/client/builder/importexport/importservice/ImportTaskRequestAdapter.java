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

package com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice;

import com.jaspersoft.jasperserver.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder.buildRequest;

public class ImportTaskRequestAdapter {

    private final SessionStorage sessionStorage;
    private final MultivaluedMap<String, String> params;

    public ImportTaskRequestAdapter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        this.params = new MultivaluedHashMap<String, String>();
    }

    public ImportTaskRequestAdapter parameter(ImportParameter parameter, boolean value) {
        params.add(parameter.getParamName(), Boolean.toString(value));
        return this;
    }

    public OperationResult<StateDto> create(File zipArchive) {
        return createImport(zipArchive);
    }

    public OperationResult<StateDto> create(InputStream zipArchive) {
        return createImport(zipArchive);
    }

    private OperationResult<StateDto> createImport(Object zipArchive) {
        return buildRequest(sessionStorage, StateDto.class, new String[]{"/import"},
                "application/zip", MediaType.APPLICATION_JSON, params, null)
                .post(zipArchive);
    }
}
