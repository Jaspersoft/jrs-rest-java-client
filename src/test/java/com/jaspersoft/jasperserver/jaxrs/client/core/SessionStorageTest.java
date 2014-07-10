package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.WebTarget;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.method;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.powermock.reflect.Whitebox.setInternalState;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link SessionStorage}.
 */
@PrepareForTest(SessionStorage.class)
public class SessionStorageTest extends PowerMockTestCase {

    @Mock
    private RestClientConfiguration expectedClientConfiguration;

    @Mock
    private AuthenticationCredentials expectedCredentials;

    private SessionStorage sessionStorageSpy;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        suppress(method(SessionStorage.class, "init"));
        sessionStorageSpy = spy(new SessionStorage(expectedClientConfiguration, expectedCredentials));
    }

    @Test(testName = "SessionStorage_constructor")
    public void should_create_an_object_of_SessionStorage_class() throws Exception {

        // TODO: find out if is it possible to mock private method which invoked in the constructor body?

        // When
        RestClientConfiguration configurationRetrieved = sessionStorageSpy.getConfiguration();
        AuthenticationCredentials credentialsRetrieved = sessionStorageSpy.getCredentials();

        // Then
        assertEquals(configurationRetrieved, expectedClientConfiguration);
        assertEquals(credentialsRetrieved, expectedCredentials);
    }

    @Test(testName = "getConfiguration")
    public void should_return_setted_configuration() {
        RestClientConfiguration retrieved = sessionStorageSpy.getConfiguration();
        assertEquals(retrieved, expectedClientConfiguration);
    }

    @Test(testName = "getCredentials")
    public void should_return_setted_credentials() {
        AuthenticationCredentials retrieved = sessionStorageSpy.getCredentials();
        assertEquals(retrieved, expectedCredentials);
    }

    @Test(testName = "getSessionId")
    public void should_return_proper_sessionId() {

        // Given
        final String expected = "DB00FEE7DEAA46ECE3EC6668281C208E";
        setInternalState(sessionStorageSpy, "sessionId", expected);

        // When
        String retrieved = sessionStorageSpy.getSessionId();

        //Than
        assertEquals(retrieved, expected);
    }

    @Test(testName = "getRootTarget")
    public void should_return_proper_rootTarget() {

        // Given
        final WebTarget expected = mock(WebTarget.class);
        setInternalState(sessionStorageSpy, "rootTarget", expected);

        // When
        WebTarget retrieved = sessionStorageSpy.getRootTarget();

        // Than
        assertEquals(retrieved, expected);
    }

    @AfterMethod
    public void tearDown() {
        reset(expectedClientConfiguration, expectedCredentials);
    }
}