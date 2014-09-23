package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Alexander Krasnyanskiy
 */
public class TestMondrianSecurity {
    public static void main(String[] args) throws FileNotFoundException {

        RestClientConfiguration config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(config);
        Session session = client.authenticate("superuser", "superuser");

        ClientReferenceableFile accessGrantSchema = new ClientFile()
                .setLabel("1_schema")
                .setDescription("1_schema")
                .setType(ClientFile.FileType.accessGrantSchema);

        ClientReferenceableFile olapMondrianSchema = new ClientFile()
                .setLabel("2_schema")
                .setDescription("2_schema")
                .setType(ClientFile.FileType.olapMondrianSchema);

        ClientReference dataSourceDescriptor = new ClientReference()
                .setUri("/public/Samples/Data_Sources/FoodmartDataSourceJNDI");

        InputStream a = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrs-rest-java-client/src/main/properties/olap/accessGrantSchema.xml");
        InputStream b = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrs-rest-java-client/src/main/properties/olap/foodmartSchema.xml");

        ClientSecureMondrianConnection secureMondrianConnection = new ClientSecureMondrianConnection();
        secureMondrianConnection.setDataSource(null);
        secureMondrianConnection.setLabel("MyCool");
        secureMondrianConnection.setDescription("---");
        secureMondrianConnection.setAccessGrants(Arrays.asList(accessGrantSchema));
        secureMondrianConnection.setSchema(olapMondrianSchema);
        secureMondrianConnection.setDataSource(dataSourceDescriptor);


        ClientSecureMondrianConnection entity = session.resourcesService()
                .resource(secureMondrianConnection)
                    .withMondrianSchema(b)
                    .withAccessGrantSchemas(Arrays.asList(a))
                .createInFolder("/here/is/my/folder/")
                    .entity();

        System.out.println(entity);

    }
}
