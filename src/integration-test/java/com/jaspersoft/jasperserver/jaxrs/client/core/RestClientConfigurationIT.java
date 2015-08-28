package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.*;

/**
 * @author Tetiana Iefimenko
 */
public class RestClientConfigurationIT {

    private RestClientConfiguration config;

    @Test
    public void should_load_configuration_from_file() {

        // When

        config = RestClientConfiguration.loadConfiguration("config.properties");
        // Then
        assertNotNull(config);
        assertNotNull(config.getJasperReportsServerUrl());
        assertNotNull(config.getJrsVersion());
        assertNotNull(config.getAuthenticationType());
        assertNotNull(config.getRestrictedHttpMethods());
        assertNotNull(config.getLogHttp());
        assertNotNull(config.getLogHttpEntity());
        assertNotNull(config.getAcceptMimeType());
        assertNotNull(config.getContentMimeType());
        assertTrue(config.getLogHttp());
        assertTrue(config.getLogHttpEntity());
        assertFalse(config.getRestrictedHttpMethods());
        assertEquals(config.getJrsVersion(), JRSVersion.v6_0_0);
        assertEquals(config.getAuthenticationType(), AuthenticationType.REST);
    }


    @Test
    public void should_throw_nullPointerException() {
        try {
            config = RestClientConfiguration.loadConfiguration("wrong_path");
        } catch (NullPointerException ex) {
            assertNotNull(ex);
            assertSame(ex, new NullPointerException());
        }
    }

   @AfterMethod
    public void afterTest() {
        config = null;
    }

}
