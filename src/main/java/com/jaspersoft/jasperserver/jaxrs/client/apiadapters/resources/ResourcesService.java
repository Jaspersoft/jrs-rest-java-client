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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientDomainTopic;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.DomainResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.DomainTopicResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.MondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.ReportUnitResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SemanticLayerResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.ResourceBuilderFactory.getBuilder;

public class ResourcesService extends AbstractAdapter {

    public ResourcesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchResourcesAdapter resources() {
        return new BatchResourcesAdapter(sessionStorage);
    }

    public SingleResourceAdapter resource(String resourceUri) {
        return new SingleResourceAdapter(sessionStorage, resourceUri);
    }

    public SingleResourceAdapter resource(ClientResource resourceDescriptor) {
        return new SingleResourceAdapter(sessionStorage, resourceDescriptor);
    }

    public SingleFileResourceUploadAdapter fileResource(InputStream inputStream, ClientFile resourceDescriptor) {
        return new SingleFileResourceUploadAdapter(sessionStorage, inputStream, resourceDescriptor);
    }

    public SingleFileResourceUploadAdapter fileResource(String fileResourceUri) {
        return new SingleFileResourceUploadAdapter(sessionStorage, fileResourceUri);
    }

    /**
     * Additional features to work with such resources as
     * - SemanticLayerDataSource
     * - ReportUnit
     * - MondrianConnection
     * - SecureMondrianConnection
     */
    @Deprecated
    public SemanticLayerResourceBuilder resource(ClientSemanticLayerDataSource resourceDescriptor) {
        ClientSemanticLayerDataSource copy = new ClientSemanticLayerDataSource(resourceDescriptor);
        return getBuilder(copy, sessionStorage);
    }

    public SemanticLayerResourceBuilder semanticLayerDataSourceResource(ClientSemanticLayerDataSource resourceDescriptor) {
        return getBuilder(resourceDescriptor, sessionStorage);
    }

    public DomainResourceBuilder domainResource(ClientDomain resourceDescriptor) {
        return getBuilder(resourceDescriptor, sessionStorage);
    }

    public ReportUnitResourceBuilder reportUnitResource(ClientReportUnit resourceDescriptor) {
        return getBuilder(resourceDescriptor, sessionStorage);
    }

    public DomainTopicResourceBuilder domainTopicResource(ClientDomainTopic resourceDescriptor) {
        return getBuilder(resourceDescriptor, sessionStorage);
    }

    public MondrianConnectionResourceBuilder mondrianConnection(ClientMondrianConnection mondrianConnectionDescriptor) {
        return getBuilder(mondrianConnectionDescriptor, sessionStorage);
    }

    public SecureMondrianConnectionResourceBuilder secureMondrianConnection(ClientSecureMondrianConnection mondrianConnectionDescriptor) {
        return getBuilder(mondrianConnectionDescriptor, sessionStorage);
    }

    @Deprecated
    public ReportUnitResourceBuilder resource(ClientReportUnit resourceDescriptor) {
        ClientReportUnit copy = new ClientReportUnit(resourceDescriptor);
        return getBuilder(copy, sessionStorage);
    }

    @Deprecated
    public MondrianConnectionResourceBuilder resource(ClientMondrianConnection resourceDescriptor) {
        ClientMondrianConnection copy = new ClientMondrianConnection(resourceDescriptor);
        return getBuilder(copy, sessionStorage);
    }

    @Deprecated
    public SecureMondrianConnectionResourceBuilder resource(ClientSecureMondrianConnection resourceDescriptor) {
        ClientSecureMondrianConnection copy = new ClientSecureMondrianConnection(resourceDescriptor);
        return getBuilder(copy, sessionStorage);
    }
}
