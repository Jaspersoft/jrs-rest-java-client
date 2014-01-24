package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.UsersService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionsService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.ReportingService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.resources.ResourcesService;

public class Session {

    private SessionStorage storage;

    public Session(SessionStorage sessionStorage){
        this.storage = sessionStorage;
    }

    public UsersService usersService(){

        return new UsersService(storage);
    }

    public RolesService rolesService(){
        return new RolesService(storage);
    }

    public PermissionsService permissionsService(){
        return new PermissionsService(storage);
    }

    public ExportService exportService(){
        return new ExportService(storage);
    }

    public ImportService importService(){
        return new ImportService(storage);
    }

    public ReportingService reportingService(){
        return new ReportingService(storage);
    }

    public ResourcesService resourcesService(){
        return new ResourcesService(storage);
    }

}
