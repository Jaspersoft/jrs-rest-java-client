package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.RestClientTestUtil;
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
public class RestClientConfigurationIT extends RestClientTestUtil {


    @Test
    public void should_load_configuration_from_file() {

        // When
        configuration = RestClientConfiguration.loadConfiguration("test_config.properties");
        // Then
        assertNotNull(configuration);
        assertNotNull(configuration.getJasperReportsServerUrl());
        assertNotNull(configuration.getJrsVersion());
        assertNotNull(configuration.getAuthenticationType());
        assertNotNull(configuration.getRestrictedHttpMethods());
        assertNotNull(configuration.getLogHttp());
        assertNotNull(configuration.getLogHttpEntity());
        assertNotNull(configuration.getAcceptMimeType());
        assertNotNull(configuration.getContentMimeType());
        assertTrue(configuration.getLogHttp());
        assertTrue(configuration.getLogHttpEntity());
        assertFalse(configuration.getRestrictedHttpMethods());
        assertFalse(configuration.getRestrictedHttpMethods());
        assertFalse(configuration.getRestrictedHttpMethods());
        assertEquals(configuration.getJasperReportsServerUrl(), "http://localhost:4444/jasperserver-pro");
        assertEquals(configuration.getJrsVersion(), JRSVersion.v6_1_0);
        assertEquals(configuration.getAuthenticationType(), AuthenticationType.SPRING);
    }


    @Test
    public void should_return_empty_configuration() {
        // When
            configuration = RestClientConfiguration.loadConfiguration("wrong_path");
        //Then
        assertEquals(configuration.getJasperReportsServerUrl(), null);
    }

   @AfterMethod
    public void afterTest() {
        configuration = null;
    }

}
