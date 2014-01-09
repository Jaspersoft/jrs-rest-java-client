package com.jaspersoft.jasperserver.jaxrs.client.builder;

import com.jaspersoft.jasperserver.jaxrs.client.rest_services.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.rest_services.UsersService;

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

    /*public Permissions permissionsService(){
        return new Permissions(credentials);
    }

    public Export exportService(){
        return new Export(credentials);
    }

    public Import importService(credentials){
        return new Import();
    }

    public Reporting reportingService(credentials){
        return new Reporting();
    }*/

}
