package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.filters.BasicAuthenticationFilter;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit tests for {@link JasperserverRestClient}
 */
@PrepareForTest({JasperserverRestClient.class, SessionStorage.class})
public class JasperserverRestClientTest extends PowerMockTestCase {

    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private AuthenticationCredentials credentialsMock;
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private Session sessionMock;
    @Mock
    AnonymousSession anonymousSessionMock;
    @Mock
    private WebTarget webTargetMock;
    @Mock
    private WebTarget rootTargetMock;
    @Mock
    private Invocation.Builder invocationBuilderMock;
    @Mock
    private Response responseMock;


    final String USER_NAME = "John";
    final String PASSWORD = "John's_super_secret_password";

    @BeforeMethod
    public void before() {
        initMocks(this);
        suppress(method(SessionStorage.class, "init"));
    }

    @Test(testName = "JasperserverRestClient_constructor")
    public void should_create_an_instance_of_JasperserverRestClient_with_proper_fields()
            throws IllegalAccessException {
        // When
        final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

        // Then
        final Field field = field(JasperserverRestClient.class, "configuration");
        final RestClientConfiguration retrieved = (RestClientConfiguration) field.get(client);

        assertEquals(retrieved, configurationMock);
    }

    @Test(testName = "JasperserverRestClient_constructor",
          expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_pass_null_param_to_the_constructor() {
        new JasperserverRestClient(null);
    }

    @Test(testName = "JasperserverRestClient_authenticate")
    public void should_return_null_when_username_is_null() {
        // Given
        JasperserverRestClient jasperserverRestClient = new JasperserverRestClient(configurationMock);
        // When
        Session session = jasperserverRestClient.authenticate(null, PASSWORD);
        // Then
        assertEquals(session, null);
    }
    @Test(testName = "JasperserverRestClient_authenticate")
    public void should_return_null_when_username_is_empty() {
        // Given
        JasperserverRestClient jasperserverRestClient = new JasperserverRestClient(configurationMock);
        // When
        Session session = jasperserverRestClient.authenticate("", PASSWORD);
        // Then
        assertEquals(session, null);
    }

    @Test(testName = "JasperserverRestClient_authenticate")
    public void should_return_null_when_password_is_null() {
        // Given
        JasperserverRestClient jasperserverRestClient = new JasperserverRestClient(configurationMock);
        // When
        Session session = jasperserverRestClient.authenticate(USER_NAME, null);
        // Then
        assertEquals(session, null);
    }
    @Test(testName = "JasperserverRestClient_authenticate")
    public void should_return_null_when_password_is_empty() {
        // Given
        JasperserverRestClient jasperserverRestClient = new JasperserverRestClient(configurationMock);
        // When
        Session session = jasperserverRestClient.authenticate(USER_NAME, "");
        // Then
        assertEquals(session, null);
    }


    @Test
    public void should_return_proper_Session_object() throws Exception {

        // Given
        final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
        final JasperserverRestClient spyClient = spy(client);

        whenNew(AuthenticationCredentials.class)
                .withArguments(USER_NAME, PASSWORD)
                .thenReturn(credentialsMock);

        whenNew(SessionStorage.class)
                .withArguments(configurationMock, credentialsMock)
                .thenReturn(sessionStorageMock);

        whenNew(Session.class)
                .withArguments(sessionStorageMock)
                .thenReturn(sessionMock);
        doNothing().when(spyClient).login(sessionStorageMock);

        // When
        Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD);

        // Then
        assertEquals(retrieved, sessionMock);
    }

    @Test
    public void should_involve_login_method_and_return_proper_session_object() throws Exception {
        // Given
        final URI location = new URI("location");
        final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

        whenNew(AuthenticationCredentials.class)
                .withArguments(USER_NAME, PASSWORD)
                .thenReturn(credentialsMock);

        whenNew(SessionStorage.class)
                .withArguments(configurationMock, credentialsMock)
                .thenReturn(sessionStorageMock);
        doReturn(credentialsMock).when(sessionStorageMock).getCredentials();
        doReturn(rootTargetMock).when(sessionStorageMock).getRootTarget();
        doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
        doReturn(USER_NAME).when(credentialsMock).getUsername();
        doReturn(PASSWORD).when(credentialsMock).getPassword();
        doReturn(webTargetMock).when(rootTargetMock).path(anyString());
        doReturn(webTargetMock).when(webTargetMock).property(anyString(), anyBoolean());
        doReturn(invocationBuilderMock).when(webTargetMock).request();
        doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
        doReturn(location).when(responseMock).getLocation();
        doReturn(302).when(responseMock).getStatus();
        doReturn(new HashMap<String, NewCookie>() {{
            put("JSESSIONID", new NewCookie(new Cookie("JSESSIONID", "AC0C233ED7E9BE5DD0D4A286E6C8BBAE")));
        }}).when(responseMock).getCookies();
        doReturn(rootTargetMock).when(rootTargetMock).register(any(SessionOutputFilter.class));

        // When
        Session session = client.authenticate(USER_NAME, PASSWORD);

        // Then
        assertNotNull(session);
        verify(sessionStorageMock).getCredentials();
        verify(sessionStorageMock).getRootTarget();
        verify(configurationMock).getAuthenticationType();
        verify(sessionStorageMock).getCredentials();
        verify(rootTargetMock, never()).register(isA(BasicAuthenticationFilter.class));
        verify(rootTargetMock).path("/j_spring_security_check");
        verify(webTargetMock).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
        verify(webTargetMock).request();
        verify(invocationBuilderMock).post(Entity.entity(any(Form.class), MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        verify(responseMock).getLocation();
        verify(responseMock).getCookies();
        verify(sessionStorageMock).setSessionId("AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
        verify(rootTargetMock).register(isA(SessionOutputFilter.class));
    }

    @Test
    public void should_involve_login_method_and_return_proper_session_object_within_basic_authorization() throws Exception {
        // Given
        final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

        whenNew(AuthenticationCredentials.class)
                .withArguments(USER_NAME, PASSWORD)
                .thenReturn(credentialsMock);

        whenNew(SessionStorage.class)
                .withArguments(configurationMock, credentialsMock)
                .thenReturn(sessionStorageMock);
        doReturn(credentialsMock).when(sessionStorageMock).getCredentials();
        doReturn(rootTargetMock).when(sessionStorageMock).getRootTarget();
        doReturn(AuthenticationType.BASIC).when(configurationMock).getAuthenticationType();
        doReturn(USER_NAME).when(credentialsMock).getUsername();
        doReturn(PASSWORD).when(credentialsMock).getPassword();
        doReturn(rootTargetMock).when(rootTargetMock).register(isA(BasicAuthenticationFilter.class));

        // When
        Session session = client.authenticate(USER_NAME, PASSWORD);

        // Then
        assertNotNull(session);
        verify(sessionStorageMock).getCredentials();
        verify(sessionStorageMock).getRootTarget();
        verify(configurationMock).getAuthenticationType();
        verify(sessionStorageMock).getCredentials();
        verify(rootTargetMock).register(isA(BasicAuthenticationFilter.class));
        verify(rootTargetMock, never()).path("/j_spring_security_check");
        verify(webTargetMock, never()).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
        verify(webTargetMock, never()).request();
        verify(invocationBuilderMock, never()).post(Entity.entity(any(Form.class), MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        verify(rootTargetMock, never()).register(isA(SessionOutputFilter.class));
    }

    @Test
    public void should_return_proper_anonymousSession_object() throws Exception {

        // Given
        final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

        whenNew(SessionStorage.class)
                .withAnyArguments()
                .thenReturn(sessionStorageMock);
        whenNew(AnonymousSession.class)
                .withArguments(sessionStorageMock)
                .thenReturn(anonymousSessionMock);

        // When
        AnonymousSession retrieved = client.getAnonymousSession();

        // Then
        assertEquals(retrieved, anonymousSessionMock);
    }

    @AfterMethod
    public void after() {
        reset(configurationMock, credentialsMock, sessionStorageMock, sessionMock);
    }
}