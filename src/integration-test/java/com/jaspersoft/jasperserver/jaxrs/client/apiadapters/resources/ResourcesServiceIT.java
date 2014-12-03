package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;

import static org.testng.Assert.assertNotNull;

/**
 * @author Alexander Krasnyanskiy
 */
public class ResourcesServiceIT extends ClientConfigurationFactory{

    private Session session;

    @BeforeMethod
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_retrieve_binary_resource() {
        InputStream resource = session.resourcesService()
                .resource("/themes/default/pages.css")
                .downloadBinary()
                .entity();
        assertNotNull(resource);
    }

    @AfterMethod
    public void after() {
        session.logout();
    }
}
