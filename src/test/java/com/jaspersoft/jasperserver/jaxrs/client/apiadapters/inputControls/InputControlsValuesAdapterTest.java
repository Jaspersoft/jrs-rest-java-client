package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
public class InputControlsValuesAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<InputControlStateListWrapper> requestMock;
    @Mock
    private OperationResult<InputControlStateListWrapper> operationResultMock;

    private String uri = "uri";

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // Given
        InputControlsValuesAdapter adapter = new InputControlsValuesAdapter(sessionStorageMock, uri);
        // When
        SessionStorage retrieved = adapter.getSessionStorage();
        // Then
        assertSame(retrieved, sessionStorageMock);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new InputControlsValuesAdapter(null, uri);
        // Then
        // should be thrown an exception
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_uri_is_null() {
        // When
        new InputControlsValuesAdapter(sessionStorageMock, null);
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_operation_result_when_invoke_get() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputControlStateListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls", "values"}))
            ).thenReturn(requestMock);

            doReturn(operationResultMock)
                    .when(requestMock)
                    .get();

            InputControlsValuesAdapter adapterSpy = spy(new InputControlsValuesAdapter(sessionStorageMock, uri));

            // When
            OperationResult<InputControlStateListWrapper> retrieved = adapterSpy.get();

            // Then
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputControlStateListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls", "values"})));

            Mockito.verify(requestMock).get();
            assertEquals(getInternalState(adapterSpy, "containerUri"), uri);
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_invoke_use_cashed_data() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputControlStateListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls", "values"}))
            ).thenReturn(requestMock);

            doReturn(requestMock)
                    .when(requestMock)
                    .addParam("freshData", "true");

            doReturn(operationResultMock)
                    .when(requestMock)
                    .get();

            InputControlsValuesAdapter adapterSpy = spy(new InputControlsValuesAdapter(sessionStorageMock, uri));
            adapterSpy.useCachedData(false);

            // When
            OperationResult<InputControlStateListWrapper> retrieved = adapterSpy.get();

            // Then
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputControlStateListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls", "values"})));

            Mockito.verify(requestMock).get();
            Mockito.verify(requestMock).addParam("freshData", "true");
            assertEquals(getInternalState(adapterSpy, "useFreshData"), Boolean.TRUE);
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, operationResultMock);
        mockitoSession.close();
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInternalState(Object target, String fieldName) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (T) field.get(target);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
