package com.jaspersoft.jasperserver.jaxrs.client.authority;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.GetDeleteBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.PostPutBuilder;

import javax.ws.rs.core.Response;

public class Roles {

    private static final String URI = "/roles";

    public static GetDeleteBuilder<ClientRole> rolename(String rolename) {
        GetDeleteBuilder<ClientRole> builder = new GetDeleteBuilder<ClientRole>(ClientRole.class);
        builder.setPath(URI).setPath(rolename);
        return builder;
    }

    public static GetDeleteBuilder<RolesListWrapper> allRoles() {
        GetDeleteBuilder<RolesListWrapper> builder = new GetDeleteBuilder<RolesListWrapper>(RolesListWrapper.class);
        builder.setPath(URI);
        return builder;
    }

    public static Response addRole(ClientRole role) {
        PostPutBuilder builder = new PostPutBuilder();
        builder.setPath(URI).setPath(role.getName());
        return builder.put(role);
    }

}
