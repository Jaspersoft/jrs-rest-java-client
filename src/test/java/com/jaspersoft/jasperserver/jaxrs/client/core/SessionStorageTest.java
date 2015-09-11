package com.jaspersoft.jasperserver.jaxrs.client.core;//package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.testng.Assert.assertNotNull;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage}
 */
@PrepareForTest({SessionStorage.class, SSLContext.class, EncryptionUtils.class, ClientBuilder.class})
public class SessionStorageTest extends PowerMockTestCase {

    @Mock
    private ClientBuilder builderMock;
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

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_create_new_instance_session_storage() throws Exception {

        // Given
        PowerMockito.suppress(method(SessionStorage.class, "init"));
        // When
        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock);
        // Then
        assertNotNull(sessionStorage);
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

        PowerMockito.mockStatic(ClientBuilder.class, SSLContext.class);
        PowerMockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
        PowerMockito.when(SSLContext.getInstance("SSL")).thenReturn(ctxMock);

        PowerMockito.when(ctxMock, "init", null, managers, new SecureRandom()).thenThrow(new RuntimeException());

        PowerMockito.doReturn("https://abc").when(configurationMock).getJasperReportsServerUrl();
        PowerMockito.doReturn(managers).when(configurationMock).getTrustManagers();
        PowerMockito.doReturn(100L).when(configurationMock).getReadTimeout();

        // When
        SessionStorage sessionStorageSpy = new SessionStorage(configurationMock, credentialsMock);

        // Then throw an exception
    }

    @Test
    public void should_set_and_get_state_for_object() {

        // Given
        PowerMockito.suppress(method(SessionStorage.class, "init"));
        SessionStorage sessionStorageSpy = PowerMockito.spy(new SessionStorage(configurationMock, credentialsMock));
        // When
        Whitebox.setInternalState(sessionStorageSpy, "rootTarget", targetMock);
        Whitebox.setInternalState(sessionStorageSpy, "sessionId", "sessionId");
        // Then
        assertNotNull(sessionStorageSpy.getConfiguration());
        assertNotNull(sessionStorageSpy.getCredentials());
        assertNotNull(sessionStorageSpy.getRootTarget());
        assertNotNull(sessionStorageSpy.getSessionId());
    }

    @AfterMethod
    public void after() {
        reset(builderMock, configurationMock, credentialsMock, invocationBuilderMock, responseMock, ctxMock, clientMock, targetMock);
    }
}