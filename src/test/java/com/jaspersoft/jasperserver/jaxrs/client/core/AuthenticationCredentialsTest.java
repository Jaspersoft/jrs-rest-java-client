package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;

/**
 * Unit tests for {@link AuthenticationCredentials}
 */
@PrepareForTest(AuthenticationCredentials.class)
public class AuthenticationCredentialsTest extends PowerMockTestCase {

    final private String USERNAME = "John";
    final private String PASSWORD = "John's_very_secret_password";

    @Mock
    private AuthenticationCredentials credentialsMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_create_AuthenticationCredentials_object_with_proper_params() {
        // When
        AuthenticationCredentials credentials = new AuthenticationCredentials(USERNAME, PASSWORD);

        // Then
        assertNotNull(credentials);
        assertEquals(credentials.getUsername(), USERNAME);
        assertEquals(credentials.getPassword(), PASSWORD);
    }

    @Test
    public void should_fire_username_getter_only_one_time() throws Exception {
        // Given
        whenNew(AuthenticationCredentials.class)
                .withArguments(USERNAME, PASSWORD)
                .thenReturn(credentialsMock);

        // When
        credentialsMock.getUsername();

        // Then
        verify(credentialsMock, times(1)).getUsername();
    }

    @Test
    public void should_fire_password_getter_only_one_time() throws Exception {
        // Given
        whenNew(AuthenticationCredentials.class)
                .withArguments(USERNAME, PASSWORD)
                .thenReturn(credentialsMock);

        // When
        credentialsMock.getPassword();

        // Then
        verify(credentialsMock, times(1)).getPassword();
    }

    @Test
    public void should_set_password_with_proper_value() {
        // Given
        AuthenticationCredentials credentials = new AuthenticationCredentials(USERNAME, PASSWORD);

        // When
        credentials.setPassword("new_password");

        // Then
        String retrieved = getInternalState(credentials, "password");
        assertEquals(retrieved, "new_password");
    }

    @Test
    public void should_set_username_with_proper_value() {
        // Given
        AuthenticationCredentials credentials = new AuthenticationCredentials(USERNAME, PASSWORD);

        // When
        credentials.setUsername("Tom");

        // Then
        String retrieved = getInternalState(credentials, "username");
        assertEquals(retrieved, "Tom");
        assertNotSame(retrieved, "Mike");
    }

    @AfterMethod
    public void after() {
        reset(credentialsMock);
    }
}