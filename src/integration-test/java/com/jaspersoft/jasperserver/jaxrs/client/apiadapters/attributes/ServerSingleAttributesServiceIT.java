package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;


public class ServerSingleAttributesServiceIT extends RestClientTestUtil {
    private String attrName;
    private String attrValue;

    @BeforeClass
    public void before() {
        initClient();
        initSession();
        attrName = "latency";
        attrValue = "5700";
    }

    @Test
    public void should_create_single_attribute() {
        HypermediaAttribute attribute = new HypermediaAttribute();
        attribute.setName(attrName);
        attribute.setValue(attrValue);

        HypermediaAttribute entity = session
                .attributesService()
                .attribute(attribute.getName())
                .createOrUpdate(attribute)
                .getEntity();
        assertNotNull(entity);
    }

    @Test(dependsOnMethods = "should_create_single_attribute")
    public void should_return_attribute() {
        HypermediaAttribute entity = session
                .attributesService()
                .attribute(attrName)
                .get()
                .getEntity();

        assertEquals(entity.getValue(), attrValue);
        assertNull(entity.getEmbedded());
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_return_attribute_with_permissions() {
        HypermediaAttribute entity = session
                .attributesService()
                .attribute(attrName)
                .setIncludePermissions(true)
                .get()
                .getEntity();

        assertEquals(entity.getValue(), attrValue);
        assertNotNull(entity.getEmbedded());
    }

    @Test(dependsOnMethods = "should_return_attribute_with_permissions")
    public void should_delete_attribute() {
        OperationResult<HypermediaAttribute> entity = session
                .attributesService()
                .attribute(attrName)
                .delete();

        assertNotNull(entity);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), entity.getResponse().getStatus());
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}