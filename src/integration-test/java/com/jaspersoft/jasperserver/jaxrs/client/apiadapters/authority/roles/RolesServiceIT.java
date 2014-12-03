package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class RolesServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeClass
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_create_a_new_role() {
        ClientRole createdRole = session.rolesService()
                .roleName("ULTIMATE_MANAGER_ROLE")
                .createOrUpdate(new ClientRole().setName("ULTIMATE_MANAGER_ROLE"))
                .entity()
                .getRoleList()
                .get(0);
        assertNotNull(createdRole);
    }


    @Test(dependsOnMethods = "should_create_a_new_role")
    public void should_retrieve_an_existed_role() {
        ClientRole retrieved = session.rolesService()
                .roleName("ULTIMATE_MANAGER_ROLE")
                .get()
                .entity();
        assertNotNull(retrieved);
    }

    @Test(dependsOnMethods = "should_retrieve_an_existed_role", expectedExceptions = ResourceNotFoundException.class)
    public void should_delete_the_role() {
        RolesService roleService = session.rolesService();
        roleService.roleName("ULTIMATE_MANAGER_ROLE").delete();
        ClientRole noEntity = roleService
                .roleName("ULTIMATE_MANAGER_ROLE")
                .get()
                .entity();
    }

    @AfterClass
    public void after() {
        session.logout();
    }
}
