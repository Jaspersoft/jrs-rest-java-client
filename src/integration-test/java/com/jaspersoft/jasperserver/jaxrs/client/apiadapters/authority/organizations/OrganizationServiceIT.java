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
        session = getClientSession(ConfigType.YML);
    }


    @Test
    public void should_create_Organization() {
        ClientTenant coolOrg = new ClientTenant()
                .setAlias("MyCoolOrg")
                .setId("CoolOrg")
                .setTenantName("MyCoolOrg");
        ClientTenant created = session.organizationsService()
                .organization("MyCoolOrg")
                .create(coolOrg)
                .entity();
        assertNotNull(created);
    }


    @Test(dependsOnMethods = "should_create_Organization")
    public void should_retrieve_Organization_by_id() {
        ClientTenant org = session.organizationsService()
                .organization("MyCoolOrg")
                .get()
                .entity();
        assertNotNull(org);
    }


    @Test(dependsOnMethods = "should_create_Organization")
    public void should_delete_Organization() {
        Object org = session.organizationsService()
                .organization("MyCoolOrg")
                .delete()
                .entity();
        assertNull(org);
    }


    @AfterClass
    public void after() {
        session.logout();
    }
}
