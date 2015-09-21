package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Tetiana Iefimenko
 */
public class RestClientConfigurationIT {

    private RestClientConfiguration config;

    @Test
    public void should_load_configuration_from_file() {

        // When

        config = RestClientConfiguration.loadConfiguration("test_config.properties");
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
        assertFalse(config.getRestrictedHttpMethods());
        assertFalse(config.getRestrictedHttpMethods());
        assertEquals(config.getJasperReportsServerUrl(), "http://localhost:4444/jasperserver-pro");
        assertEquals(config.getJrsVersion(), JRSVersion.v6_1_0);
        assertEquals(config.getAuthenticationType(), AuthenticationType.SPRING);
    }


    @Test
    public void should_return_empty_configuration() {
        // When
            config = RestClientConfiguration.loadConfiguration("wrong_path");
        //Then
        assertEquals(config.getJasperReportsServerUrl(), null);
    }

   @AfterMethod
    public void afterTest() {
        config = null;
    }

}
