package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.core.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ClientTenantAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.TenantAttributesListWrapper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.testng.Assert.*;

public class OrganizationsServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_1);
        client = new JasperserverRestClient(config);
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    public void should_create_single_attribute() {
        ClientTenantAttribute attribute = new ClientTenantAttribute("industry", "IT");
        OperationResult<ClientTenantAttribute> retrieved = session
                .organizationsService()
                .organization("organization_1")
                .attribute()
                .createOrUpdate(attribute);
        
        Assert.assertNull(retrieved.getEntity());
    }

    @Test(dependsOnMethods = "should_create_single_attribute")
    public void should_return_attribute() {
        OperationResult<ClientTenantAttribute> attributes = session
                .organizationsService()
                .organization("organization_1")
                .attribute("industry")
                .get();

        Assert.assertNotNull(attributes.getEntity());
        Assert.assertEquals(attributes.getEntity().getValue(), "IT");
    }

    @Test(dependsOnMethods = "should_return_attribute")
    public void should_delete_attribute() {
        OperationResult<ClientTenantAttribute> attributes = session
                .organizationsService()
                .organization("organization_1")
                .attribute("industry")
                .delete();

        Assert.assertNull(attributes.getEntity());
    }

    @Test(dependsOnMethods = "should_delete_attribute")
    public void should_create_attributes() {
        TenantAttributesListWrapper attributes = new TenantAttributesListWrapper();
        attributes.setAttributes(Arrays.asList(
                new ClientTenantAttribute("number_of_employees", "1000+"),
                new ClientTenantAttribute("number_of_units", "29"),
                new ClientTenantAttribute("country_code", "FR")));
        TenantAttributesListWrapper retrieved = session
                .organizationsService()
                .organization("organization_1")
                .attributes()
                .createOrUpdate(attributes)
                .getEntity();
        Assert.assertNotNull(retrieved);
    }


    @Test(dependsOnMethods = "should_create_attributes")
    public void should_return_all_attributes() {
        OperationResult<TenantAttributesListWrapper> attributes = session
                .organizationsService()
                .organization("organization_1")
                .attributes()
                .get();
        Assert.assertSame(attributes.getEntity().getAttributes().size(), 3);
    }

    @Test(dependsOnMethods = "should_return_all_attributes")
    public void should_return_specified_attributes() {
        OperationResult<TenantAttributesListWrapper> attributes = session
                .organizationsService()
                .organization("organization_1")
                .attributes(asList("number_of_employees", "number_of_units", "country_code"))
                .get();
        Assert.assertSame(attributes.getEntity().getAttributes().size(), 3);
    }

    @Test(dependsOnMethods = "should_return_specified_attributes")
    public void should_delete_specified_attributes() {
        OperationResult<TenantAttributesListWrapper> attributes = session
                .organizationsService()
                .organization("organization_1")
                .attributes(asList("number_of_employees", "country_code"))
                .delete();
        Assert.assertNull(attributes.getEntity());
    }

    @Test(dependsOnMethods = "should_delete_specified_attributes")
    public void should_delete_all_attributes() {
        OperationResult<TenantAttributesListWrapper> attributes = session
                .organizationsService()
                .organization("organization_1")
                .attributes()
                .delete();
        Assert.assertNull(attributes.getEntity());
    }

    @AfterMethod
    public void after() {
        session.logout();
        client = null;
        config = null;
        session = null;
    }
}