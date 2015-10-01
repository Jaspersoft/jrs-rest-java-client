package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
        HypermediaAttributesListWrapper serverAttributes = new HypermediaAttributesListWrapper();
        serverAttributes.setProfileAttributes(asList(
                new HypermediaAttribute(new ClientUserAttribute().setName("max_threads").setValue("512")),
                new HypermediaAttribute(new ClientUserAttribute().setName("admin_cell_phone").setValue("03"))));

        OperationResult<HypermediaAttributesListWrapper> attributes = session
                .serverAttributesService()
                .attributes()
                .createOrUpdate(serverAttributes);

        assertNotNull(attributes);

    }

    @Test(dependsOnMethods = "should_create_attributes")
    public void should_return_server_attributes() {
        List<HypermediaAttribute> attributes = session
                .serverAttributesService()
                .attributes()
                .get()
                .getEntity()
                .getProfileAttributes();

        Assert.assertTrue(attributes.size() >= 2);
    }

    @Test(dependsOnMethods = "should_return_server_attributes")
    public void should_return_specified_server_attributes() {
        List<HypermediaAttribute> attributes = session
                .serverAttributesService()
                .attributes(asList("max_threads", "admin_cell_phone"))
                .setIncludePermissions(true)                        // new
                .get()
                .getEntity()
                .getProfileAttributes();

        Assert.assertTrue(attributes.size() >= 2);

    }

    @Test(dependsOnMethods = "should_return_specified_server_attributes")
    public void should_delete_specified_server_attributes() {
        OperationResult<HypermediaAttributesListWrapper> entity = session
                .serverAttributesService()
                .attributes("max_threads")
                .delete();

        Assert.assertTrue(instanceOf(NullEntityOperationResult.class).matches(entity));
    }

    @Test(dependsOnMethods = "should_delete_specified_server_attributes")
    public void should_delete_server_attributes() {
        HypermediaAttributesListWrapper entity = session
                .serverAttributesService()
                .attributes()
                .delete()
                .getEntity();

        Assert.assertNull(entity);
    }

    @Test(dependsOnMethods = "should_delete_server_attributes")
    public void should_create_single_attribute() {
        HypermediaAttribute attribute = new HypermediaAttribute();
        attribute.setName("latency");
        attribute.setValue("5700");

        HypermediaAttribute entity = session
                .serverAttributesService()
                .attribute()
                .createOrUpdate(attribute)
                .getEntity();
        assertNotNull(entity);
    }

    @Test(dependsOnMethods = "should_create_single_attribute")
    public void should_return_attribute() {
        HypermediaAttribute entity = session
                .serverAttributesService()
                .attribute("latency")
                .setIncludePermissions(true)
                .get()
                .getEntity();

        assertEquals(entity.getValue(), "5700");
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_delete_attribute() {
        OperationResult<HypermediaAttribute> entity = session
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