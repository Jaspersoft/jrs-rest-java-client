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

import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.ResourceBuilderFactory;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.DomainResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.MondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.ReportUnitResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

public class ResourcesService extends AbstractAdapter {

    public ResourcesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchResourcesAdapter resources() {
        return new BatchResourcesAdapter(sessionStorage);
    }

    public SingleResourceAdapter resource(String uri) {
        return new SingleResourceAdapter(sessionStorage, uri);
    }

    public DomainResourceBuilder resource(ClientSemanticLayerDataSource resource) {
        ClientSemanticLayerDataSource copy = new ClientSemanticLayerDataSource(resource);
        return ResourceBuilderFactory.getBuilder(copy, sessionStorage);
    }

    public ReportUnitResourceBuilder resource(ClientReportUnit resource) {
        ClientReportUnit copy = new ClientReportUnit(resource);
        return ResourceBuilderFactory.getBuilder(copy, sessionStorage);
    }

    public MondrianConnectionResourceBuilder resource(ClientMondrianConnection resource) {
        ClientMondrianConnection copy = new ClientMondrianConnection(resource);
        return ResourceBuilderFactory.getBuilder(copy, sessionStorage);
    }

    public SecureMondrianConnectionResourceBuilder resource(ClientSecureMondrianConnection resource) {
        ClientSecureMondrianConnection copy = new ClientSecureMondrianConnection(resource);
        return ResourceBuilderFactory.getBuilder(copy, sessionStorage);
    }
}
