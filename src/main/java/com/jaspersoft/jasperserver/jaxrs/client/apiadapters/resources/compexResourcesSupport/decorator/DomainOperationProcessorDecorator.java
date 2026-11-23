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

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.WrongResourceFormatException;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.processor.CommonOperationProcessorImpl;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * @author Alexander Krasnyanskiy
 */
public class DomainOperationProcessorDecorator {
    protected CommonOperationProcessorImpl<ClientDomain> processor;
    protected ClientDomain domain;
    protected FormDataMultiPart multipart;
    protected String path;
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<>();

    public DomainOperationProcessorDecorator parameter(ResourceServiceParameter param, String value) {
        params.add(param.getName(), UrlUtils.encode(value));
        return this;
    }

    public DomainOperationProcessorDecorator parameter(ResourceServiceParameter param, Boolean value) {
        params.add(param.getName(), value.toString());
        return this;
    }

    public DomainOperationProcessorDecorator parameter(String param, String value) {
        params.add(param, UrlUtils.encode(value));
        return this;
    }


    public DomainOperationProcessorDecorator(SessionStorage sessionStorage, ClientDomain domain) {
        this.processor = new CommonOperationProcessorImpl(domain, domain.getClass(), sessionStorage);
        this.multipart = new FormDataMultiPart();
        this.domain = domain;
    }

    public OperationResult<ClientDomain> get() {
        if (domain.getUri() == null) {
            throw new WrongResourceFormatException("Can't find uri!");
        }
        return processor.get(domain.getUri());
    }

    public DomainOperationProcessorDecorator inFolder(String parentFolder) {
        this.path = parentFolder;
        return this;
    }

    public OperationResult<ClientDomain> create() {
        return processor.create(multipart, new MediaType("application", "repository.domain+json"), path, params);
    }

    public OperationResult<ClientDomain> createOrUpdate() {
        return processor.createOrUpdate(multipart, new MediaType("application", "repository.domain+json"), domain.getUri(), params);
    }

}
