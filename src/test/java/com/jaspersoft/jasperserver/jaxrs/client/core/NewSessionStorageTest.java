package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Alexander Krasnyanskiy
 */
@PrepareForTest({ClientBuilder.class, SSLContext.class, EncryptionUtils.class})
public class NewSessionStorageTest extends PowerMockTestCase {

    @Mock
    public RestClientConfiguration configurationMock;

    @Mock
    public AuthenticationCredentials credentialsMock;

    @Mock
    public WebTarget targetMock;
    @Mock
    public ClientBuilder builderMock;
    @Mock
    public Client clientMock;
    @Mock
    public Invocation.Builder invocationBuilderMock;
    @Mock
    public Response responseMock;
    @Mock
    public Response.StatusType statusTypeMock;

    @Mock
    public SSLContext sslContextMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

//    @Test(enabled = true)
//    public void should_throw_exception_while_login_attempt() {
//
//        /* Given */
//        /** - mock for static method **/
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
//        Mockito.doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
//        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
//        Mockito.doReturn(clientMock).when(builderMock).build();
//        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");
//
//        /** - mocks and dummies for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
//        Map<String, String> paramsSpy = Mockito.spy(new HashMap<String, String>() {{
//            put("e", "e_");
//            put("n", "n_");
//        }});
//
//        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
//        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(paramsSpy);
//        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");
//
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
//        Mockito.doReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
//        }}).when(responseMock).getCookies();
//
//        /** - mocks for {@link SessionStorage#login()} method **/
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
//                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
//
//        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.doReturn("JSESSIONID=AC0C233ED7E9BE5DD0D4A286E6C8BBAE;")
//                .when(responseMock)
//                .getHeaderString("Set-Cookie");
//        Mockito.doReturn(404).when(responseMock).getStatus();
//
//        Mockito.doReturn(statusTypeMock).when(responseMock).getStatusInfo();
//        Mockito.doReturn("phase_").when(statusTypeMock).getReasonPhrase();
//
//
//        /* When */
//        try {
//            new SessionStorage(configurationMock, credentialsMock);
//        } catch (Exception e) {
//            assertTrue(instanceOf(ResourceNotFoundException.class).matches(e));
//        }
//    }

//    @Test
//    public void should_throw_exception_when_Location_in_Response_ends_with_error() {
//
//        /* Given */
//        /** - mock for static method **/
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
//        Mockito.doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
//        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
//        Mockito.doReturn(clientMock).when(builderMock).build();
//        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");
//
//        /** - mocks and dummies for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
//        Map<String, String> paramsSpy = Mockito.spy(new HashMap<String, String>() {{
//            put("e", "e_");
//            put("n", "n_");
//        }});
//
//        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
//        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(paramsSpy);
//        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");
//
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
//        Mockito.doReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
//        }}).when(responseMock).getCookies();
//
//        /** - mocks for {@link SessionStorage#login()} method **/
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
//                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
//
//        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.doReturn(null).when(responseMock).getHeaderString("Set-Cookie");
//        Mockito.doReturn("/uri/error=1").when(responseMock).getHeaderString("Location");
//        Mockito.doReturn(302).when(responseMock).getStatus();
//
//
//        /* Check */
//        try {
//            new SessionStorage(configurationMock, credentialsMock);
//        } catch (Exception e) {
//            assertTrue(instanceOf(AuthenticationFailedException.class).matches(e));
//        }
//    }
//
//    @Test
//    public void should_throw_exception_when_not_enable_to_obtain_session() {
//
//        /* Given */
//        /** - mock for static method **/
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
//        Mockito.doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
//        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
//        Mockito.doReturn(clientMock).when(builderMock).build();
//        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");
//
//        /** - mocks and dummies for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
//        Map<String, String> paramsSpy = Mockito.spy(new HashMap<String, String>() {{
//            put("e", "e_");
//            put("n", "n_");
//        }});
//
//        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
//        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(paramsSpy);
//        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");
//
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
//        Mockito.doReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
//        }}).when(responseMock).getCookies();
//
//        /** - mocks for {@link SessionStorage#login()} method **/
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
//                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
//
//        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.doReturn(null).when(responseMock).getHeaderString("Set-Cookie");
//        Mockito.doReturn("/uri").when(responseMock).getHeaderString("Location");
//        Mockito.doReturn(302).when(responseMock).getStatus();
//
//
//        /* Check */
//        try {
//            new SessionStorage(configurationMock, credentialsMock);
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//            assertTrue(instanceOf(JSClientException.class).matches(e));
//        }
//    }
//
//    @Test
//    public void should_ssl_2() throws NoSuchAlgorithmException, KeyManagementException {
//
//        /* Given */
//        PowerMockito.mockStatic(SSLContext.class);
//        /*Power*/
//        Mockito.when(SSLContext.getInstance("SSL")).thenReturn(sslContextMock);
//        PowerMockito.suppress(method(SSLContext.class, "init", KeyManager[].class, TrustManager[].class, SecureRandom.class));
//
//        /** - mock for static method **/
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
//        Mockito.doReturn("https://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
//        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
//        Mockito.doReturn(clientMock).when(builderMock).build();
//        Mockito.doReturn(targetMock).when(clientMock).target("https://54.83.98.156/jasperserver-pro");
//
//        /** - mocks for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
//        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
//        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(null);
//        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");
//
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
//        Mockito.doReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
//        }}).when(responseMock).getCookies();
//
//        /** - mocks for {@link SessionStorage#login()} method **/
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
//                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
//
//        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.doReturn("JSESSIONID=AC0C233ED7E9BE5DD0D4A286E6C8BBAE;")
//                .when(responseMock)
//                .getHeaderString("Set-Cookie");
//        Mockito.doReturn(302).when(responseMock).getStatus();
//
//
//        /* When */
//        SessionStorage retrieved = new SessionStorage(configurationMock, credentialsMock);
//
//
//        /* Than */
//        /** - verification for {@link SessionStorage#init()} method **/
//        Mockito.verify(configurationMock, times(2)).getJasperReportsServerUrl();
//        Mockito.verify(configurationMock, times(1)).getConnectionTimeout();
//        Mockito.verify(configurationMock, times(1)).getReadTimeout();
//        Mockito.verify(builderMock, times(1)).build();
//        Mockito.verify(clientMock, times(2)).property(anyString(), anyInt());
//
//        /**
//         * - verification  for {@link SessionStorage#login()}
//         *   and partially for {@link SessionStorage#getSecurityAttributes()} methods
//         **/
//        Mockito.verify(targetMock, times(2)).path(anyString());
//        Mockito.verify(targetMock, times(2)).request();
//        Mockito.verify(invocationBuilderMock, times(1)).get();
//        Mockito.verify(responseMock, times(1)).getCookies();
//
//        Mockito.verify(targetMock, times(1)).property(anyString(), eq(Boolean.FALSE));
//        Mockito.verify(invocationBuilderMock, times(1)).cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.verify(invocationBuilderMock, times(1)).post(any(Entity.class));
//
//        /** - verification for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.verify(responseMock, times(1)).getHeaderString("Set-Cookie");
//        Mockito.verify(responseMock, times(1)).getStatus();
//
//        /** - verification for {@link SessionStorage#initSSL(ClientBuilder)} ()} method**/
//        Mockito.verify(builderMock).sslContext(sslContextMock);
//        Mockito.verify(builderMock).hostnameVerifier(any(HostnameVerifier.class));
//
//        /** general result checking **/
//        assertNotNull(retrieved);
//    }
//
//    @Test
//    public void all_getters_should_return_proper_values() {
//
//        /* Given */
//        /** - mock for static method **/
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
//        Mockito.doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
//        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
//        Mockito.doReturn(clientMock).when(builderMock).build();
//        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");
//
//        /** - mocks for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
//        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
//        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(null);
//        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");
//
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
//        Mockito.doReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
//        }}).when(responseMock).getCookies();
//
//        /** - mocks for {@link SessionStorage#login()} method **/
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
//                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
//
//        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.doReturn("JSESSIONID=AC0C233ED7E9BE5DD0D4A286E6C8BBAE;")
//                .when(responseMock)
//                .getHeaderString("Set-Cookie");
//        Mockito.doReturn(302).when(responseMock).getStatus();
//
//
//        /* When */
//        SessionStorage retrieved = new SessionStorage(configurationMock, credentialsMock);
//
//
//        /* Than */
//        assertSame(retrieved.getConfiguration(), configurationMock);
//        assertSame(retrieved.getCredentials(), credentialsMock);
//        assertSame(retrieved.getRootTarget(), targetMock);
//        assertEquals(retrieved.getSessionId(), "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//    }

    @Test(enabled = true)
    public void should_ssl() {

        /* Given */
        /** - mock for logger **/
//        PowerMockito.mockStatic(LogFactory.class);
//        PowerMockito.when(LogFactory.getLog(SessionStorage.class)).thenReturn(logMock);

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
//            Mockito.verify(logMock, times(1)).error(anyString(), any(NoSuchAlgorithmException.class));
        }
    }

//    @Test
//    public void should_x() {
//
//        /* Given */
//        /** - mock for static method **/
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//
//        /** - mocks for {@link SessionStorage#init()} method (no SSL) **/
//        Mockito.doReturn("http://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//        Mockito.doReturn(10000).when(configurationMock).getConnectionTimeout();
//        Mockito.doReturn(8000).when(configurationMock).getReadTimeout();
//        Mockito.doReturn(clientMock).when(builderMock).build();
//        Mockito.doReturn(targetMock).when(clientMock).target("http://54.83.98.156/jasperserver-pro");
//
//        /** - mocks for {@link SessionStorage#getSecurityAttributes()} method (no SSL) **/
//        Mockito.doReturn("Alex").when(credentialsMock).getUsername();
//        Mockito.doReturn("oh, yeah!").when(credentialsMock).getPassword();
//
//        PowerMockito.mockStatic(EncryptionUtils.class);
//        PowerMockito.when(EncryptionUtils.parseEncryptionParams(responseMock)).thenReturn(null);
//        PowerMockito.when(EncryptionUtils.encryptPassword("oh, yeah!", "n_", "e_")).thenReturn("encryptPassword");
//
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).get();
//        Mockito.doReturn(new HashMap<String, NewCookie>() {{
//            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
//        }}).when(responseMock).getCookies();
//
//        /** - mocks for {@link SessionStorage#login()} method **/
//        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
//        Mockito.doReturn(targetMock).when(targetMock).property(anyString(), eq(Boolean.FALSE));
//        Mockito.doReturn(invocationBuilderMock).when(targetMock).request();
//        Mockito.doReturn(invocationBuilderMock).when(invocationBuilderMock)
//                .cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
//        Mockito.doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
//
//        /** - mocks for {@link SessionStorage#parseSessionId()} method **/
//        Mockito.doReturn("JSESSIONID=AC0C233ED7E9BE5DD0D4A286E6C8BBAE;")
//                .when(responseMock)
//                .getHeaderString("Set-Cookie");
//        Mockito.doReturn(404).when(responseMock).getStatus();
//
//        Mockito.doReturn(statusTypeMock).when(responseMock).getStatusInfo();
//        Mockito.doReturn("phrase_").when(statusTypeMock).getReasonPhrase();
//
//        try {
//            new SessionStorage(configurationMock, credentialsMock);
//        } catch (Exception e) {
//            assertEquals(e.getMessage(), "phrase_");
//            assertNotNull(e);
//        }
//    }
//
//    @Test
//    public void should_Name() {
//
//        /* Given */
//        PowerMockito.mockStatic(ClientBuilder.class);
//        Mockito.when(ClientBuilder.newBuilder()).thenReturn(builderMock);
//        Mockito.doReturn("https://54.83.98.156/jasperserver-pro").when(configurationMock).getJasperReportsServerUrl();
//
//        try {
//            new SessionStorage(configurationMock, credentialsMock);
//        } catch (Exception e) {
//            assertNotNull(e);
//            assertEquals(e.getMessage(), "Unable to init SSL context");
//        }
//    }


    @AfterMethod
    public void after() {
        reset(configurationMock, credentialsMock, builderMock, invocationBuilderMock, clientMock,
                responseMock, targetMock, /*logMock,*/ sslContextMock, statusTypeMock);
    }
}
