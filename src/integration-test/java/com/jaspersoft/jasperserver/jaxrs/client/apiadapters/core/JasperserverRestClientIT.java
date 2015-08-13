package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author Tetiana Iefimenko
 */
public class JasperserverRestClientIT {


    private RestClientConfiguration config;
    private JasperserverRestClient client;
    Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_0);
        client = new JasperserverRestClient(config);
    }

    @Test
    public void should_return_session_via_j_sucurity_check() {
        config.setAuthenticationType(AuthenticationType.SPRING);
        session = client.authenticate("superuser", "superuser");
        assertNotNull(session);
        assertNotNull(session.getStorage().getSessionId());
    }

    @Test
    public void should_return_session_via_rest_login() {
        session = client.authenticate("superuser", "superuser");
        assertNotNull(session);
        assertNotNull(session.getStorage().getSessionId());
    }

    @AfterMethod
    public  void  after() {
        session.logout();
    }
}
