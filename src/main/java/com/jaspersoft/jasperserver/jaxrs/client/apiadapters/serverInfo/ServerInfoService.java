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

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.common.ServerInfo;

import javax.ws.rs.core.MediaType;


public class ServerInfoService extends AbstractAdapter {

    public ServerInfoService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public OperationResult<ServerInfo> details(){
        return JerseyRequestBuilder.buildRequest(sessionStorage, ServerInfo.class, new String[]{"/serverInfo"}, new DefaultErrorHandler()).get();
    }

    private JerseyRequestBuilder<String> buildServerInfoRequest(String path){
        JerseyRequestBuilder<String> builder =
                JerseyRequestBuilder.buildRequest(sessionStorage, String.class, new String[]{"/serverInfo", path}, new DefaultErrorHandler());
        builder.setAccept(MediaType.TEXT_PLAIN);
        return builder;
    }

    public OperationResult<String> edition(){
        return buildServerInfoRequest("/edition").get();
    }

    public OperationResult<String> version(){
        return buildServerInfoRequest("/version").get();
    }

    public OperationResult<String> build(){
        return buildServerInfoRequest("/build").get();
    }

    public OperationResult<String> features(){
        return buildServerInfoRequest("/features").get();
    }

    public OperationResult<String> editionName(){
        return buildServerInfoRequest("/editionName").get();
    }

    public OperationResult<String> licenseType(){
        return buildServerInfoRequest("/licenseType").get();
    }

    public OperationResult<String> expiration(){
        return buildServerInfoRequest("/expiration").get();
    }

    public OperationResult<String> dateFormatPattern(){
        return buildServerInfoRequest("/dateFormatPattern").get();
    }

    public OperationResult<String> dateTimeFormatPattern(){
        return buildServerInfoRequest("/datetimeFormatPattern").get();
    }

}
