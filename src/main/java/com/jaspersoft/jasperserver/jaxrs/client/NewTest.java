package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alexander Krasnyanskiy
 */
public class NewTest {
    public static void main(String[] args) {

        RestClientConfiguration config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(config);
        Session session = client.authenticate("superuser", "superuser");

        ClientSemanticLayerDataSource domain = new ClientSemanticLayerDataSource();

        ClientSemanticLayerDataSource result = session
                .resourcesService()
                    .resource(domain)
                        .withUri("/my/new/folder/Label")
                    .get()
                        .entity();

        System.out.println(result);
    }
}
