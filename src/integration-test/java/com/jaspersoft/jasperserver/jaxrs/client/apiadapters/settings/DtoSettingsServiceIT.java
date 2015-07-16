package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.settings;

import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.dto.settings.AwsSettings;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    public void should_return_AwsSettings_object() {

        // When
        final AwsSettings settings = session
                .settingsService()
                .settings()
                .loadAwsSettings().getEntity();

        // Then
        assertNotNull(settings);
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



    @AfterMethod
    public void after() {
        config = null;
        client = null;
    }
}