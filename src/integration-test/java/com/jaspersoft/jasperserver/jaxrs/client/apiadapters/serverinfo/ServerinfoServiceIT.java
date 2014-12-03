package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverinfo;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Alexander Krasnyanskiy
 */
public class ServerinfoServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeMethod
    public void before() {
        session = getClientSession(ConfigType.YML);
    }

    @Test
    public void should_return_proper_JRS_version() {
        String version = session.serverInfoService().details().entity().getVersion();
        Assert.assertEquals(version, "5.6.0");
    }

    @AfterMethod
    public void after() {
        session.logout();
    }
}
