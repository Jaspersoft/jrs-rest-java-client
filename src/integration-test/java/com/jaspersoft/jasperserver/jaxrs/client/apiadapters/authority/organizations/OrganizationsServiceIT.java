package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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

        HypermediaAttributesListWrapper attributes = new HypermediaAttributesListWrapper();
        attributes.setProfileAttributes(Arrays.asList(
                        new HypermediaAttribute(new ClientUserAttribute().setName("number_of_employees").setValue("1000+")),
                        new HypermediaAttribute(new ClientUserAttribute().setName("number_of_units").setValue("29")),
                new HypermediaAttribute(new ClientUserAttribute().setName("country_code").setValue("FR"))
        ));

        OperationResult<HypermediaAttributesListWrapper> retrieved = session
                .organizationsService()
                .organization("myOrg1")
                .attributes()
                .createOrUpdate(attributes);

        assertNotNull(retrieved);

    }

    @Test(dependsOnMethods = "should_create_tenant_attributes")
    public void should_return_tenant_attributes() {


        OperationResult<HypermediaAttributesListWrapper> operationResult = session
                .organizationsService()
                .organization("myOrg1")
                .attributes()
                .get();

        HypermediaAttributesListWrapper tenantAttributesListWrapper = operationResult.getEntity();
        assertNotNull(operationResult);
        assertNotNull(tenantAttributesListWrapper);

    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }

}
