package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttributesListWrapper;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ServerAttributesServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeClass
    public void before() {
        config = RestClientConfiguration.loadConfiguration("test_config.properties");
        client = new JasperserverRestClient(config);
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    public void should_create_attributes() {
        ServerAttributesListWrapper serverAttributes = new ServerAttributesListWrapper();
        serverAttributes.setAttributes(asList(
                new ServerAttribute("max_threads", "512"),
                new ServerAttribute("admin_cell_phone", "03")));

        OperationResult<ServerAttributesListWrapper> attributes = session
                .serverAttributesService()
                .attributes()
                .createOrUpdate(serverAttributes);

        Assert.assertTrue(instanceOf(NullEntityOperationResult.class).matches(attributes));
    }

    @Test(dependsOnMethods = "should_create_attributes")
    public void should_return_server_attributes() {
        List<ServerAttribute> attributes = session
                .serverAttributesService()
                .attributes()
                .get()
                .getEntity()
                .getAttributes();

        Assert.assertTrue(attributes.size() >= 2);
    }

    @Test(dependsOnMethods = "should_return_server_attributes")
    public void should_return_specified_server_attributes() {
        List<ServerAttribute> attributes = session
                .serverAttributesService()
                .attributes(asList("max_threads", "admin_cell_phone"))
                .get()
                .getEntity()
                .getAttributes();

        Assert.assertTrue(attributes.size() >= 2);
    }

    @Test(dependsOnMethods = "should_return_specified_server_attributes")
    public void should_delete_specified_server_attributes() {
        OperationResult<ServerAttributesListWrapper> entity = session
                .serverAttributesService()
                .attributes(asList("max_threads"))
                .delete();

        Assert.assertTrue(instanceOf(NullEntityOperationResult.class).matches(entity));
    }

    @Test(dependsOnMethods = "should_delete_specified_server_attributes")
    public void should_delete_server_attributes() {
        ServerAttributesListWrapper entity = session
                .serverAttributesService()
                .attributes()
                .delete()
                .getEntity();

        Assert.assertNull(null);
    }

    @Test(dependsOnMethods = "should_delete_server_attributes")
    public void should_create_single_attribute() {
        ServerAttribute attribute = new ServerAttribute();
        attribute.setName("latency");
        attribute.setValue("5700");

        ServerAttribute entity = session
                .serverAttributesService()
                .attribute()
                .createOrUpdate(attribute)
                .getEntity();
        Assert.assertNull(entity);
    }

    @Test(dependsOnMethods = "should_create_single_attribute")
    public void should_return_attribute() {
        ServerAttribute entity = session
                .serverAttributesService()
                .attribute("latency")
                .get()
                .getEntity();
        Assert.assertEquals(entity.getValue(), "5700");
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_delete_attribute() {
        OperationResult<ServerAttribute> entity = session
                .serverAttributesService()
                .attribute("latency")
                .delete();
        Assert.assertTrue(instanceOf(NullEntityOperationResult.class).matches(entity));
    }

    @AfterClass
    public void after() {
        session.logout();
        client = null;
        config = null;
        session = null;
    }
}