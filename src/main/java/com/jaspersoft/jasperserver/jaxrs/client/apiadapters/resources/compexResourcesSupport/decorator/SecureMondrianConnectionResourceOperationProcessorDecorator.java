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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator;

import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.processor.CommonOperationProcessorImpl;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import javax.ws.rs.core.MediaType;

/**
 * @author Alexander Krasnyanskiy
 */
public class SecureMondrianConnectionResourceOperationProcessorDecorator {
    protected CommonOperationProcessorImpl<ClientSecureMondrianConnection> processor;
    protected ClientSecureMondrianConnection connection;
    protected FormDataMultiPart multipart;
    protected String path;

    public SecureMondrianConnectionResourceOperationProcessorDecorator(SessionStorage sessionStorage, ClientSecureMondrianConnection connection) {
        this.processor = new CommonOperationProcessorImpl(connection, connection.getClass(), sessionStorage);
        this.multipart = new FormDataMultiPart();
        this.connection = connection;
    }

    @Deprecated
    public OperationResult<ClientSecureMondrianConnection> createInFolder(String path) {
        return processor.create(multipart, new MediaType("application", "repository.secureMondrianConnection+xml"), path);
    }


    public SecureMondrianConnectionResourceOperationProcessorDecorator inFolder(String parentFolder) {
        this.path = parentFolder;
        return this;
    }

    public OperationResult<ClientSecureMondrianConnection> create() {
        return processor.create(multipart, new MediaType("application", "repository.secureMondrianConnection+xml"), path);
    }

}
