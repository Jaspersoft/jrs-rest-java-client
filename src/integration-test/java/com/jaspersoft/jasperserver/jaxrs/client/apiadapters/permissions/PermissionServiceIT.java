package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class PermissionServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeMethod
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_return_all_permissions_for_a_given_resource() {
        List<RepositoryPermission> permissions = session.permissionsService()
                .resource("/public")
                .get()
                .entity()
                .getPermissions();
        assertNotNull(permissions);
    }

    @AfterMethod
    public void after() {
        session.logout();
    }
}
