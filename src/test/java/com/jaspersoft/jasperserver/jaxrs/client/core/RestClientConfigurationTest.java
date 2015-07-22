package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jaspersoft.jasperserver.jaxrs.client.core.config.ServerMetaData;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.easymock.PowerMock.expectPrivate;
import static org.powermock.api.easymock.PowerMock.mockStaticPartial;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNull;

/**
 * Unit tests for {@link RestClientConfigurationTest}
 *
 * @author Alexander Krasnyanskiy
 */
@PrepareForTest({RestClientConfiguration.class, Properties.class, LogFactory.class, X509TrustManager.class})
public class RestClientConfigurationTest extends PowerMockTestCase {

    @Mock
    private Log logMock;

    @Mock
    private Properties propertiesMock;

    @Mock
    private RestClientConfiguration configMock;

    @Mock
    private X509TrustManager managerMock;

    private Properties fakeProps = new Properties() {{
        setProperty("url", "http://localhost:8080/jasperserver-pro/");
        setProperty("contentMimeType", "JSON");
        setProperty("acceptMimeType", "JSON");
        setProperty("connectionTimeout", "");
        setProperty("readTimeout", "");
    }};

    private Properties fakePropsWithNotEmptyConnectionTimeoutAndReadTimeout = new Properties() {{
        setProperty("url", "http://localhost:8080/jasperserver-pro/");
        setProperty("contentMimeType", "JSON");
        setProperty("acceptMimeType", "JSON");
        setProperty("connectionTimeout", "100");
        setProperty("readTimeout", "20");
    }};

    private Properties fakePropsWithUnproperAcceptMimeTypeAndContentMimeType = new Properties() {{
        setProperty("url", "http://localhost:8080/jasperserver-pro/");
        setProperty("contentMimeType", "UnacceptableContentMimeType");
        setProperty("acceptMimeType", "UnacceptableAcceptMimeType");
        setProperty("connectionTimeout", "");
        setProperty("readTimeout", "");
    }};

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test(testName = "RestClientConfiguration_parametrized_constructor")
    public void should_invoke_default_constructor_and_pass_valid_URL_to_setJasperReportsServerUrl_method() throws Exception {
        String fakedValidUrl = "http://localhost:8080/jasperserver-pro/";
        RestClientConfiguration config = new RestClientConfiguration(fakedValidUrl);
        assertNotNull(config.getTrustManagers());
        assertTrue(config.getTrustManagers().length > 0);
        assertNotNull(config.getTrustManagers()[0]);
        assertEquals(config.getJasperReportsServerUrl(), fakedValidUrl);
    }

    @Test(testName = "RestClientConfiguration_parametrized_constructor", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_pass_invalid_URL_to_the_constructor() {
        new RestClientConfiguration("invalidURL");
    }

    @Test(testName = "RestClientConfiguration_constructor_without_parameters")
    public void should_create_an_instance_of_RestClientConfiguration_but_without_setting_jasperReportsServerUrl() {
        RestClientConfiguration config = new RestClientConfiguration();
        assertNotNull(config.getTrustManagers());
        assertTrue(config.getTrustManagers().length > 0);
        assertNull(config.getJasperReportsServerUrl()); // URL must be null
        assertEquals(config.getAuthenticationType(), ServerMetaData.AuthenticationType.REST);
    }

    @Test(testName = "loadConfiguration")
    public void should_load_configuration_from_property_file() throws Exception {

        // Given
        // Here we're mocking nasty I/O operations
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakeProps);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        //Than
        assertEquals(configuration.getJasperReportsServerUrl(), fakeProps.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakeProps.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType().toString(), fakeProps.getProperty("acceptMimeType"));

        assertSame(configuration.getConnectionTimeout(), null);
        assertSame(configuration.getReadTimeout(), null);

        assertNotNull(configuration.getTrustManagers());
    }

    @Test(testName = "loadConfiguration", enabled = false)
    public void should_load_configuration_from_property_file_with_all_kind_of_setted_values() throws Exception {
        // Given
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithNotEmptyConnectionTimeoutAndReadTimeout);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        // Than
        assertEquals(configuration.getJasperReportsServerUrl(), fakeProps.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakeProps.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType().toString(), fakeProps.getProperty("acceptMimeType"));
        assertSame(configuration.getConnectionTimeout(), Integer.valueOf(fakePropsWithNotEmptyConnectionTimeoutAndReadTimeout.getProperty("connectionTimeout"))); // should be 20
        assertSame(configuration.getReadTimeout(), Integer.valueOf(fakePropsWithNotEmptyConnectionTimeoutAndReadTimeout.getProperty("readTimeout"))); // should be 100
        assertNotNull(configuration.getTrustManagers());
    }

    @Test(testName = "loadConfiguration")
    public void should_fire_logging_info_method_when_given_AcceptMimeType_and_ContentMimeType_are_invalid() throws Exception {

        // Given
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperAcceptMimeTypeAndContentMimeType);

        PowerMockito.whenNew(RestClientConfiguration.class).withNoArguments().thenReturn(configMock);

        // Mocked static member injection.
        Field field = field(RestClientConfiguration.class, "log");
        field.set(RestClientConfiguration.class, logMock);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration.loadConfiguration("superCoolPath");

        // Than
        verify(logMock, times(2)).info(anyString(), any(Throwable.class));
    }

    /**
     * NB: Please notice that tests for getters and setters were skipped.
     */
    @Test(testName = "setJrsVersion")
    public void should_set_setJrsVersion_field() throws IllegalAccessException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setJrsVersion(JRSVersion.v5_0_0);
        Field field = field(RestClientConfiguration.class, "jrsVersion");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, JRSVersion.v5_0_0);
    }
    
    @Test(testName = "setAuthenticationType")
    public void should_set_authenticationType_field() throws IllegalArgumentException, IllegalAccessException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setAuthenticationType(ServerMetaData.AuthenticationType.SPRING);
        Field field = field(RestClientConfiguration.class, "authenticationType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, ServerMetaData.AuthenticationType.SPRING);
    }

    @Test(testName = "setJrsVersion")
    public void should_get_not_null_setJrsVersion_field() throws IllegalAccessException {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = field(RestClientConfiguration.class, "jrsVersion");
        field.set(config, JRSVersion.v4_7_0);
        assertEquals(config.getJrsVersion(), JRSVersion.v4_7_0);
    }

    @Test(testName = "setTrustManagers")
    public void should_set_TrustManagers_with_proper_TrustManagers_object() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        X509TrustManager expected = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        // When
        config.setTrustManagers(new TrustManager[]{expected});

        // Than
        assertNotNull(config.getTrustManagers()[0]);
        assertEquals(expected, config.getTrustManagers()[0]);
    }

    @Test(testName = "setDatePattern", enabled = false)
    public void should_set_datePattern_field_with_proper_value() throws IllegalAccessException {
        RestClientConfiguration config = new RestClientConfiguration();
        //config.setDatePattern("dummyDatePattern");
        Field field = field(RestClientConfiguration.class, "datePattern");
        String retrieved = (String) field.get(config);
        assertEquals(retrieved, "dummyDatePattern");
    }

    @Test(testName = "getDatePattern", enabled = false)
    public void should_get_not_null_datePattern_field() throws IllegalAccessException {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = field(RestClientConfiguration.class, "datePattern");
        field.set(config, "dummyDatePattern");
        //assertEquals(config.getDatePattern(), "dummyDatePattern");
    }

    @Test(testName = "setDateTimePattern", enabled = false)
    public void should_set_dateTimePattern_field_with_proper_value() throws IllegalAccessException {
        RestClientConfiguration config = new RestClientConfiguration();
        //config.setDateTimePattern("dummyDateTimePattern");
        Field field = field(RestClientConfiguration.class, "dateTimePattern");
        String retrieved = (String) field.get(config);
        assertEquals(retrieved, "dummyDateTimePattern");
    }

    @Test(testName = "getDateTimePattern", enabled = false)
    public void should_get_not_null_dateTimePattern_field() throws IllegalAccessException {
        // Given
        final RestClientConfiguration config = new RestClientConfiguration();

        // When
        final Field field = field(RestClientConfiguration.class, "dateTimePattern");
        field.set(config, "dummyDateTimePattern");

        // Than
        //assertEquals(config.getDateTimePattern(), "dummyDateTimePattern");
    }

    @Test
    public void should_invoke_private_method() throws Exception {

        Properties propertiesSpy = PowerMockito.spy(new Properties());

        propertiesSpy.setProperty("url", "http://localhost:8080/jasperserver-pro/");
        propertiesSpy.setProperty("contentMimeType", "JSON");
        propertiesSpy.setProperty("acceptMimeType", "JSON");
        propertiesSpy.setProperty("connectionTimeout", "");
        propertiesSpy.setProperty("readTimeout", "");

        PowerMockito.whenNew(Properties.class).withNoArguments().thenReturn(propertiesSpy);
        PowerMockito.doNothing().when(propertiesSpy).load(any(InputStream.class));
        PowerMockito.suppress(method(Properties.class, "load", InputStream.class));

        RestClientConfiguration retrieved = RestClientConfiguration.loadConfiguration("path");

        AssertJUnit.assertNotNull(retrieved);
        Mockito.verify(propertiesSpy, times(1)).load(any(InputStream.class));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void should_throw_an_exception_while_loading_props() throws Exception {
        RestClientConfiguration.loadConfiguration("path");
    }

    @Test
    public void should_return_trusted_manager() throws Exception {

        RestClientConfiguration config = Mockito.spy(new RestClientConfiguration());
        TrustManager[] managers = config.getTrustManagers();

        assertNotNull(managers);
        assertTrue(managers.length == 1);

        ((X509TrustManager) managers[0]).checkClientTrusted(null, "abc");
        //((X509TrustManager) managers[0]).checkServerTrusted(null, "abc");
        X509Certificate[] retrieved = ((X509TrustManager) managers[0]).getAcceptedIssuers();

        Assert.assertNull(retrieved);
    }

    @AfterMethod
    public void after() {
        reset(configMock, logMock, propertiesMock, managerMock);
    }
}