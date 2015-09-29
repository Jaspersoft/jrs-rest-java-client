package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
                new ClientUserAttribute().setName("number_of_employees").setValue("1000+"),
                new ClientUserAttribute().setName("number_of_units").setValue("29"),
                new ClientUserAttribute().setName("country_code").setValue("FR")
                ));

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
