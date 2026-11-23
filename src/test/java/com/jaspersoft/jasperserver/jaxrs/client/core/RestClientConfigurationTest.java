package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.Field;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

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
public class RestClientConfigurationTest {

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
        assertNull(config.getJasperReportsServerUrl());
    }

    @Test(testName = "loadConfiguration")
    public void should_load_configuration_from_property_file() throws Exception {
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

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakeProps);

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

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakeProps);

        assertEquals(configuration.getJasperReportsServerUrl(), fakeProps.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakeProps.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType().toString(), fakeProps.getProperty("acceptMimeType"));
        assertSame(configuration.getConnectionTimeout(), Integer.valueOf(fakeProps.getProperty("connectionTimeout")));
        assertSame(configuration.getReadTimeout(), Integer.valueOf(fakeProps.getProperty("readTimeout")));
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
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperContentMimeType);

        assertEquals(configuration.getJasperReportsServerUrl(), fakePropsWithUnproperContentMimeType.getProperty("url"));
        assertEquals(configuration.getContentMimeType(), MimeType.JSON);
        assertEquals(configuration.getAcceptMimeType().toString(), fakePropsWithUnproperContentMimeType.getProperty("acceptMimeType"));
        assertNull(configuration.getConnectionTimeout());
        assertNull(configuration.getReadTimeout());
        assertNotNull(configuration.getTrustManagers());
    }

    @Test(testName = "loadProperties")
    public void should_load_configuration_from_property_with_all_kind_of_set_values() throws Exception {
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

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(properties);

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
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperAcceptMimeType);

        assertEquals(configuration.getJasperReportsServerUrl(), fakePropsWithUnproperAcceptMimeType.getProperty("url"));
        assertEquals(configuration.getContentMimeType().toString(), fakePropsWithUnproperAcceptMimeType.getProperty("contentMimeType"));
        assertEquals(configuration.getAcceptMimeType(), MimeType.JSON);
        assertNull(configuration.getConnectionTimeout());
        assertNull(configuration.getReadTimeout());
        assertNotNull(configuration.getTrustManagers());
    }

    @Test(testName = "setJrsVersion")
    public void should_set_setJrsVersion_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setJrsVersion(JRSVersion.v5_0_0);
        Field field = RestClientConfiguration.class.getDeclaredField("jrsVersion");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, JRSVersion.v5_0_0);
    }

    @Test(testName = "setJrsVersion")
    public void should_get_not_null_setJrsVersion_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = RestClientConfiguration.class.getDeclaredField("jrsVersion");
        field.setAccessible(true);
        field.set(config, JRSVersion.v4_7_0);
        assertEquals(config.getJrsVersion(), JRSVersion.v4_7_0);
    }

    @Test(testName = "setTrustManagers")
    public void should_set_TrustManagers_with_proper_TrustManagers_object() throws Exception {
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

        config.setTrustManagers(new TrustManager[]{expected});

        assertNotNull(config.getTrustManagers()[0]);
        assertEquals(expected, config.getTrustManagers()[0]);
    }

    @Test(testName = "getTrustManagers")
    public void should_return_trusted_manager() throws Exception {
        RestClientConfiguration config = new RestClientConfiguration();

        TrustManager[] managers = config.getTrustManagers();
        assertNotNull(managers);
        assertTrue(managers.length == 1);

        ((X509TrustManager) managers[0]).checkClientTrusted(null, "abc");
        X509Certificate[] retrieved = ((X509TrustManager) managers[0]).getAcceptedIssuers();
        assertNull(retrieved);
    }

    @Test
    public void should_return_empty_configuration() {
        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration("path");
        assertEquals(configuration.getJasperReportsServerUrl(), null);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_contentMimetype() throws Exception {
        Properties fakePropsWithUnproperContentMimeType = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("contentMimeType", "UnacceptableContentMimeType");
        }};

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperContentMimeType);
        Assert.assertEquals(configuration.getContentMimeType(), MimeType.JSON);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_acceptMimetype() throws Exception {
        Properties fakePropsWithUnproperAcceptMimeType = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("acceptMimeType", "UnacceptableAcceptMimeType");
        }};

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperAcceptMimeType);
        Assert.assertEquals(configuration.getAcceptMimeType(), MimeType.JSON);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_jrs_version() throws Exception {
        Properties fakePropsWithUnproperJrsVersion = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("jrsVersion", "6.1");
        }};

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperJrsVersion);
        Assert.assertEquals(configuration.getJrsVersion(), JRSVersion.v5_5_0);
    }

    @Test
    public void should_not_throw_an_exception_with_wrong_authintication_type() throws Exception {
        Properties fakePropsWithUnproperAuthorizationType = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("authenticationType", "authenticationType");
        }};

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperAuthorizationType);
        Assert.assertEquals(configuration.getAuthenticationType(), AuthenticationType.SPRING);
    }

    @Test
    public void should_load_configuration_from_property_file_with_true_in_boolean_values() throws Exception {
        Properties fakePropsWithUnproperJrsVersion = new Properties() {{
            setProperty("url", "http://localhost:8080/jasperserver-pro/");
            setProperty("restrictedHttpMethods", "true");
            setProperty("logHttpEntity", "true");
            setProperty("logHttp", "true");
        }};

        RestClientConfiguration configuration = RestClientConfiguration.loadConfiguration(fakePropsWithUnproperJrsVersion);
        assertTrue(configuration.getRestrictedHttpMethods());
        assertTrue(configuration.getLogHttp());
        assertTrue(configuration.getLogHttpEntity());
    }

    @Test(testName = "getAuthenticationType")
    public void should_return_not_null_default_value_of_authenticationType_field() throws Exception {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = RestClientConfiguration.class.getDeclaredField("authenticationType");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, AuthenticationType.SPRING);
    }

    @Test(testName = "getAuthenticationType")
    public void should_return_not_null_value_of_authenticationType_field() throws Exception {
        RestClientConfiguration config = new RestClientConfiguration();
        AuthenticationType authenticationType = config.getAuthenticationType();
        assertNotNull(authenticationType);
        assertEquals(authenticationType, AuthenticationType.SPRING);
    }

    @Test(testName = "setAuthenticationType")
    public void should_set_authenticationType_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setAuthenticationType(AuthenticationType.BASIC);
        Field field = RestClientConfiguration.class.getDeclaredField("authenticationType");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, AuthenticationType.BASIC);
    }

    @Test(testName = "getRestrictedHttpMethods")
    public void should_return_not_null_value_of_restrictedHttpMethods_field() throws Exception {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = RestClientConfiguration.class.getDeclaredField("restrictedHttpMethods");
        field.setAccessible(true);
        Boolean retrieved = (Boolean) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.FALSE);
    }

    @Test(testName = "getHandleErrorsMethods")
    public void should_return_not_null_value_of_handleErrors_field() throws Exception {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = RestClientConfiguration.class.getDeclaredField("handleErrors");
        field.setAccessible(true);
        Boolean retrieved = (Boolean) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.TRUE);
    }

    @Test(testName = "getRestrictedHttpMethods")
    public void should_return_not_null_default_value_of_restrictedHttpMethods_field() throws Exception {
        RestClientConfiguration config = new RestClientConfiguration();
        Boolean retrieved = config.getRestrictedHttpMethods();
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.FALSE);
    }

    @Test(testName = "setRestrictedHttpMethods")
    public void should_set_restrictedHttpMethods_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setRestrictedHttpMethods(true);
        Field field = RestClientConfiguration.class.getDeclaredField("restrictedHttpMethods");
        field.setAccessible(true);
        Boolean retrieved = (Boolean) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.TRUE);
    }

    @Test(testName = "setContentMimeType")
    public void should_set_contentMimeType_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setContentMimeType(MimeType.XML);
        Field field = RestClientConfiguration.class.getDeclaredField("contentMimeType");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.XML);
    }

    @Test(testName = "setHamdleErrors")
    public void should_set_handkeErrors_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setHandleErrors(false);
        Field field = RestClientConfiguration.class.getDeclaredField("handleErrors");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Boolean.FALSE);
    }

    @Test(testName = "getContentMimeType")
    public void should_get_not_null_contentMimeType_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = RestClientConfiguration.class.getDeclaredField("contentMimeType");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.JSON);
    }

    @Test(testName = "getAcceptMimeType")
    public void should_set_acceptMimeType_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setAcceptMimeType(MimeType.XML);
        Field field = RestClientConfiguration.class.getDeclaredField("acceptMimeType");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.XML);
    }

    @Test(testName = "setAcceptMimeType")
    public void should_get_not_null_acceptMimeType_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        Field field = RestClientConfiguration.class.getDeclaredField("acceptMimeType");
        field.setAccessible(true);
        Object retrieved = field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, MimeType.JSON);
    }

    @Test(testName = "setConnectionTimeout")
    public void should_set_connectionTimeout_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setConnectionTimeout(100);
        Field field = RestClientConfiguration.class.getDeclaredField("connectionTimeout");
        field.setAccessible(true);
        Integer retrieved = (Integer) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Integer.valueOf(100));
    }

    @Test(testName = "setReadTimeout")
    public void should_set_readTimeout_field() throws IllegalAccessException, NoSuchFieldException {
        RestClientConfiguration config = new RestClientConfiguration();
        config.setReadTimeout(100);
        Field field = RestClientConfiguration.class.getDeclaredField("readTimeout");
        field.setAccessible(true);
        Integer retrieved = (Integer) field.get(config);
        assertNotNull(retrieved);
        assertEquals(retrieved, Integer.valueOf(100));
    }
}
