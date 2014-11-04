package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.OrganizationsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationParameter.CREATE_DEFAULT_USERS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationParameter.Q;
import static com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType.YML;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander Krasnyanskiy
 */
public class OrganizationServiceIT {

    private static final String USERNAME = "superuser";
    private static final String PASSWORD = "superuser";

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;
    private ClientTenant created;
    private ClientTenant trick;

    @BeforeClass(groups = {"pro.version"})
    public void before() throws IOException, URISyntaxException, InterruptedException {
        config = new RestClientConfiguration(YML);
        client = new JasperserverRestClient(config);
        session = client.authenticate(USERNAME, PASSWORD);

        /*
        URI uri = getClass().getResource("/data/jrs-rest-client-integration-test-data.zip").toURI();
        File file = new File(uri);

        StateDto state = session.importService().newTask().parameter(ImportParameter.UPDATE, true).create(file).getEntity();
        while (!state.getPhase().equals("finished")) {
            sleep(500);
            state = session.importService().task(state.getId()).state().getEntity();
        }
        */
    }

    @Test(groups = {"pro.version"})
    public void should_create_simple_organization() throws InterruptedException {
        ClientTenant tenant = new ClientTenant().setAlias("MyCoolOrg").setId("MyCoolOrg");
        created = session.organizationsService()
                .organization("/MyCoolOrg")
                .parameter(CREATE_DEFAULT_USERS, false)
                .create(tenant)
                .entity();

        assertNotNull(created);
        assertEquals(created.getAlias(), "MyCoolOrg");
    }

    @Test(groups = {"pro.version"}, dependsOnMethods = "should_create_simple_organization")
    public void should_return_all_available_organizations_and_check_if_created_organization_is_exist_among_these_organizations() throws InterruptedException {
        List<ClientTenant> organizations = session.organizationsService()
                .organizations()
                .get()
                .entity()
                .getList();

        boolean founded = false;

        // the key here is unique id of Organization
        for (ClientTenant organization : organizations) {
            if (organization.getId().equals(created.getId())) {
                founded = true;
            }
        }

        assertTrue(founded);
    }

    @Test(groups = {"pro.version"})
    public void should_find_organization_only_by_fragment_of_id() {
        trick = new ClientTenant().setTenantName("the_biggest").setAlias("empire").setId("in_history");
        session.organizationsService().organization("/in_history").create(trick);

        OrganizationsListWrapper organizations = session.organizationsService()
                .organizations()
                .parameter(Q, "story")
                .get()
                .entity();

        boolean founded = false;

        for (ClientTenant tenant : organizations.getList()) {
            if (tenant.getId().equals("in_history")) {
                founded = true;
                break;
            }
        }

        assertTrue(founded);
    }

    @AfterClass(groups = {"pro.version"})
    public void after() throws InterruptedException {
        /*
        session.resourcesService().resource("/integrationTestFolder").delete();
        */
        session.organizationsService().organization("/MyCoolOrg").delete();
        session.organizationsService().organization("/in_history").delete();
        session = null;
        client = null;
        config = null;
        trick = null;
        created = null;
    }
}
