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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.processor;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.SingleResourceAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import javax.ws.rs.core.MediaType;

/**
 * @author Alexander Krasnyanskiy
 */
public class CommonOperationProcessorImpl<ResourceType> implements CommonOperationProcessor<ResourceType> {
    private ResourceType resource;
    private Class<ResourceType> resourceTypeClass;
    private SessionStorage sessionStorage;

    public CommonOperationProcessorImpl(ResourceType resource, Class<ResourceType> resourceTypeClass, SessionStorage sessionStorage) {
        this.resource = resource;
        this.resourceTypeClass = resourceTypeClass;
        this.sessionStorage = sessionStorage;
    }

    @Override
    public OperationResult<ResourceType> create(FormDataMultiPart multipart, MediaType mediaType, String path) {
        multipart.field("resource", resource, mediaType);
        return new SingleResourceAdapter(sessionStorage, path).uploadMultipartResource(multipart, resourceTypeClass);
    }

    @Override
    public OperationResult<ResourceType> get(String uri) {
        return new SingleResourceAdapter(sessionStorage, uri).get(resourceTypeClass);
    }

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }
}
