package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 * @author
 * @author Tetiana Iefimenko
 * */
public class SettingsServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private AnonymousSession session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_0_0);
        config.setIsJerseyRequestLogged(true);
        config.setIsJSonEntitieLogged(true);
        client = new JasperserverRestClient(config);
        session = client.getAnonymousSession();
    }

    @Test
    public void should_return_settings_by_request_group() {

        // When
        final Map settings = session
                .settingsService()
                    .settings()
                        .group("request", Map.class)
                            .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_requestSettings_dto() {

        // When
        final RequestSettings settings = session
                .settingsService()
                .settings()
                .ofRequestGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getContextPath());
        assertNotNull(settings.getMaxInactiveInterval());
    }

    @Test
    public void should_return_settings_by_dataSourcePatterns() {

        // When
        final Map settings = session
                .settingsService()
                .settings()
                .group("dataSourcePatterns", Map.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_dataSourcePatternsSettings_dto() {

        // When
        final DataSourcePatternsSettings settings = session
                .settingsService()
                .settings()
                .ofDataSourcePatternsGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getAttributePlaceholderPattern());
        assertNotNull(settings.getDynamicUrlPartPattern());
        assertNotNull(settings.getDbHost());
        assertNotNull(settings.getDbName());
        assertNotNull(settings.getDbPort());
    }

    @Test
    public void should_return_settings_by_awsSettings() {

        // When
        final Map settings = session
                .settingsService()
                .settings()
                .group("awsSettings", Map.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_awsSettings_dto() {

        // When
        final AwsSettings settings = session
                .settingsService()
                .settings()
                .ofAwsGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getAwsRegions());
    }

    @Test
    public void should_return_settings_by_decimalFormatSymbols() {

        // When
        final Map settings = session
                .settingsService()
                .settings()
                .group("decimalFormatSymbols", Map.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_decimalFormatSymbolsSettings_dto() {

        // When
        final DecimalFormatSymbolsSettings settings = session
                .settingsService()
                .settings()
                .ofDecimalFormatSymbolsGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getDecimalSeparator());
        assertNotNull(settings.getCurrency());
        assertNotNull(settings.getMinusSign());
        assertNotNull(settings.getInfinity());
    }

    @Test
    public void should_return_settings_by_dateTimeSettings() {

        // When
        final Map settings = session
                .settingsService()
                .settings()
                .group("dateTimeSettings", Map.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_list_of_dateTimeSettings_dto() {

        // When
        final DateTimeSettings settings = session
                .settingsService()
                .settings()
                .ofDateTimeGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getDatepicker());
        assertNotNull(settings.getTimepicker());
    }

    @Test
    public void should_return_settings_by_userTimeZone() {

        // When
        final List settings = session
                .settingsService()
                .settings()
                .group("userTimeZones", List.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_list_of_userTimeZone_dto() {

        // When
        final List<UserTimeZone> settings = session
                .settingsService()
                .settings()
                .ofUserTimeZonesGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertTrue(settings.size() > 0);
    }

    @Test
    public void should_return_list_of_userTimeZone_dto_by_genericType() {

        // When
        final List<UserTimeZone> settings = session
                .settingsService()
                .settings()
                .group("userTimeZones", new GenericType<List<UserTimeZone>>() {
                })
                .getEntity();

        // Then
        assertNotNull(settings);
        assertTrue(settings.size() > 0);
    }

    @Test
    public void should_return_settings_by_dashboardSettings() {

        // When
        final Map settings = session
                .settingsService()
                .settings()
                .group("dashboardSettings", Map.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_list_of_dashboardSettings_dto() {

        // When
        final DashboardSettings settings = session
                .settingsService()
                .settings()
                .ofDashboardGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getNewItemsRegistry());
        assertTrue(settings.getNewItemsRegistry().size() > 0);
    }

    @Test
    public void should_return_settings_by_inputControls() {

        // When
        final Map settings = session
                .settingsService()
                .settings()
                .group("inputControls", Map.class)
                .getEntity();

        // Then
        assertNotNull(settings);
        assertFalse(settings.isEmpty());
    }

    @Test
    public void should_return_list_of_inputControlsSettings_dto() {

        // When
        final InputControlsSettings settings = session
                .settingsService()
                .settings()
                .ofInputControlsGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getUseUrlParametersOnReset());
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
                        .group("globalConfiguration", Map.class)
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
    public void should_return_list_of_globalConfigurationSettings_dto() {

        // When
        final GlobalConfigurationSettings settings = session
                .settingsService()
                .settings()
                .ofGlobalConfigurationGroup()
                .getEntity();

        // Then
        assertNotNull(settings);
        assertNotNull(settings.getAllFileResourceTypes());
        assertNotNull(settings.getCalendarInputJsp());
        assertNotNull(settings.getCurrentYearDateFormat());
        assertNotNull(settings.getDateFormat());
        assertNotNull(settings.getDefaultDomainDependentsBlockAndUpdate());
        assertNotNull(settings.getDefaultDomainDependentsUseACL());
        assertNotNull(settings.getDefaultRole());
        assertNotNull(settings.getDefaultDontUpdateDomainDependents());
        assertNotNull(settings.getEntitiesPerPage());
        assertNotNull(settings.getEmailRegExpPattern());
        assertNotNull(settings.getAllFileResourceTypes());
        assertNotNull(settings.getViewReportsFilterList());
        assertNotNull(settings.getOutputFolderFilterPatterns());
        assertNotNull(settings.getOutputFolderFilterList());
        assertNotNull(settings.getMessages());
        assertNotNull(settings.getDataSourceTypes());
    }

    @Test
    public void should_return_settings_by_user_specified_string_key_of_group() {

        /**
         * When
         */
        final Map settings = session
                .settingsService()
                    .settings()
                        .group("dateTimeSettings", Map.class)
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