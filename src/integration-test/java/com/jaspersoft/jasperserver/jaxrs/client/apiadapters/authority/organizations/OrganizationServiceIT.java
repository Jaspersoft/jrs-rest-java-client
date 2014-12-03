package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class OrganizationServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeClass
    public void before() {

        /**
         * Prepare JRS server for testing
         */
        try {
            session.organizationsService().organization("MyCoolTestOrg").delete();
        } catch (Exception ignored){
            /*NOP*/
        }
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_create_test_Organization() {

        ClientTenant coolOrg = new ClientTenant()
                .setAlias("MyCoolTestOrg")
                .setId("MyCoolTestOrg")
                .setTenantName("MyCoolTestOrg");

        ClientTenant created = session.organizationsService()
                .organization("MyCoolTestOrg")
                .create(coolOrg)
                .entity();
        assertNotNull(created);
    }

    @Test(dependsOnMethods = "should_create_test_Organization")
    public void should_retrieve_Organization_by_id() {
        ClientTenant org = session.organizationsService()
                .organization("MyCoolTestOrg")
                .get()
                .entity();
        assertNotNull(org);
    }

    @Test(dependsOnMethods = "should_retrieve_Organization_by_id")
    public void should_delete_Organization() {
        Object org = session.organizationsService()
                .organization("MyCoolTestOrg")
                .delete()
                .entity();
        assertNull(org);
    }

    @AfterClass
    public void after() {

        /**
         * Clean up
         */
        try {
            session.organizationsService().organization("MyCoolTestOrg").delete();
        } catch (Exception ignored){
            /*NOP*/
        }

        session.logout();
    }
}
