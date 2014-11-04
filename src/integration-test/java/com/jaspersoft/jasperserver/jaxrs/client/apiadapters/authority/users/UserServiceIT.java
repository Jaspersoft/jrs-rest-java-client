package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType.YML;

/**
 * @author Alexander Krasnyanskiy
 */
public class UserServiceIT {

    private static final String USERNAME = "superuser";
    private static final String PASSWORD = "superuser";

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeClass(groups = {"pro.version"})
    public void before() throws IOException, URISyntaxException, InterruptedException {
        config = new RestClientConfiguration(YML);
        client = new JasperserverRestClient(config);
        session = client.authenticate(USERNAME, PASSWORD);
    }

    @Test
    public void should_persist_user() {

    }

    @AfterClass(groups = {"pro.version"})
    public void after() throws InterruptedException {
        session = null;
        client = null;
        config = null;
    }
}