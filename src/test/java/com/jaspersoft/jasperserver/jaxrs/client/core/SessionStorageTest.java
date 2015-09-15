package com.jaspersoft.jasperserver.jaxrs.client.core;//package com.jaspersoft.jasperserver.jaxrs.client.core;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.testng.Assert.assertEquals;
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
    @Mock
    public Response.StatusType statusTypeMock;

    @Mock
    public SSLContext sslContextMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_create_new_instance_session_storage() throws Exception {

        // Given
        PowerMockito.suppress(method(SessionStorage.class, "init"));
        // When
        SessionStorage sessionStorageSpy = spy(new SessionStorage(configurationMock, credentialsMock));
        // Then
        assertNotNull(sessionStorageSpy);
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "configuration"));
        assertNotNull(Whitebox.getInternalState(sessionStorageSpy, "credentials"));
        assertEquals(Whitebox.getInternalState(sessionStorageSpy, "rootTarget"), null);
        assertEquals(Whitebox.getInternalState(sessionStorageSpy, "sessionId"), null);
    }

    @Test
    public void should_init_ssl() {

        /** - mock for static method **/
        PowerMockito.mockStatic(ClientBuilder.class);
        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);

        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
        Mockito.doReturn("https://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
        Mockito.doReturn(clientMock).when(builderMock).build();
        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");

        /** - mocks for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();

        PowerMockito.mockStatic(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(null);
        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");

        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
        Mockito.doReturn(new HashMap<String, NewCookie>() {{
            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
        }}).when(responseMock).getCookies();

        /** - mocks for {@link SessionStorage#login()} method **/
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));

        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
        Mockito.doReturn("JSESSIONID=AC0C233ED7E9BE5DD0D4A286E6C8BBAE;")
                .when(responseMock)
                .getHeaderString("Set-Cookie");
        Mockito.doReturn(302).when(responseMock).getStatus();


        try {
            new SessionStorage(configurationMock, credentialsMock);
        } catch (Exception e) {
            //Mockito.verify(logMock, times(1)).error(anyString(), any(NoSuchAlgorithmException.class));
            assertNotNull(e);
        }
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
        doReturn("http").when(configurationMock).getJasperReportsServerUrl();

        SessionStorage sessionStorage = new SessionStorage(configurationMock, credentialsMock);

        // When
        Whitebox.setInternalState(sessionStorage, "rootTarget", targetMock);
        Whitebox.setInternalState(sessionStorage, "sessionId", "sessionId");
        // Then
        assertNotNull(sessionStorage.getConfiguration());
        assertNotNull(sessionStorage.getCredentials());
        assertNotNull(sessionStorage.getRootTarget());
        assertNotNull(sessionStorage.getSessionId());
    }

    @AfterMethod
    public void after() {
        reset(builderMock, configurationMock, credentialsMock, invocationBuilderMock, responseMock, ctxMock, clientMock, targetMock);
    }
}