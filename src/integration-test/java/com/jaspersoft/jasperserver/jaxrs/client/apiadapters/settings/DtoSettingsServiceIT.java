package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings.SingleSettingsAdapter.ServerSettingsGroup.*;
import static junit.framework.Assert.*;

public class DtoSettingsServiceIT {

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
    public void should_return_AwsSettings_object_by_class() {

        // When
        final AwsSettings settings = session
                .settingsService()
                .settings().group(AWS_SETTINGS, AwsSettings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
    }

    @Test
    public void should_return_requestSetiings_object_by_class() {

        // When
        final RequestSettings settings = session
                .settingsService()
                .settings().group(REQUEST, RequestSettings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertEquals(settings.getMaxInactiveInterval().intValue(), 1200);
    }


    @Test
    public void should_return_dataSourcePatternsSettings_object_by_class() {

        // When
        final DataSourcePatternsSettings settings = session
                .settingsService()
                .settings().group(DATA_SOURCE_PATTERNS, DataSourcePatternsSettings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
    }

    @Test
    public void should_return_globalConfigurationSettings_object_by_class() {

        // When
        final GlobalConfigurationSettings settings = session
                .settingsService()
                .settings()
                .group(GLOBAL_CONFIGURATION, GlobalConfigurationSettings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
    }

    @Test
    public void should_return_decimalFormatSymbolsSettings_object_by_class() {

        // When
        final DecimalFormatSymbolsSettings settings = session
                .settingsService()
                .settings()
                .group(DECIMAL_FORMAT_SYMBOLS, DecimalFormatSymbolsSettings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
    }

    @Test
    public void should_return_dashBoardSettings_object_by_class() {

        // When
        final DashboardSettings settings = session
                .settingsService()
                .settings()
                .group(DASHBOARD_SETTINGS, DashboardSettings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
    }

    @Test
    public void should_return_inputControlSettings_object_by_class() {

        // When
        final InputControlsSetiings settings = session
                .settingsService()
                .settings()
                .group(INPUT_CONTROL, InputControlsSetiings.class)
                .getEntity();

        // Then
        assertNotNull(settings);
    }

    @Test
    public void should_return_timeZonesSettings_object_by_class() {

        // When
        final GenericType<List<UserTimeZone>> settings = session
                .settingsService()
                .settings()
                .group(USER_TIME_ZONES, new GenericType<List<UserTimeZone>>(){}.getClass())
                .getEntity();

        // Then
        assertNotNull(settings);
    }


    @AfterMethod
    public void after() {
        config = null;
        client = null;
    }
}