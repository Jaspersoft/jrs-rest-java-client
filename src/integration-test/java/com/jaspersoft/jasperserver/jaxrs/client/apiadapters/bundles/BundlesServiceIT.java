package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Tetiana Iefimenko
 */
public class BundlesServiceIT {

    private RestClientConfiguration config;
    private JasperserverRestClient client;
    private AnonymousSession session;

    @BeforeMethod
    public void before() {
        config = new RestClientConfiguration("http://localhost:4444/jasperserver-pro");
        config.setAcceptMimeType(MimeType.JSON);
        config.setContentMimeType(MimeType.JSON);
        config.setJrsVersion(JRSVersion.v6_1_0);
        client = new JasperserverRestClient(config);
        session = client.getAnonymousSession();
    }

     @Test
    public void should_return_all_bundles_for_default_locale() {

       // When
        final JSONObject bundles = session
                .bundlesService()
                .forLocale(null)
                .AllBundles()
                .getEntity();

        // Then
        assertNotNull(bundles);
        assertFalse(bundles.has("jasperserver_messages"));
    }

    @Test
    public void should_return_all_bundles_for_specified_locale() {

       // When
        final JSONObject bundles = session
                .bundlesService()
                .forLocale("de")
                .AllBundles()
                .getEntity();

        // Then
        assertNotNull(bundles);
        assertFalse(bundles.has("jasperserver_messages"));
    }

    @Test
    public void should_return__bundle_by_name_for_specified_locale() {

        // When
        final JSONObject bundle = session
                .bundlesService()
                .forLocale("de")
                .bundle("jasperserver_messages")
                .getEntity();

        // Then
        assertNotNull(bundle);
        assertFalse(bundle.has("jsp.JSErrorPage.errorTrace"));
    }

    @Test
    public void should_return__bundle_by_name_for_default_locale() {

        // When
        final JSONObject bundle = session
                .bundlesService()
                .forLocale(null)
                .bundle("jasperserver_messages")
                .getEntity();

        // Then
        assertNotNull(bundle);
        assertFalse(bundle.has("jsp.JSErrorPage.errorTrace"));
    }
}
