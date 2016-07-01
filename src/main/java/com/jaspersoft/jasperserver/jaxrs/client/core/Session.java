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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.connections.ConnectionsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic.DiagnosticService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.DomainMetadataService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls.InputControlsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query.QueryExecutorService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportingService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails.ThumbnailsService;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;



public class Session extends AnonymousSession{


    public Session(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public void logout() {
        if (storage.getConfiguration().getAuthenticationType() == AuthenticationType.BASIC) {
            storage.getCredentials().setUsername(null);
            storage.getCredentials().setPassword(null);
        } else {
            WebTarget target = storage.getRootTarget().path("/logout.html");
            Response response = target.request().get();
            if (response.getStatus() >= 400) {
                new DefaultErrorHandler().handleError(response);
            }
        }
    }

    public OrganizationsService organizationsService() {
        return getService(OrganizationsService.class);
    }

    public UsersService usersService() {
        return getService(UsersService.class);
    }

    public RolesService rolesService() {
        return getService(RolesService.class);
    }

    public PermissionsService permissionsService() {
        return getService(PermissionsService.class);
    }

    public ExportService exportService() {
        return getService(ExportService.class);
    }

    public ImportService importService() {
        return getService(ImportService.class);
    }

    public ReportingService reportingService() {
        return getService(ReportingService.class);
    }

    public ResourcesService resourcesService() {
        return getService(ResourcesService.class);
    }

    public JobsService jobsService() {
        return getService(JobsService.class);
    }

    public DomainMetadataService domainService() {
        return getService(DomainMetadataService.class);
    }

    public QueryExecutorService queryExecutorService() {
        return getService(QueryExecutorService.class);
    }

    public ThumbnailsService thumbnailsService() {
        return getService(ThumbnailsService.class);
    }

    public AttributesService attributesService() {
        return getService(AttributesService.class);
    }

    public InputControlsService inputControlsService() {return getService(InputControlsService.class);}

    public DiagnosticService diagnosticService() {return getService(DiagnosticService.class);}

    public ConnectionsService connectionsService() {return getService(ConnectionsService.class);}

}