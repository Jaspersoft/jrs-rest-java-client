package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.OrganizationsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import javax.ws.rs.core.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;


/**
 * @author Tetiana Iefimenko
 */
public class OrganizationsServiceIT extends RestClientTestUtil {

    private ClientTenant organization;


    @BeforeClass
    public void before() {
        initClient();
        initSession();
        organization = new ClientTenant();
        organization.setAlias("test_org");
        organization.setId("test_Id");
    }

    @Test
    public void should_create_organization() {

        OperationResult<ClientTenant> operationResult = session
                .organizationsService()
                .organization(organization)
                .create();

        ClientTenant entity = operationResult.getEntity();

        assertEquals(Response.Status.CREATED.getStatusCode(), operationResult.getResponse().getStatus());
        assertNotNull(entity);
    }

    @Test(dependsOnMethods = "should_create_organization")
    public void should_return_organization() {

        OperationResult<ClientTenant> operationResult = session
                .organizationsService()
                .organization(organization)
                .get();

        ClientTenant entity = operationResult.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), operationResult.getResponse().getStatus());
        assertNotNull(entity);
    }

    @Test(dependsOnMethods = "should_return_organization")
    public void should_return_organizations() {

        OperationResult<OrganizationsListWrapper> operationResult = session
                .organizationsService()
                .allOrganizations()
                .get();

        OrganizationsListWrapper entity = operationResult.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), operationResult.getResponse().getStatus());
        assertNotNull(entity);
        assertEquals(true, entity.getList().size() > 1);
    }


    @Test(dependsOnMethods = "should_return_organizations")
    public void should_update_organization() {
        OperationResult<ClientTenant> operationResult = session
                .organizationsService()
                .organization(organization)
                .createOrUpdate(new ClientTenant(organization).setAlias("new_alias"));

        ClientTenant entity = operationResult.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), operationResult.getResponse().getStatus());
        assertNotNull(entity);
        assertEquals("new_alias", entity.getAlias());
    }


    @Test(dependsOnMethods = "should_update_organization")
    public void should_delete_organization() {

        OperationResult<ClientTenant> operationResult = session
                .organizationsService()
                .organization(organization)
                .delete();

        ClientTenant entity = operationResult.getEntity();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), operationResult.getResponse().getStatus());
        assertNull(entity);
    }

    @AfterClass
    public void after() {
        session.logout();
        session = null;
    }

}
