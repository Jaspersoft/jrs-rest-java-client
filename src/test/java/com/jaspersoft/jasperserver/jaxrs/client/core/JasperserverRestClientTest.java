package com.jaspersoft.jasperserver.jaxrs.client.core;

import java.lang.reflect.Field;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import static org.testng.Assert.assertEquals;

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