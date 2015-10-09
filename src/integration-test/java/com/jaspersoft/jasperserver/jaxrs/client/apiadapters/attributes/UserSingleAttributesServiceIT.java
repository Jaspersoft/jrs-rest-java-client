package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class UserSingleAttributesServiceIT extends RestClientTestUtil {
    private HypermediaAttribute attribute;
    private String orgName;
    private String userName;

    @BeforeClass
    public void before() {
        attribute = new HypermediaAttribute();
        attribute.setName("test_attribute");
        attribute.setValue("test_value");
        orgName = "myOrg1";
        userName = "jasperadmin";
        initClient();
        initSession();
    }

    @Test
    public void should_create_single_attribute() {

        HypermediaAttribute entity = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .attribute(attribute.getName())
                .createOrUpdate(attribute)
                .getEntity();

        assertNotNull(entity);
    }

    @Test(dependsOnMethods = "should_create_single_attribute")
    public void should_return_attribute() {
        HypermediaAttribute entity = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .attribute(attribute.getName())
                .get()
                .getEntity();

        assertEquals(entity.getValue(), attribute.getValue());
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_return_attribute_with_permissions() {
        HypermediaAttribute entity = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .attribute(attribute.getName())
                .setIncludePermissions(true)
                .get()
                .getEntity();

        assertEquals(entity.getValue(), attribute.getValue());
        assertNotNull(entity.getEmbedded());
    }

    @Test(dependsOnMethods = "should_return_attribute_with_permissions")
    public void should_delete_attribute() {
        OperationResult<HypermediaAttribute> operationResult = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(new ClientUser().setUsername(userName))
                .attribute(attribute.getName())
                .delete();

        assertNotNull(operationResult);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), operationResult.getResponse().getStatus());
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}