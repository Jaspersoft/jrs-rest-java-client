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

    @Test(enabled = false)
    public void test() throws Exception {

        /* Given */
        PowerMockito.suppress(method(SessionStorage.class, "init"));

        RestClientConfiguration configurationMock = PowerMockito.mock(RestClientConfiguration.class);
        AuthenticationCredentials credentialsMock = PowerMockito.mock(AuthenticationCredentials.class);
        SessionStorage sessionStorageSpy = PowerMockito.spy(new SessionStorage(configurationMock, credentialsMock));

        assertNotNull(sessionStorageSpy);
    }

    @Test(enabled = true)
    public void should_invoke_private_methods_during_object_constructing() throws Exception {

//        /* Given */
//        TrustManager[] managers = new TrustManager[]{
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                    }
//                }};
//
//        PowerMockito.mockStatic(ClientBuilder.class);
//        PowerMockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        PowerMockito.mockStatic(SSLContext.class);
//        PowerMockito.when(SSLContext.getInstance("SSL")).thenReturn(ctxMock);
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(new HashMap<String, String>() {{
//            put("n", "abc");
//            put("e", "abc");
//        }});
//
//
//        PowerMockito.doReturn("https://abc").when(configurationMock).getJasperReportsServerUrl();
//        PowerMockito.doReturn(managers).when(configurationMock).getTrustManagers();
//        PowerMockito.doReturn(100).when(configurationMock).getReadTimeout();
//        PowerMockito.doReturn("qwerty").when(credentialsMock).getPassword();
//
//
//        PowerMockito.suppress(method(SSLContext.class, "init", KeyManager[].class, TrustManager[].class, SecureRandom.class));
//        //PowerMockito.suppress(increment(SessionStorage.class, "login"));
//
//        PowerMockito.when(builderMock.sslContext(ctxMock)).thenReturn(builderMock);
//        PowerMockito.when(builderMock.hostnameVerifier(any(HostnameVerifier.class))).thenReturn(builderMock);
//        PowerMockito.when(builderMock.build()).thenReturn(clientMock);
//        PowerMockito.when(clientMock.target("https://abc")).thenReturn(targetMock);
//        PowerMockito.when(targetMock.path(anyString())).thenReturn(targetMock);
//        PowerMockito.when(targetMock.request()).thenReturn(invocationBuilderMock);
//        PowerMockito.when(targetMock.property(anyString(), any(Boolean.class))).thenReturn(targetMock);
//        PowerMockito.when(invocationBuilderMock.get()).thenReturn(responseMock);
//        PowerMockito.when(responseMock.getStatus()).thenReturn(302);
//        PowerMockito.when(responseMock.getHeaderString(anyString())).thenReturn("JSESSIONID=ABC;");
//        PowerMockito.when(invocationBuilderMock.cookie(anyString(), anyString())).thenReturn(invocationBuilderMock);
//
//        // ???
//        PowerMockito.when(invocationBuilderMock.post(any(javax.ws.rs.client.Entity.class))).thenReturn(responseMock);
//
//        PowerMockito.when(responseMock.readEntity(String.class)).thenReturn("abc");
//        PowerMockito.when(responseMock.getCookies()).thenReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("name", "value", "domain", "version")));
//        }});
//
//        /* When */
//
//        SessionStorage sessionStorageSpy = PowerMockito.spy(new SessionStorage(configurationMock, credentialsMock));
//
//
//        /* Than */
//
//        assertNotNull(sessionStorageSpy);
//
//        Mockito.verify(builderMock, times(1)).build();
//        //Mockito.verify(ctxMock, times(1)).init(null, any(TrustManager[].class), any(SecureRandom.class));
//        Mockito.verify(builderMock, times(1)).sslContext(ctxMock);
//        Mockito.verify(builderMock, times(1)).hostnameVerifier(any(HostnameVerifier.class));
//
//        Mockito.verify(configurationMock, times(1)).getConnectionTimeout();
//        Mockito.verify(configurationMock, times(2)).getJasperReportsServerUrl();
//
//        Mockito.verify(clientMock, times(1)).target(anyString());
//        Mockito.verify(clientMock, times(2)).property(anyString(), anyLong());
//
//        Mockito.verify(targetMock, times(1)).register(any(SessionOutputFilter.class));
//
//        PowerMockito.verifyStatic(times(1));
//        ClientBuilder.newBuilder();
//
//        PowerMockito.verifyStatic(times(1));
//        SSLContext.getInstance("SSL");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void should_throw_an_exception_when_unable_to_init_SSL_context() throws Exception {

        /* Given */
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

        /* When */
        SessionStorage sessionStorageSpy = PowerMockito.spy(new SessionStorage(configurationMock, credentialsMock));

        /* Than throw an exception */
    }

    @Test(enabled = false)
    public void should_set_and_get_state_for_object() {

        /* Given */
        PowerMockito.suppress(method(SessionStorage.class, "init"));
        SessionStorage sessionStorageSpy = PowerMockito.spy(new SessionStorage(configurationMock, credentialsMock));
        Whitebox.setInternalState(sessionStorageSpy, "rootTarget", targetMock);
        Whitebox.setInternalState(sessionStorageSpy, "sessionId", "sessionId");

        /* Verify */
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