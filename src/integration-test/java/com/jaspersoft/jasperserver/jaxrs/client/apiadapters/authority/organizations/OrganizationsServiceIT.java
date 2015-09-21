package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ClientTenantAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.TenantAttributesListWrapper;
import java.util.Arrays;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author Tetiana Iefimenko
 */
public class OrganizationsServiceIT {


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
    public void should_create_tenant_attributes() {

        TenantAttributesListWrapper attributes = new TenantAttributesListWrapper();
        attributes.setAttributes(Arrays.asList(
                new ClientTenantAttribute("number_of_employees", "1000+"),
                new ClientTenantAttribute("number_of_units", "29"),
                new ClientTenantAttribute("country_code", "FR")));

        OperationResult<TenantAttributesListWrapper> retrieved = session
                .organizationsService()
                .organization("myOrg1")
                .attributes()
                .createOrUpdate(attributes);

        assertNotNull(retrieved);

    }

    @Test(dependsOnMethods = "should_create_tenant_attributes")
    public void should_return_tenant_attributes() {


        OperationResult<TenantAttributesListWrapper> result3 = session
                .organizationsService()
                .organization("myOrg1")
                .attributes()
                .get();

        TenantAttributesListWrapper result = result3.getEntity();
        assertNotNull(result3);
        assertNotNull(result);

    }

    @AfterClass
    public void after() {
        session.logout();
    }

}
