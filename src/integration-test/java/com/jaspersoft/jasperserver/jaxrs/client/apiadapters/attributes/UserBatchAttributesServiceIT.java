package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.NullEntityOperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.List;
import javax.ws.rs.core.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class UserBatchAttributesServiceIT extends RestClientTestUtil {

    private HypermediaAttributesListWrapper serverAttributes;
    private String orgName;
    private String userName;

    @BeforeClass
    public void before() {
        serverAttributes = new HypermediaAttributesListWrapper();
        serverAttributes.setProfileAttributes(asList(
                new HypermediaAttribute(new ClientUserAttribute().setName("test_attribute_1").setValue("test_value_1")),
                new HypermediaAttribute(new ClientUserAttribute().setName("test_attribute_2").setValue("test_value_2"))));
        orgName = "myOrg1";
        userName = "jasperadmin";

        initClient();
        initSession();
    }

    @Test
    public void should_create_attributes() {

        OperationResult<HypermediaAttributesListWrapper> attributes = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .allAttributes()
                .createOrUpdate(serverAttributes);

        assertNotNull(attributes);
        assertEquals(javax.ws.rs.core.Response.Status.OK.getStatusCode(), attributes.getResponse().getStatus());

    }

    @Test(dependsOnMethods = "should_create_attributes")
    public void should_return_server_attributes() {
        List<HypermediaAttribute> attributes = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .allAttributes()
                .get()
                .getEntity()
                .getProfileAttributes();

        assertTrue(attributes.size() >= 2);

    }

    @Test(dependsOnMethods = "should_create_attributes")
    public void should_return_server_attributes_with_permissions() {
        List<HypermediaAttribute> attributes = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .allAttributes()
                .setIncludePermissions(true)
                .get()
                .getEntity()
                .getProfileAttributes();

        assertTrue(attributes.size() >= 2);
        assertTrue(attributes.get(0).getEmbedded() != null);
    }

    @Test(dependsOnMethods = "should_return_server_attributes_with_permissions")
    public void should_return_specified_server_attributes() {
        List<HypermediaAttribute> attributes = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .attributes(asList(serverAttributes.getProfileAttributes().get(0).getName(),
                        serverAttributes.getProfileAttributes().get(1).getName()))
                .get()
                .getEntity()
                .getProfileAttributes();

        assertTrue(attributes.size() >= 2);
    }


    @Test(dependsOnMethods = "should_return_specified_server_attributes")
    public void should_return_specified_server_attributes_with_permissions() {
        List<HypermediaAttribute> attributes = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .attributes(asList(serverAttributes.getProfileAttributes().get(0).getName(),
                        serverAttributes.getProfileAttributes().get(1).getName()))
                .setIncludePermissions(true)
                .get()
                .getEntity()
                .getProfileAttributes();

        assertTrue(attributes.size() >= 2);
        assertTrue(attributes.get(0).getEmbedded() != null);
    }

    @Test(dependsOnMethods = "should_return_specified_server_attributes_with_permissions")
    public void should_delete_specified_server_attributes() {
        OperationResult<HypermediaAttributesListWrapper> entity = session
                .attributesService()
                .forOrganization(orgName)
                .forUser(userName)
                .attributes(asList(serverAttributes.getProfileAttributes().get(0).getName(),
                        serverAttributes.getProfileAttributes().get(1).getName()))
                .delete();

        assertTrue(instanceOf(NullEntityOperationResult.class).matches(entity));
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), entity.getResponse().getStatus());
    }


    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}