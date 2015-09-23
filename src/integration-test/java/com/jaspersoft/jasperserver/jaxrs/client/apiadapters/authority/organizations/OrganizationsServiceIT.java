package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
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
public class OrganizationsServiceIT extends RestClientTestUtil {


    @BeforeClass
    public void before() {
        initClient();
        initSession();
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


        OperationResult<TenantAttributesListWrapper> operationResult = session
                .organizationsService()
                .organization("myOrg1")
                .attributes()
                .get();

        TenantAttributesListWrapper tenantAttributesListWrapper= operationResult.getEntity();
        assertNotNull(operationResult);
        assertNotNull(tenantAttributesListWrapper);

    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }

}
