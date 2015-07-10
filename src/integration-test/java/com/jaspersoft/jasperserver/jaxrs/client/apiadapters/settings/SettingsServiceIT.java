package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SingleSettingsAdapter.ServerSettingsGroup.GLOBAL_CONFIGURATION;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SingleSettingsAdapter.ServerSettingsGroup.REQUEST;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class SettingsServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private Session session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_0);
        client = new JasperserverRestClient(config);
        session = client.authenticate("superuser", "superuser");
    }

    @Test
    public void should_return_settings_by_request_group() {

        // When
        final Map settings = session
                .settingsService()
                    .settings()
                        .group(REQUEST)
                            .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_return_settings_by_global_configuration_group() {

        // Given
        final int FILE_RESOURCES_TYPES_SIZE = 12; // for sample data in JRS 6.1

        // When
        final Map settings = session
                .settingsService()
                    .settings()
                        .group(GLOBAL_CONFIGURATION)
                            .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());

        final Map<String, Object> allFileResourceTypes =
                (Map<String, Object>) settings.get("allFileResourceTypes");

        assertNotNull(allFileResourceTypes);
        assertSame(allFileResourceTypes.size(), FILE_RESOURCES_TYPES_SIZE);

        assertTrue(allFileResourceTypes.containsKey("font"));
        assertTrue(allFileResourceTypes.containsKey("img"));
        assertTrue(allFileResourceTypes.containsKey("jar"));
        assertTrue(allFileResourceTypes.containsKey("xml"));

        assertTrue(allFileResourceTypes.containsValue("OLAP Schema"));
        assertTrue(allFileResourceTypes.containsValue("Content Resource"));
    }

    @Test
    public void should_return_settings_by_user_specified_string_key_of_group() {

        /**
         * When
         */
        final Map settings = session
                .settingsService()
                    .settings()
                        .group("dateTimeSettings")
                            .getEntity();

        /**
         * Then
         */
        assertNotNull(settings);

        final Map datePicker = (Map) settings.get("datepicker");

        assertTrue(datePicker.containsKey("nextText"));
        assertTrue(datePicker.containsKey("yearSuffix"));
    }

    @AfterMethod
    public void after() {
        config = null;
        client = null;
    }
}