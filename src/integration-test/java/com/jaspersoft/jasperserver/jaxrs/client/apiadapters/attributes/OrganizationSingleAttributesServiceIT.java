package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributeEmbeddedContainer;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;


public class OrganizationSingleAttributesServiceIT extends RestClientTestUtil {

    private HypermediaAttribute attribute;
    private String orgName;

    @BeforeClass
    public void before() {
        attribute = new HypermediaAttribute();
        attribute.setName("number_of_employees_test");
        attribute.setValue("1000+");
        orgName = "myOrg1";
        initClient();
        initSession();
    }

    @Test
    public void should_create_organization_attribute() {
        OperationResult<HypermediaAttribute> retrieved = session
                .attributesService()
                .forOrganization(orgName)
                .attribute(attribute.getName())
                .createOrUpdate(attribute);

        assertNotNull(retrieved);
        assertEquals(retrieved.getEntity().getName(), attribute.getName());

    }

    @Test(dependsOnMethods = "should_create_organization_attribute")
    public void should_return_attribute() {
        HypermediaAttribute entity = session
                .attributesService()
                .forOrganization(orgName)
                .attribute(attribute.getName())
                .get()
                .getEntity();

        assertEquals(entity.getValue(), attribute.getValue());
        assertNull(entity.getEmbedded());
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_return_attribute_with_permissions() {
        HypermediaAttribute entity = session
                .attributesService()
                .forOrganization(orgName)
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
                .attribute(attribute.getName())
                .delete();

        assertNotNull(operationResult);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), operationResult.getResponse().getStatus());
    }

    @Test
    public void should_create_attribute_with_permission() {
        HypermediaAttribute newAttribute = new HypermediaAttribute(attribute);

        newAttribute.setName("OrganizationAttributeTest");
        newAttribute.setValue("Organization attribute test value");
        newAttribute.setDescription("Organization attribute description");
        newAttribute.setPermissionMask(1);
        newAttribute.setSecure(false);
        newAttribute.setInherited(false);
        newAttribute.setHolder("tenant:/organization_1");
        RepositoryPermission permission = new RepositoryPermission().setUri("attr:/organizations/organization_1/attributes/OrganizationAttributeTest").setRecipient("role:/ROLE_ADMINISTRATOR").setMask(32);
        HypermediaAttributeEmbeddedContainer container = new HypermediaAttributeEmbeddedContainer();
        container.setRepositoryPermissions(asList(permission));
        newAttribute.setEmbedded(container);

        HypermediaAttribute entity = session
                .attributesService()
                .forOrganization("organization_1")
                .attribute("OrganizationAttributeTest")
                .setIncludePermissions(true)
                .createOrUpdate(newAttribute)
                .getEntity();

        assertEquals(entity.getValue(), newAttribute.getValue());
        assertNotNull(entity.getEmbedded());

        OperationResult<HypermediaAttribute> operationResult = session
                .attributesService()
                .forOrganization("organization_1")
                .attribute("OrganizationAttributeTest")
                .delete();
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }
}