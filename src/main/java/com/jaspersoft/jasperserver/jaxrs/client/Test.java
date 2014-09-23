package com.jaspersoft.jasperserver.jaxrs.client;

import com.jaspersoft.jasperserver.dto.resources.ClientBundle;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        RestClientConfiguration config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        JasperserverRestClient client = new JasperserverRestClient(config);
        Session session = client.authenticate("superuser", "superuser");

        InputStream securityFile = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrs-rest-java-client/src/main/properties/supermartDomain_domain_security.xml");
        InputStream schemaFile = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrs-rest-java-client/src/main/properties/schema.xml");
        InputStream defaultBundle = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrs-rest-java-client/src/main/properties/supermart_domain.properties");
        InputStream enUSBundle = new FileInputStream("/Users/alexkrasnyaskiy/IdeaProjects/jrs-rest-java-client/src/main/properties/supermart_domain_en_US.properties");

        ClientReference dataSourceDescriptor = new ClientReference()
                .setUri("/public/Samples/Data_Sources/FoodmartDataSourceJNDI");

        ClientFile schemaDescriptor = new ClientFile()
                .setLabel("supermartDomain_schema")
                .setDescription("supermartDomain_schema")
                .setType(ClientFile.FileType.xml);

        ClientFile securityFileDescriptor = new ClientFile()
                .setLabel("supermartDomain_domain_security")
                .setDescription("supermartDomain_domain_security")
                .setType(ClientFile.FileType.xml);

        ClientBundle defaultBundleDescriptor = new ClientBundle()
                .setLocale("")
                .setFile(new ClientFile().setLabel("supermart_domain.properties").setType(ClientFile.FileType.prop));

        ClientBundle enUsBundleDescriptor = new ClientBundle()
                .setLocale("en_US")
                .setFile(new ClientFile().setLabel("supermart_domain_en_US.properties").setType(ClientFile.FileType.prop));

        ClientSemanticLayerDataSource domain = new ClientSemanticLayerDataSource();
        domain.setLabel("Label");
        domain.setDescription("---");
        domain.setDataSource(dataSourceDescriptor);


        ClientSemanticLayerDataSource domainEntity = session
            .resourcesService()
                .resource(domain)
                    .withBundle(defaultBundle, defaultBundleDescriptor)
                    .withBundle(enUSBundle, enUsBundleDescriptor)
                    .withSecurityFile(securityFile, securityFileDescriptor)
                    .withSchema(schemaFile, schemaDescriptor)
                .createInFolder("/my/new/folder/")
                    .getEntity();
    }
}
