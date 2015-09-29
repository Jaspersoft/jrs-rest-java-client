package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttributesListWrapper;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class ServerAttributesServiceIT extends RestClientTestUtil {


    @BeforeClass
    public void before() {
        initClient();
        initSession();
    }

    @Test
    public void should_create_attributes() {
        ServerAttributesListWrapper serverAttributes = new ServerAttributesListWrapper();
        serverAttributes.setAttributes(asList(
                new ClientUserAttribute().setName("max_threads").setValue("512"),
                new ClientUserAttribute().setName("admin_cell_phone").setValue("03")));

        OperationResult<ServerAttributesListWrapper> attributes = session
                .serverAttributesService()
                .attributes()
                .createOrUpdate(serverAttributes);

        assertNotNull(attributes);

    }

    @Test(dependsOnMethods = "should_create_attributes")
    public void should_return_server_attributes() {
        List<ClientUserAttribute> attributes = session
                .serverAttributesService()
                .attributes()
                .get()
                .getEntity()
                .getAttributes();

        Assert.assertTrue(attributes.size() >= 2);
    }

    @Test(dependsOnMethods = "should_return_server_attributes")
    public void should_return_specified_server_attributes() {
        List<ClientUserAttribute> attributes = session
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

        Assert.assertNull(entity);
    }

    @Test(dependsOnMethods = "should_delete_server_attributes")
    public void should_create_single_attribute() {
        ClientUserAttribute attribute = new ClientUserAttribute();
        attribute.setName("latency");
        attribute.setValue("5700");

        ClientUserAttribute entity = session
                .serverAttributesService()
                .attribute()
                .createOrUpdate(attribute)
                .getEntity();
        assertNotNull(entity);
    }

    @Test(dependsOnMethods = "should_create_single_attribute")
    public void should_return_attribute() {
        ClientUserAttribute entity = session
                .serverAttributesService()
                .attribute("latency")
                .get()
                .getEntity();
        assertEquals(entity.getValue(), "5700");
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_delete_attribute() {
        OperationResult<ClientUserAttribute> entity = session
                .serverAttributesService()
                .attribute("latency")
                .delete();

        assertNotNull(entity);
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}