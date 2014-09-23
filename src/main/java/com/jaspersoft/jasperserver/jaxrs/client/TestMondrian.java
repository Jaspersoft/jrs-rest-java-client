package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestMondrian {
    public static void main(String[] args) throws FileNotFoundException {

        RestClientConfiguration config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(config);
        Session session = client.authenticate("superuser", "superuser");

        InputStream schema = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/" +
                "jrs-rest-java-client/src/main/properties/olap/" +
                "foodmartSchema.xml");

        ClientReference dataSourceDescriptor = new ClientReference()
                .setUri("/public/Samples/Data_Sources/FoodmartDataSourceJNDI");

        ClientFile schemaRef = new ClientFile()
                .setLabel("FoodmartSchemaPostgres2013")
                .setDescription("Some description...")
                .setType(ClientFile.FileType.olapMondrianSchema);

        ClientMondrianConnection mondrianConnection = new ClientMondrianConnection();
        mondrianConnection.setLabel("MondrianConnection");
        mondrianConnection.setDescription("Some description...");
        mondrianConnection.setDataSource(dataSourceDescriptor);

        ClientMondrianConnection connection = session
                .resourcesService()
                    .resource(mondrianConnection)
                        .withMondrianSchema(schema, schemaRef)
                    .createInFolder("my/olap/folder")
                        .entity();

        System.out.println(connection);
    }
}
