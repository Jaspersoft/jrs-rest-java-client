package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.ServerAttributesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.DomainMetadataService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query.QueryExecutorService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportingService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails.ThumbnailsService;

/**
 * Created by tetiana.iefimenko on 7/23/2015.
 */
public class AuthenticatedSession extends AnonymousSession {

    protected AuthenticatedSession() {
    }

    public AuthenticatedSession(SessionStorage storage) {
        super(storage);
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

    public ServerAttributesService serverAttributesService() {
        return getService(ServerAttributesService.class);
    }

}
