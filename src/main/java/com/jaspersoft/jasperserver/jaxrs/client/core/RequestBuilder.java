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

package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedMap;

public interface RequestBuilder<ResponseType> {

    //GET-DELETE
    OperationResult<ResponseType> get();
    OperationResult<ResponseType> delete();

    //PUT-POST
    OperationResult<ResponseType> put(Object entity);
    OperationResult<ResponseType> post(Object entity);

    //common
    RequestBuilder<ResponseType> addParam(String name, String... values);
    RequestBuilder<ResponseType> addParams(MultivaluedMap<String, String> params);
    RequestBuilder<ResponseType> addMatrixParam(String name, String... values);
    RequestBuilder<ResponseType> addMatrixParams(MultivaluedMap<String, String> params);

    //util
    RequestBuilder<ResponseType> setPath(String path);
    RequestBuilder<ResponseType> setContentType(String mime);
    RequestBuilder<ResponseType> setAccept(String acceptMime);
    RequestBuilder<ResponseType> addHeader(String name, String... values);
    RequestBuilder<ResponseType> setHeaders(MultivaluedMap<String, String> headers);
}
