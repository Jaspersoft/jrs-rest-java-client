package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.UsersService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._export.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.importexport._import.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.permissions.PermissionsService;

public class JasperserverRestServiceCatalog {

    private AuthenticationCredentials credentials;

    public JasperserverRestServiceCatalog(AuthenticationCredentials credentials){
        this.credentials = credentials;
    }

    public UsersService usersService(){
        return new UsersService(credentials);
    }

    public RolesService rolesService(){
        return new RolesService(credentials);
    }

    public PermissionsService permissionsService(){
        return new PermissionsService(credentials);
    }

    public ExportService exportService(){
        return new ExportService(credentials);
    }

    public ImportService importService(){
        return new ImportService(credentials);
    }

    /*public Reporting reportingService(){
        return new Reporting(credentials);
    }*/

}
