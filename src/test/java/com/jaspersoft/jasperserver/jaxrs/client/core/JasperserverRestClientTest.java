package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.filters.BasicAuthenticationFilter;
import com.jaspersoft.jasperserver.jaxrs.client.filters.SessionOutputFilter;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit tests for {@link JasperserverRestClient}
 */
public class JasperserverRestClientTest {

    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private AuthenticationCredentials credentialsMock;
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private Session sessionMock;
    @Mock
    private AnonymousSession anonymousSessionMock;
    @Mock
    private WebTarget webTargetMock;
    @Mock
    private WebTarget rootTargetMock;
    @Mock
    private Invocation.Builder invocationBuilderMock;
    @Mock
    private Response responseMock;
    @Mock
    private DefaultErrorHandler errorHandler;
    @Captor
    private ArgumentCaptor<Entity<?>> entityArgumentCaptor;

    private AutoCloseable mockitoSession;

    final String USER_NAME = "John";
    final String PASSWORD = "John's_super_secret_password";
    final String TIME_ZONE = "Canada/Central";
    final String LOCALE = "de";

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test(testName = "JasperserverRestClient_constructor")
    public void should_create_an_instance_of_JasperserverRestClient_with_proper_fields()
            throws IllegalAccessException {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class)) {
            // When
            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            // Then
            Field field;
            try {
                field = JasperserverRestClient.class.getDeclaredField("configuration");
                field.setAccessible(true);
                final RestClientConfiguration retrieved = (RestClientConfiguration) field.get(client);
                assertEquals(retrieved, configurationMock);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test(testName = "JasperserverRestClient_constructor",
            expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_pass_null_param_to_the_constructor() {
        new JasperserverRestClient(null);
    }

    @Test(testName = "JasperserverRestClient_constructor",
            expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_pass_empty_configuration_to_the_constructor() {
        new JasperserverRestClient(new RestClientConfiguration());
    }

    @Test(testName = "JasperserverRestClient_authenticate")
    public void should_return_null_when_username_is_null() {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();
        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class)) {
            JasperserverRestClient jasperserverRestClient = new JasperserverRestClient(configurationMock);
            // When
            Session session = jasperserverRestClient.authenticate(null, PASSWORD);
            // Then
            assertEquals(session, null);
        }
    }

    @Test(testName = "JasperserverRestClient_authenticate")
    public void should_return_null_when_username_is_empty() {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();
        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class)) {
            JasperserverRestClient jasperserverRestClient = new JasperserverRestClient(configurationMock);
            // When
            Session session = jasperserverRestClient.authenticate("", PASSWORD);
            // Then
            assertEquals(session, null);
        }
    }

    @Test
    public void should_return_proper_Session_object_when_password_is_empty() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, "");

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_when_password_is_null() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, (String) null);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_as_object() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, TimeZone.getTimeZone(TIME_ZONE));

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_and_locale_as_objects() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, new Locale(LOCALE), TimeZone.getTimeZone(TIME_ZONE));

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_is_object_and_locale_is_null_object() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, (Locale) null, TimeZone.getTimeZone(TIME_ZONE));

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_is_object_and_locale_is_null_string() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, (String) null, TIME_ZONE);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone__is_nul_object_and_locale_object() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, new Locale(LOCALE), (TimeZone) null);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_is_null_string_and_locale_is_string() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, LOCALE, (String) null);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_as_string() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, TIME_ZONE);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_return_proper_Session_object_with_time_zone_and_locale_as_string() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(credentialsMock).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);
            final JasperserverRestClient spyClient = spy(client);
            doNothing().when(spyClient).login(any(SessionStorage.class));

            // When
            Session retrieved = spyClient.authenticate(USER_NAME, PASSWORD, LOCALE, TIME_ZONE);

            // Then
            assertEquals(retrieved, mockedSession.constructed().get(0));
        }
    }

    @Test
    public void should_invoke_login_method_and_return_proper_session_object() throws Exception {
        // Given
        final URI location = new URI("location");
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<AuthenticationCredentials> mockedCreds = org.mockito.Mockito.mockConstruction(AuthenticationCredentials.class,
                (mock, ctx) -> {
                    doReturn(USER_NAME).when(mock).getUsername();
                    doReturn(PASSWORD).when(mock).getPassword();
                });
             MockedConstruction<Form> mockedForm = org.mockito.Mockito.mockConstruction(Form.class,
                (mock, ctx) -> {
                    doReturn(mock).when(mock).param(anyString(), anyString());
                });
             MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(mockedCreds.constructed().isEmpty() ? credentialsMock : mockedCreds.constructed().get(0)).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                    doReturn(TimeZone.getDefault()).when(mock).getUserTimeZone();
                    doReturn(Locale.getDefault()).when(mock).getUserLocale();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
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
            SessionStorage storage = mockedStorage.constructed().get(0);
            verify(storage).getCredentials();
            verify(storage).getRootTarget();
            verify(rootTargetMock, never()).register(isA(BasicAuthenticationFilter.class));
            verify(rootTargetMock).path("/j_spring_security_check");
            verify(webTargetMock).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
            verify(webTargetMock).request();
            verify(invocationBuilderMock).post(entityArgumentCaptor.capture());
            Entity<?> entity = entityArgumentCaptor.getValue();
            assertEquals(entity.getMediaType(), MediaType.APPLICATION_FORM_URLENCODED_TYPE);
            assertSame(entity.getEntity().getClass(), Form.class);
            verify(responseMock).getLocation();
            verify(responseMock).getCookies();
            verify(storage).setSessionId("AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
            verify(rootTargetMock).register(isA(SessionOutputFilter.class));
        }
    }

    @Test
    public void should_invoke_login_method_and_return_proper_session_object_with_time_zone() throws Exception {
        // Given
        final URI location = new URI("location");
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<AuthenticationCredentials> mockedCreds = org.mockito.Mockito.mockConstruction(AuthenticationCredentials.class,
                (mock, ctx) -> {
                    doReturn(USER_NAME).when(mock).getUsername();
                    doReturn(PASSWORD).when(mock).getPassword();
                });
             MockedConstruction<Form> mockedForm = org.mockito.Mockito.mockConstruction(Form.class,
                (mock, ctx) -> {
                    doReturn(mock).when(mock).param(anyString(), anyString());
                });
             MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(mockedCreds.constructed().isEmpty() ? credentialsMock : mockedCreds.constructed().get(0)).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                    doReturn(TimeZone.getTimeZone(TIME_ZONE)).when(mock).getUserTimeZone();
                    doReturn(Locale.getDefault()).when(mock).getUserLocale();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
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
            Session session = client.authenticate(USER_NAME, PASSWORD, TIME_ZONE);

            // Then
            assertNotNull(session);
            Form capturedForm = mockedForm.constructed().get(0);
            verify(capturedForm).param("j_username", USER_NAME);
            verify(capturedForm).param("j_password", PASSWORD);
            verify(capturedForm).param("userTimezone", TIME_ZONE);
            verify(rootTargetMock, never()).register(isA(BasicAuthenticationFilter.class));
            verify(rootTargetMock).path("/j_spring_security_check");
            verify(webTargetMock).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
            verify(webTargetMock).request();
            verify(invocationBuilderMock).post(any(Entity.class));
            verify(responseMock).getLocation();
            verify(responseMock).getCookies();
            verify(mockedStorage.constructed().get(0)).setSessionId("AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
            verify(rootTargetMock).register(isA(SessionOutputFilter.class));
        }
    }

    @Test
    public void should_invoke_login_method_and_return_proper_session_object_with_time_zone_and_locale() throws Exception {
        // Given
        final URI location = new URI("location");
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();
        final Locale locale = new Locale("de");

        try (MockedConstruction<AuthenticationCredentials> mockedCreds = org.mockito.Mockito.mockConstruction(AuthenticationCredentials.class,
                (mock, ctx) -> {
                    doReturn(USER_NAME).when(mock).getUsername();
                    doReturn(PASSWORD).when(mock).getPassword();
                });
             MockedConstruction<Form> mockedForm = org.mockito.Mockito.mockConstruction(Form.class,
                (mock, ctx) -> {
                    doReturn(mock).when(mock).param(anyString(), anyString());
                });
             MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(mockedCreds.constructed().isEmpty() ? credentialsMock : mockedCreds.constructed().get(0)).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                    doReturn(TimeZone.getTimeZone(TIME_ZONE)).when(mock).getUserTimeZone();
                    doReturn(locale).when(mock).getUserLocale();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
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
            Session session = client.authenticate(USER_NAME, PASSWORD, "de", TIME_ZONE);

            // Then
            assertNotNull(session);
            Form capturedForm = mockedForm.constructed().get(0);
            verify(capturedForm).param("j_username", USER_NAME);
            verify(capturedForm).param("j_password", PASSWORD);
            verify(capturedForm).param("userTimezone", TIME_ZONE);
            verify(capturedForm).param("userLocale", locale.toString());
            verify(rootTargetMock, never()).register(isA(BasicAuthenticationFilter.class));
            verify(rootTargetMock).path("/j_spring_security_check");
            verify(webTargetMock).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
            verify(webTargetMock).request();
            verify(invocationBuilderMock).post(any(Entity.class));
            verify(responseMock).getLocation();
            verify(responseMock).getCookies();
            verify(mockedStorage.constructed().get(0)).setSessionId("AC0C233ED7E9BE5DD0D4A286E6C8BBAE");
            verify(rootTargetMock).register(isA(SessionOutputFilter.class));
        }
    }

    @Test(expectedExceptions = ResourceNotFoundException.class)
    public void should_invoke_login_method_and_throw_exception() throws Exception {
        // Given
        final URI location = new URI("location");
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();
        final Locale locale = new Locale("de");

        try (MockedConstruction<AuthenticationCredentials> mockedCreds = org.mockito.Mockito.mockConstruction(AuthenticationCredentials.class,
                (mock, ctx) -> {
                    doReturn(USER_NAME).when(mock).getUsername();
                    doReturn(PASSWORD).when(mock).getPassword();
                });
             MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(mockedCreds.constructed().isEmpty() ? credentialsMock : mockedCreds.constructed().get(0)).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                    doReturn(TimeZone.getTimeZone(TIME_ZONE)).when(mock).getUserTimeZone();
                    doReturn(locale).when(mock).getUserLocale();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
            doReturn(webTargetMock).when(rootTargetMock).path(anyString());
            doReturn(webTargetMock).when(webTargetMock).property(anyString(), anyBoolean());
            doReturn(invocationBuilderMock).when(webTargetMock).request();
            doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
            doReturn(location).when(responseMock).getLocation();
            doReturn(404).when(responseMock).getStatus();

            // When
            client.authenticate(USER_NAME, PASSWORD, "de", TIME_ZONE);

            // Then
            // an exception should be thrown
        }
    }

    @Test(expectedExceptions = AuthenticationFailedException.class)
    public void should_throw_authentication_failed_exception() throws Exception {
        // Given
        final URI location = new URI("http://location?error=1");
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();
        final Locale locale = new Locale("de");

        try (MockedConstruction<AuthenticationCredentials> mockedCreds = org.mockito.Mockito.mockConstruction(AuthenticationCredentials.class,
                (mock, ctx) -> {
                    doReturn(USER_NAME).when(mock).getUsername();
                    doReturn(PASSWORD).when(mock).getPassword();
                });
             MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(mockedCreds.constructed().isEmpty() ? credentialsMock : mockedCreds.constructed().get(0)).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                    doReturn(TimeZone.getTimeZone(TIME_ZONE)).when(mock).getUserTimeZone();
                    doReturn(locale).when(mock).getUserLocale();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
            doReturn(webTargetMock).when(rootTargetMock).path(anyString());
            doReturn(webTargetMock).when(webTargetMock).property(anyString(), anyBoolean());
            doReturn(invocationBuilderMock).when(webTargetMock).request();
            doReturn(responseMock).when(invocationBuilderMock).post(any(Entity.class));
            doReturn(location).when(responseMock).getLocation();
            doReturn(302).when(responseMock).getStatus();

            // When
            client.authenticate(USER_NAME, PASSWORD, "de", TIME_ZONE);

            // Then
            // an exception should be thrown
        }
    }

    @Test
    public void should_invoke_login_method_and_return_proper_session_object_within_basic_authorization() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<AuthenticationCredentials> mockedCreds = org.mockito.Mockito.mockConstruction(AuthenticationCredentials.class,
                (mock, ctx) -> {
                    doReturn(USER_NAME).when(mock).getUsername();
                    doReturn(PASSWORD).when(mock).getPassword();
                });
             MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class,
                (mock, ctx) -> {
                    doReturn(mockedCreds.constructed().isEmpty() ? credentialsMock : mockedCreds.constructed().get(0)).when(mock).getCredentials();
                    doReturn(rootTargetMock).when(mock).getRootTarget();
                    doReturn(TimeZone.getDefault()).when(mock).getUserTimeZone();
                });
             MockedConstruction<Session> mockedSession = org.mockito.Mockito.mockConstruction(Session.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            doReturn(AuthenticationType.BASIC).when(configurationMock).getAuthenticationType();
            doReturn(rootTargetMock).when(rootTargetMock).register(isA(BasicAuthenticationFilter.class));

            // When
            Session session = client.authenticate(USER_NAME, PASSWORD);

            // Then
            assertNotNull(session);
            SessionStorage storage = mockedStorage.constructed().get(0);
            verify(storage).getCredentials();
            verify(storage).getRootTarget();
            verify(rootTargetMock).register(isA(BasicAuthenticationFilter.class));
            verify(rootTargetMock, never()).path("/j_spring_security_check");
            verify(webTargetMock, never()).property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
            verify(webTargetMock, never()).request();
            verify(rootTargetMock, never()).register(isA(SessionOutputFilter.class));
        }
    }

    @Test
    public void should_return_proper_anonymousSession_object() throws Exception {
        // Given
        doReturn("url").when(configurationMock).getJasperReportsServerUrl();

        try (MockedConstruction<SessionStorage> mockedStorage = org.mockito.Mockito.mockConstruction(SessionStorage.class);
             MockedConstruction<AnonymousSession> mockedAnon = org.mockito.Mockito.mockConstruction(AnonymousSession.class)) {

            final JasperserverRestClient client = new JasperserverRestClient(configurationMock);

            // When
            AnonymousSession retrieved = client.getAnonymousSession();

            // Then
            assertNotNull(retrieved);
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(configurationMock, credentialsMock,
                sessionStorageMock, sessionMock,
                anonymousSessionMock, webTargetMock,
                rootTargetMock, invocationBuilderMock,
                responseMock);
        mockitoSession.close();
    }
}
