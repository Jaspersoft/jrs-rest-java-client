package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.users.UsersService;
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

    /*public Export exportService(){
        return new Export(credentials);
    }

    public Import importService(credentials){
        return new Import();
    }

    public Reporting reportingService(credentials){
        return new Reporting();
    }*/

}
