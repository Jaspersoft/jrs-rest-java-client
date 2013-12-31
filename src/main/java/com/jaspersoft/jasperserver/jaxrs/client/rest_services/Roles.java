package com.jaspersoft.jasperserver.jaxrs.client.rest_services;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.authority.RolesRequestBuilder;

public class Roles {

    public static RolesRequestBuilder<ClientRole, ClientRole> rolename(String rolename) {
        RolesRequestBuilder<ClientRole, ClientRole> builder =
                new RolesRequestBuilder<ClientRole, ClientRole>(ClientRole.class);
        builder.setPath(rolename);
        return builder;
    }

    public static RolesRequestBuilder<RolesListWrapper, RolesListWrapper> allRoles() {
        return  new RolesRequestBuilder<RolesListWrapper, RolesListWrapper>(RolesListWrapper.class);
    }

}
