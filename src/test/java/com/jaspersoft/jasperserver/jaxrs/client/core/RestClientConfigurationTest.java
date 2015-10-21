package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.easymock.PowerMock.expectPrivate;
import static org.powermock.api.easymock.PowerMock.mockStaticPartial;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

/**
 * Unit tests for {@link RestClientConfiguration}
 *
 * @author Alexander Krasnyanskiy
 */
@PrepareForTest({RestClientConfiguration.class, Properties.class, X509TrustManager.class})
public class RestClientConfigurationTest extends PowerMockTestCase {

    private Properties fakePropsWithUnproperAcceptMimeType = new Properties() {{
        setProperty("url", "http://localhost:8080/jasperserver-pro/");
        setProperty("contentMimeType", "XML");
        setProperty("acceptMimeType", "UnacceptableAcceptMimeType");
        setProperty("connectionTimeout", "");
        setProperty("readTimeout", "");
    }};

    private Properties fakePropsWithUnproperContentMimeType = new Properties() {{
        setProperty("url", "http://localhost:8080/jasperserver-pro/");
        setProperty("contentMimeType", "UnacceptableContentMimeType");
        setProperty("acceptMimeType", "XML");
        setProperty("connectionTimeout", "");
        setProperty("readTimeout", "");
    }};

    @Test(testName = "RestClientConfiguration_parametrized_constructor")
    public void should_invoke_default_constructor_and_pass_valid_URL_to_set_JasperReportsServerUrl_method() {
        String fakedValidUrl = "http://localhost:8080/jasperserver-pro/";
        RestClientConfiguration config = new RestClientConfiguration(fakedValidUrl);
        assertNotNull(config.getTrustManagers());
        assertTrue(config.getTrustManagers().length > 0);
        assertNotNull(config.getTrustManagers()[0]);
        assertEquals(config.getJasperReportsServerUrl(), fakedValidUrl);
        assertEquals(config.getAuthenticationType(), AuthenticationType.SPRING);
        assertFalse(config.getRestrictedHttpMethods());
        assertFalse(config.getLogHttp());
        assertFalse(config.getLogHttpEntity());
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
    }

    @Test(testName = "loadConfiguration")
    public void should_load_configuration_from_property_file() throws Exception {
        // Given
        Properties fakeProps = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("contentMimeType", "JSON");
            setProperty("acceptMimeType", "JSON");
            setProperty("connectionTimeout", "");
            setProperty("readTimeout", "");
            setProperty("authenticationType", "SPRING");
            setProperty("restrictedHttpMethods", "false");
            setProperty("logHttpEntity", "false");
            setProperty("logHttp", "false");
            setProperty("handleErrors", "true");
        }};
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakeProps);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        //Then
        assertEquals(configuration.getJasperReportsServerUrl(), fakeProps.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakeProps.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType().toString(), fakeProps.getProperty("acceptMimeType"));
        assertEquals(configuration.getAuthenticationType().toString(), fakeProps.getProperty("authenticationType"));
        assertEquals(configuration.getRestrictedHttpMethods().toString(), fakeProps.getProperty("restrictedHttpMethods"));
        assertEquals(configuration.getLogHttp().toString(), fakeProps.getProperty("logHttp"));
        assertEquals(configuration.getLogHttpEntity().toString(), fakeProps.getProperty("logHttpEntity"));
        assertEquals(configuration.getHandleErrors().toString(), fakeProps.getProperty("handleErrors"));

        assertSame(configuration.getConnectionTimeout(), null);
        assertSame(configuration.getReadTimeout(), null);

        assertNotNull(configuration.getTrustManagers());
    }

    @Test(testName = "loadConfiguration")
      public void should_load_configuration_by_path_with_all_kind_of_set_values() throws Exception {
        // Given
        // fake properties with not empty connection timeout and read timeout
        Properties fakeProps = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("contentMimeType", "JSON");
            setProperty("acceptMimeType", "JSON");
            setProperty("connectionTimeout", "100");
            setProperty("readTimeout", "20");
            setProperty("authenticationType", "SPRING");
            setProperty("restrictedHttpMethods", "false");
            setProperty("logHttpEntity", "false");
            setProperty("logHttp", "false");
            setProperty("handleErrors", "false");
        }};
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakeProps);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        // Then
        assertEquals(configuration.getJasperReportsServerUrl(), fakeProps.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakeProps.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType().toString(), fakeProps.getProperty("acceptMimeType"));
        assertSame(configuration.getConnectionTimeout(), Integer.valueOf(fakeProps.getProperty("connectionTimeout"))); // should be 20
        assertSame(configuration.getReadTimeout(), Integer.valueOf(fakeProps.getProperty("readTimeout"))); // should be 100

        assertEquals(configuration.getAuthenticationType().toString(), fakeProps.getProperty("authenticationType"));
        assertEquals(configuration.getRestrictedHttpMethods().toString(), fakeProps.getProperty("restrictedHttpMethods"));
        assertEquals(configuration.getLogHttp().toString(), fakeProps.getProperty("logHttp"));
        assertEquals(configuration.getLogHttpEntity().toString(), fakeProps.getProperty("logHttpEntity"));
        assertEquals(configuration.getHandleErrors().toString(), fakeProps.getProperty("handleErrors"));

        assertNotNull(configuration.getTrustManagers());
        assertEquals(configuration.getAuthenticationType(), AuthenticationType.SPRING);
        assertFalse(configuration.getRestrictedHttpMethods());
        assertFalse(configuration.getLogHttp());
        assertFalse(configuration.getLogHttpEntity());
    }


    @Test(testName = "loadConfiguration")
    public void should_load_configuration_from_property_file_with_unsupported_contentMimeType() throws Exception {
        // Given
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperContentMimeType);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        // Then
        assertEquals(configuration.getJasperReportsServerUrl(), fakePropsWithUnproperContentMimeType.getProperty("url"));
        assertEquals(configuration.getContentMimeType(), MimeType.JSON);
        assertEquals(configuration.getAcceptMimeType().toString(), fakePropsWithUnproperContentMimeType.getProperty("acceptMimeType"));
        assertNull(configuration.getConnectionTimeout());
        assertNull(configuration.getReadTimeout());
        assertNotNull(configuration.getTrustManagers());
    }

    @Test(testName = "loadProperties")
    public void should_load_configuration_from_property_with_all_kind_of_set_values() throws Exception {
        // Given
        Properties properties = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("contentMimeType", "JSON");
            setProperty("acceptMimeType", "JSON");
            setProperty("connectionTimeout", "100");
            setProperty("readTimeout", "20");
            setProperty("authenticationType", "SPRING");
            setProperty("restrictedHttpMethods", "false");
            setProperty("logHttpEntity", "false");
            setProperty("logHttp", "false");
            setProperty("handleErrors", "false");
        }};

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(properties);

        // Then
        assertEquals(configuration.getJasperReportsServerUrl(), properties.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), properties.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType().toString(), properties.getProperty("acceptMimeType"));
        assertSame(configuration.getConnectionTimeout(), Integer.valueOf(properties.getProperty("connectionTimeout")));
        assertSame(configuration.getReadTimeout(), Integer.valueOf(properties.getProperty("readTimeout")));

        assertEquals(configuration.getAuthenticationType().toString(), properties.getProperty("authenticationType"));
        assertEquals(configuration.getRestrictedHttpMethods().toString(), properties.getProperty("restrictedHttpMethods"));
        assertEquals(configuration.getLogHttp().toString(), properties.getProperty("logHttp"));
        assertEquals(configuration.getLogHttpEntity().toString(), properties.getProperty("logHttpEntity"));
        assertEquals(configuration.getHandleErrors().toString(), properties.getProperty("handleErrors"));

        assertNotNull(configuration.getTrustManagers());
        assertEquals(configuration.getAuthenticationType(), AuthenticationType.SPRING);
        assertFalse(configuration.getRestrictedHttpMethods());
        assertFalse(configuration.getLogHttp());
        assertFalse(configuration.getLogHttpEntity());
    }

    @Test(testName = "loadConfiguration")
    public void should_load_configuration_from_property_file_with_unsupported_acceptMimeType() throws Exception {
        // Given
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperAcceptMimeType);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        // Then
        assertEquals(configuration.getJasperReportsServerUrl(), fakePropsWithUnproperAcceptMimeType.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakePropsWithUnproperAcceptMimeType.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType(), MimeType.JSON);
        assertNull(configuration.getConnectionTimeout());
        assertNull(configuration.getReadTimeout());
        assertNotNull(configuration.getTrustManagers());
    }


    @Test(testName = "setJrsVersion")
    public void should_set_setJrsVersion_field() throws IllegalAccessException {

        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        config.setJrsVersion(JRSVersion.v5_0_0);
        Field field = field(RestClientConfiguration.class, "jrsVersion");
        // When
        Object retrieved = field.get(config);
        //Then
        assertNotNull(retrieved);
        assertEquals(retrieved, JRSVersion.v5_0_0);
    }

    @Test(testName = "setJrsVersion")
    public void should_get_not_null_setJrsVersion_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = field(RestClientConfiguration.class, "jrsVersion");
        // When
        field.set(config, JRSVersion.v4_7_0);
        //then
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

        // Then
        assertNotNull(config.getTrustManagers()[0]);
        assertEquals(expected, config.getTrustManagers()[0]);
    }

    @Test(testName = "loadConfiguration")
    public void should_invoke_private_method() throws Exception {
        // Given
        Properties propertiesSpy = spy(new Properties());
        propertiesSpy.setProperty("url", "http://localhost:8080/jasperserver-pro/");
        propertiesSpy.setProperty("contentMimeType", "JSON");
        propertiesSpy.setProperty("acceptMimeType", "JSON");
        propertiesSpy.setProperty("connectionTimeout", "");
        propertiesSpy.setProperty("readTimeout", "");

        PowerMockito.whenNew(Properties.class).withNoArguments().thenReturn(propertiesSpy);
        PowerMockito.doNothing().when(propertiesSpy).load(any(InputStream.class));
        PowerMockito.suppress(method(Properties.class, "load", InputStream.class));
        // When
        RestClientConfiguration retrieved = RestClientConfiguration.loadConfiguration("path");
        //Then
        assertNotNull(retrieved);
        Mockito.verify(propertiesSpy, times(1)).load(any(InputStream.class));
    }

    @Test(testName = "ExceptionWhileLoadingProperties", expectedExceptions = NullPointerException.class, enabled = false)
    public void should_throw_an_exception_while_loading_props() throws Exception {
        // When
        RestClientConfiguration.loadConfiguration("path");
    }

    @Test(testName = "getTrustManagers")
    public void should_return_trusted_manager() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When

        TrustManager[] managers = config.getTrustManagers();
        //then
        assertNotNull(managers);
        assertTrue(managers.length == 1);

        ((X509TrustManager) managers[0]).checkClientTrusted(null, "abc");
        X509Certificate[] retrieved = ((X509TrustManager) managers[0]).getAcceptedIssuers();
        //Then
        assertNull(retrieved);
    }

    @Test
    public void should_return_empty_configuration() {
        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("path");
        //Then
        assertEquals(configuration.getJasperReportsServerUrl(), null);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_contentMimetype() throws Exception {
        // Given
        Properties fakePropsWithUnproperContentMimeType = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("contentMimeType", "UnacceptableContentMimeType");
        }};

        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperContentMimeType);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");
        // Then
        Assert.assertEquals(configuration.getContentMimeType(), MimeType.JSON);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_acceptMimetype() throws Exception {
        // Given
        Properties fakePropsWithUnproperAcceptMimeType = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("acceptMimeType", "UnacceptableAcceptMimeType");
        }};

        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperAcceptMimeType);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");
        // Then
        Assert.assertEquals(configuration.getAcceptMimeType(), MimeType.JSON);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_jrs_version() throws Exception {
        // Given
        Properties fakePropsWithUnproperJrsVersion = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("jrsVersion", "6.1");
        }};
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperJrsVersion);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");
        // Then
        Assert.assertEquals(configuration.getJrsVersion(), JRSVersion.v5_5_0);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_authintication_type() throws Exception {
        // Given
        Properties fakePropsWithUnproperAuthorizationType = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("authenticationType", "authenticationType");
        }};
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperAuthorizationType);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");

        // Then
        Assert.assertEquals(configuration.getAuthenticationType(), AuthenticationType.SPRING);
    }

    @Test
    public void should_load_configuration_from_property_file_with_true_in_boolean_values() throws Exception {
        // Given
        Properties fakePropsWithUnproperJrsVersion = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("restrictedHttpMethods", "true");
            setProperty("logHttpEntity", "true");
            setProperty("logHttp", "true");
        }};
        mockStaticPartial(RestClientConfiguration.class, "loadProperties");
        Method[] methods = MemberMatcher.methods(RestClientConfiguration.class, "loadProperties");

        expectPrivate(RestClientConfiguration.class, methods[0], "superCoolPath").andReturn(fakePropsWithUnproperJrsVersion);
        replay(RestClientConfiguration.class);

        // When
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("superCoolPath");
        assertTrue(configuration.getRestrictedHttpMethods());
        assertTrue(configuration.getLogHttp());
        assertTrue(configuration.getLogHttpEntity());
    }


    @Test(testName = "getAuthenticationType")
    public void should_return_not_null_default_value_of_authenticationType_field() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        //Then
        Field field = field(RestClientConfiguration.class, "authenticationType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, AuthenticationType.SPRING);

    }

    @Test(testName = "getAuthenticationType")
    public void should_return_not_null_value_of_authenticationType_field() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        AuthenticationType authenticationType = config.getAuthenticationType();
        //Then
        assertNotNull(authenticationType);
        assertEquals(authenticationType, AuthenticationType.SPRING);

    }

    @Test(testName = "setAuthenticationType")
    public void should_set_authenticationType_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setAuthenticationType(AuthenticationType.BASIC);
        //Then
        Field field = field(RestClientConfiguration.class, "authenticationType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, AuthenticationType.BASIC);
    }

    @Test(testName = "getRestrictedHttpMethods")
    public void should_return_not_null_value_of_restrictedHttpMethods_field() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        //Then
        Field field = field(RestClientConfiguration.class, "restrictedHttpMethods");
        Boolean retrieved = (Boolean) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.FALSE);

    }

    @Test(testName = "getHandleErrorsMethods")
    public void should_return_not_null_value_of_handleErrors_field() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        //Then
        Field field = field(RestClientConfiguration.class, "handleErrors");
        Boolean retrieved = (Boolean) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.TRUE);

    }

    @Test(testName = "getRestrictedHttpMethods")
    public void should_return_not_null_default_value_of_restrictedHttpMethods_field() throws Exception {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        Boolean retrieved = config.getRestrictedHttpMethods();
        //Then
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.FALSE);

    }

    @Test(testName = "setRestrictedHttpMethods")
    public void should_set_restrictedHttpMethods_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setRestrictedHttpMethods(true);
        //Then
        Field field = field(RestClientConfiguration.class, "restrictedHttpMethods");
        Boolean retrieved = (Boolean) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.TRUE);
    }

    @Test(testName = "setContentMimeType")
    public void should_set_contentMimeType_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setContentMimeType(MimeType.XML);
        //Then
        Field field = field(RestClientConfiguration.class, "contentMimeType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.XML);

    }

    @Test(testName = "setHamdleErrors")
    public void should_set_handkeErrors_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setHandleErrors(false);
        //Then
        Field field = field(RestClientConfiguration.class, "handleErrors");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.FALSE);

    }

    @Test(testName = "getContentMimeType")
    public void should_get_not_null_contentMimeType_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        //Then
        Field field = field(RestClientConfiguration.class, "contentMimeType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.JSON);
    }

    @Test(testName = "getAcceptMimeType")
    public void should_set_acceptMimeType_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setAcceptMimeType(MimeType.XML);
        //Then
        Field field = field(RestClientConfiguration.class, "acceptMimeType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.XML);

    }

    @Test(testName = "setAcceptMimeType")
    public void should_get_not_null_acceptMimeType_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        //Then
        Field field = field(RestClientConfiguration.class, "acceptMimeType");
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.JSON);
    }

    @Test(testName = "setConnectionTimeout")
    public void should_set_connectionTimeout_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setConnectionTimeout(100);
        //Then
        Field field = field(RestClientConfiguration.class, "connectionTimeout");
        Integer retrieved = (Integer)field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Integer.valueOf(100));

    }

    @Test(testName = "setReadTimeout")
    public void should_set_readTimeout_field() throws IllegalAccessException {
        // Given
        RestClientConfiguration config = new RestClientConfiguration();
        // When
        config.setReadTimeout(100);
        //Then
        Field field = field(RestClientConfiguration.class, "readTimeout");
        Integer retrieved = (Integer) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Integer.valueOf(100));


    }

}