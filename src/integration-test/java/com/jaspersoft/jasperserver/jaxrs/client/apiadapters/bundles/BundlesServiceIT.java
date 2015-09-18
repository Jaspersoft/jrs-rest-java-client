package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.core.AnonymousSession;
import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        config.setLogHttp(true);
        config.setLogHttpEntity(true);
        client = new JasperserverRestClient(config);
        session = client.getAnonymousSession();
    }

     @Test
    public void should_return_all_bundles_for_default_locale() {

       // When
        final Map<String, Map<String, String>> bundles = session
                .bundlesService()
                .forLocale(null)
                .allBundles()
                .getEntity();

        // Then
        assertNotNull(bundles);
        assertFalse(bundles.size() == 0);
        assertTrue(bundles.containsKey("jasperserver_config"));
    }

    @Test
    public void should_return_all_bundles_for_specified_locale() {

       // When
        final Map<String, Map<String, String>> bundles = session
                .bundlesService()
                .forLocale("de")
                .allBundles()
                .getEntity();

        // Then
        assertNotNull(bundles);
        assertFalse(bundles.size() == 0);
        assertTrue(bundles.containsKey("jasperserver_config"));
    }

    @Test
    public void should_return__bundle_by_name_for_specified_locale() {

        // When
        final Map<String, String> bundle = session
                .bundlesService()
                .forLocale("de")
                .bundle("jasperserver_messages")
                .getEntity();

        // Then
        assertNotNull(bundle);
        assertFalse(bundle.size() == 0);
        assertTrue(bundle.containsKey("logCollectors.form.resourceUri.hint"));
    }

    @Test
    public void should_return__bundle_by_name_for_default_locale() {

        // When
        final Map<String, String> bundle = session
                .bundlesService()
                .forLocale(null)
                .bundle("jasperserver_messages")
                .getEntity();

        // Then
        assertNotNull(bundle);
        assertFalse(bundle.size() == 0);
        assertTrue(bundle.containsKey("logCollectors.form.resourceUri.hint"));
    }
}
