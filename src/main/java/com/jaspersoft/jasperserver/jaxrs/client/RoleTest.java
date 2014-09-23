package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alexander Krasnyanskiy
 */
public class RoleTest {
    public static void main(String[] args) {
        RestClientConfiguration config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(config);
        Session session = client.authenticate("superuser", "superuser");

        session.rolesService().organization("MyOrg").roleName("").createOrUpdate(null);

    }
}
