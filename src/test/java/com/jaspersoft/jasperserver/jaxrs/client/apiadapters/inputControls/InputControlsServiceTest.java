package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private InputControlsAdapter adapterMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // Given
        InputControlsService service = new InputControlsService(sessionStorageMock);
        // When
        SessionStorage retrieved = service.getSessionStorage();
        // Then
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new InputControlsService(null);
        // Then
        // should be thrown an exception
    }


    @Test
    public void should_return_adapter_instance() {
        // Given
        InputControlsService serviceSpy = spy(new InputControlsService(sessionStorageMock));
        doReturn(adapterMock).when(serviceSpy).inputControls();
        // When
        InputControlsAdapter retried = serviceSpy.inputControls();

        // Then
        assertEquals(adapterMock, retried);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, adapterMock);
        mockitoSession.close();
    }
}
