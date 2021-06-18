package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.TimeZone;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;
import static org.powermock.reflect.internal.WhiteboxImpl.setInternalState;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage}
 */
@PrepareForTest({SessionStorage.class,
        SSLContext.class,
        EncryptionUtils.class,
        ClientBuilder.class,
        Client.class,
        WebTarget.class})
public class SessionStorageTest extends PowerMockTestCase {

    @Mock
    private ClientBuilder builderMock;
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private AuthenticationCredentials credentialsMock;
    @Mock
    private SSLContext ctxMock;
    @Mock
    private Client clientMock;
    @Mock
    private WebTarget targetMock;
    @Mock
    private Invocation.Builder invocationBuilderMock;
    @Mock
    private Response responseMock;
    @Mock
    public SSLContext sslContextMock;
    @Mock
    public Response.StatusType statusTypeMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_init_ssl() {

        // Given
        mockStatic(ClientBuilder.class);
        when(ClientBuilder.newBuilder()).thenReturn(builderMock);
        doReturn("https://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
        doReturn(10000).when(configurationMock).getConnectionTimeout();
        doReturn(8000).when(configurationMock).getReadTimeout();
        doReturn(clientMock).when(builderMock).build();
        doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");

        // When
        try {
            new SessionStorage(configurationMock, credentialsMock, null, null);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void should_invoke_init_method_with_default_configuration() throws Exception {
        // Given
        mockStatic(ClientBuilder.class);
        when(ClientBuilder.newBuilder()).thenReturn(builderMock);
        doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
        doReturn(clientMock).when(builderMock).build();
        doReturn(null).when(configurationMock).getConnectionTimeout();
        doReturn(null).when(configurationMock).getReadTimeout();
        doReturn(targetMock).when(clientMock).target(anyString());
        doReturn(targetMock).when(targetMock).register(JacksonFeature.class);
        doReturn(targetMock).when(targetMock).register(MultiPartFeature.class);
        doReturn(targetMock).when(targetMock).register(any(JacksonJsonProvider.class));
        doReturn(false).when(configurationMock).getLogHttp();

        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);

        // Then
        assertEquals(Whitebox.getInternalState(sessionStorage, "configuration"), configurationMock);
        assertEquals(Whitebox.getInternalState(sessionStorage, "credentials"), credentialsMock);
        verify(configurationMock, times(2)).getJasperReportsServerUrl();
        verify(builderMock).build();
        verify(configurationMock).getConnectionTimeout();
        verify(configurationMock).getReadTimeout();
        verify(clientMock).target("http://54.83.98.156/jasperserver-pro");
        verify(targetMock).register(JacksonFeature.class);
        verify(targetMock, times(1)).register(isA(JacksonJsonProvider.class));
        verify(configurationMock).getLogHttp();
        verify(targetMock, never()).register(LoggingFilter.class);
    }

    @Test
    public void should_invoke_init_method_with_custom_configuration() throws Exception {

        //  Given
        mockStatic(ClientBuilder.class);
        when(ClientBuilder.newBuilder()).thenReturn(builderMock);
        doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
        doReturn(clientMock).when(builderMock).build();
        doReturn(1000).when(configurationMock).getConnectionTimeout();
        doReturn(clientMock).when(clientMock).property("jersey.config.client.connectTimeout", 1000);
        doReturn(200).when(configurationMock).getReadTimeout();
        doReturn(clientMock).when(clientMock).property("jersey.config.client.readTimeout", 200);
        doReturn(targetMock).when(clientMock).target(anyString());
        doReturn(targetMock).when(targetMock).register(JacksonFeature.class);
        doReturn(targetMock).when(targetMock).register(MultiPartFeature.class);
        doReturn(targetMock).when(targetMock).register(any(JacksonJsonProvider.class));
        doReturn(true).when(configurationMock).getLogHttp();
        doReturn(targetMock).when(targetMock).register(any(LoggingFilter.class));

        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);

        // Then
        assertEquals(Whitebox.getInternalState(sessionStorage, "configuration"), configurationMock);
        assertEquals(Whitebox.getInternalState(sessionStorage, "credentials"), credentialsMock);
        verify(configurationMock, times(2)).getJasperReportsServerUrl();
        verify(builderMock).build();
        verify(configurationMock).getConnectionTimeout();
        verify(configurationMock).getReadTimeout();
        verify(clientMock).property("jersey.config.client.connectTimeout", 1000);
        verify(clientMock).property("jersey.config.client.readTimeout", 200);
        verify(clientMock).target("http://54.83.98.156/jasperserver-pro");
        verify(targetMock).register(JacksonFeature.class);
        verify(targetMock, times(1)).register(isA(JacksonJsonProvider.class));
        verify(configurationMock).getLogHttp();
        verify(targetMock).register(isA(LoggingFilter.class));
    }

    @Test
    public void should_return_client_without_configuration() throws Exception {

        //  Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();
        SessionStorage sessionStorage = Mockito.spy(new SessionStorage(configurationMock, credentialsMock, null, null));
        when(sessionStorage.getRawClient()).thenReturn(clientMock);

        // When

        // When
        Client configuredClient = sessionStorage.getRawClient();

        // Then
        assertEquals(configuredClient, clientMock);
    }

    @Test
    public void should_return_client_with_configuration() throws Exception {

        //  Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();
        SessionStorage sessionStorage = Mockito.spy(new SessionStorage(configurationMock, credentialsMock, null, null));
        Whitebox.setInternalState(sessionStorage, "client", clientMock);
        Whitebox.setInternalState(sessionStorage, "sessionId", "sessionId");
        doReturn(targetMock).when(clientMock).target(anyString());
        doReturn(targetMock).when(targetMock).register(JacksonFeature.class);
        doReturn(targetMock).when(targetMock).register(MultiPartFeature.class);
        doReturn(targetMock).when(targetMock).register(any(JacksonJsonProvider.class));
        doReturn(targetMock).when(targetMock).register(any(SessionOutputFilter.class));
        doReturn(true).when(configurationMock).getLogHttp();
        doReturn(targetMock).when(targetMock).register(any(LoggingFilter.class));
        when(sessionStorage.getConfiguredClient()).thenReturn(targetMock);

        // When

        // When
        WebTarget configuredClient = sessionStorage.getConfiguredClient();

        // Then
        assertEquals(configuredClient, targetMock);
    }

    @Test
    public void should_create_new_instance_session_storage() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorageSpy = new SessionStorage(configurationMock, credentialsMock, null, null);

        // Then
        assertNotNull(sessionStorageSpy);
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "configuration"));
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "credentials"));
        assertEquals(Whitebox.getInternalState(sessionStorageSpy, "sessionId"), null);
    }

    /**
     * @deprecated  test for derprecared constructor*/
    @Test
    public void should_create_new_instance_session_storage_by_user_name_password() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorageSpy = new SessionStorage(configurationMock, credentialsMock);

        // Then
        assertNotNull(sessionStorageSpy);
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "configuration"));
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "credentials"));
        assertEquals(Whitebox.getInternalState(sessionStorageSpy, "sessionId"), null);
    }

    /**
     * @deprecated  test for derprecared constructor*/
    @Test
    public void should_create_new_instance_session_storage_by_user_name_password_time_zone() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorageSpy = new SessionStorage(configurationMock, credentialsMock, TimeZone.getTimeZone("America/Los_Angeles"));

        // Then
        assertNotNull(sessionStorageSpy);
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "configuration"));
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "credentials"));
        assertEquals(Whitebox.getInternalState(sessionStorageSpy, "sessionId"), null);
        assertEquals(Whitebox.getInternalState(sessionStorageSpy, "userTimeZone"), TimeZone.getTimeZone("America/Los_Angeles"));
    }

    @Test
    public void should_set_proper_internal_state_user_time_zone() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);
        sessionStorage.setUserTimeZone(TimeZone.getTimeZone("Canada/Central"));

        // Then
        assertEquals(Whitebox.getInternalState(sessionStorage, "userTimeZone"), TimeZone.getTimeZone("Canada/Central"));
    }


    @Test
    public void should_set_proper_internal_state_user_locale() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);
        Locale locale = new Locale("de");
        sessionStorage.setUserLocale(locale);

        // Then
        assertEquals(Whitebox.getInternalState(sessionStorage, "userLocale"), locale);
    }

    @Test
    public void should_return_proper_internal_state_user_time_zone() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);
        Whitebox.setInternalState(sessionStorage, "userTimeZone", TimeZone.getTimeZone("Canada/Central"));

        // Then
        assertEquals(sessionStorage.getUserTimeZone(), TimeZone.getTimeZone("Canada/Central"));
    }

    @Test
    public void should_return_proper_internal_state_user_locale() throws Exception {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);
        Locale locale = new Locale("de");
        Whitebox.setInternalState(sessionStorage, "userLocale", locale);

        // Then
        assertEquals(sessionStorage.getUserLocale(), locale);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void should_throw_an_exception_when_unable_to_init_SSL_context() throws Exception {

        // Given
        TrustManager[] managers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

        mockStatic(ClientBuilder.class, SSLContext.class);
        when(ClientBuilder.newBuilder()).thenReturn(builderMock);
        when(SSLContext.getInstance("SSL")).thenReturn(ctxMock);

        when(ctxMock, "init", null, managers, new SecureRandom()).thenThrow(new RuntimeException());

        doReturn("https://abc").when(configurationMock).getJasperReportsServerUrl();
        doReturn(managers).when(configurationMock).getTrustManagers();
        doReturn(100L).when(configurationMock).getReadTimeout();

        // When
        new SessionStorage(configurationMock, credentialsMock, null, null);

        // Then throw an exception
    }

    @Test
    public void should_set_and_get_state_for_object() {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);

        // When
        setInternalState(sessionStorage, "rootTarget", targetMock);
        setInternalState(sessionStorage, "sessionId", "sessionId");
        // Then
        assertNotNull(sessionStorage.getConfiguration());
        assertNotNull(sessionStorage.getCredentials());
        assertNotNull(sessionStorage.getRootTarget());
        assertNotNull(sessionStorage.getSessionId());
    }

    @Test
    public void should_set_session_id_for_object() {

        // Given
        suppress(method(SessionStorage.class, "init"));
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock, null, null);
        // When
        String sessionId = "JSESSIONID";
        sessionStorage.setSessionId(sessionId);
        // Then

        assertEquals(sessionId, getInternalState(sessionStorage, "sessionId"));
    }

    @AfterMethod
    public void after() {
        reset(builderMock, configurationMock, credentialsMock, invocationBuilderMock, responseMock, ctxMock, clientMock, targetMock);
    }

}