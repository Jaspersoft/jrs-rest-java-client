package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

public class CreateAndGetResource {


    public static void main(String[] args) {

        RestClientConfiguration configuration = new RestClientConfiguration("http://localhost:8080/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(configuration);
        Session session = client.authenticate("superuser", "superuser");

        ClientFolder folder = new ClientFolder();
        folder
                .setUri("/public/circle_files/kpi")
                .setLabel("Test Folder")
                .setDescription("Test folder description")
                .setPermissionMask(0)
                .setVersion(0);

        ClientResource result = session
                .resourcesService()
                .resource(folder.getUri())
                .createNew(folder)
                .getEntity();
        System.out.println(result);


        ClientResource entity = session
                .resourcesService()
                .resource("/public/circle_files/kpi")
                .details()
                .getEntity();
        System.out.println(entity);
    }
}
