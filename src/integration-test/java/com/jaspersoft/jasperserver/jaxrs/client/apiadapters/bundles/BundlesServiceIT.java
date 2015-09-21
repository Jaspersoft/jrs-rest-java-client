package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.RestClientUnitTest;
import com.jaspersoft.jasperserver.jaxrs.client.core.AnonymousSession;
import java.util.Locale;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Tetiana Iefimenko
 */

public class BundlesServiceIT extends RestClientUnitTest {

    private AnonymousSession session;

    @BeforeClass
    public void before() {
        session = client.getAnonymousSession();
    }

     @Test
    public void should_return_all_bundles_for_default_locale() {

       // When
        final Map<String, Map<String, String>> bundles = session
                .bundlesService()
                .allBundles()
                .getEntity();

        // Then
        assertNotNull(bundles);
        assertFalse(bundles.size() == 0);
        assertTrue(bundles.containsKey("jasperserver_config"));
    }

    @Test
    public void should_return_all_bundles_for_specified_string_locale() {

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
    public void should_return_all_bundles_for_specified_locale() {

       // When
        final Map<String, Map<String, String>> bundles = session
                .bundlesService()
                .forLocale(new Locale("de"))
                .allBundles()
                .getEntity();

        // Then
        assertNotNull(bundles);
        assertFalse(bundles.size() == 0);
        assertTrue(bundles.containsKey("jasperserver_config"));
    }

    @Test
    public void should_return__bundle_by_name_for_specified_string_locale() {

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
    public void should_return__bundle_by_name_for_specified_locale() {

        // When
        final Map<String, String> bundle = session
                .bundlesService()
                .forLocale(new Locale("en_US"))
                .bundle("jasperserver_messages")
                .getEntity();

        // Then
        assertNotNull(bundle);
        assertFalse(bundle.size() == 0);
        assertTrue(bundle.containsKey("logCollectors.form.resourceUri.hint"));
    }

    @Test
    public void should_return__bundle_by_name_for_specified_as_constant_locale() {

        // When
        final Map<String, String> bundle = session
                .bundlesService()
                .forLocale(Locale.US)
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
                .bundle("jasperserver_messages")
                .getEntity();

        // Then
        assertNotNull(bundle);
        assertFalse(bundle.size() == 0);
        assertTrue(bundle.containsKey("logCollectors.form.resourceUri.hint"));
    }
}
