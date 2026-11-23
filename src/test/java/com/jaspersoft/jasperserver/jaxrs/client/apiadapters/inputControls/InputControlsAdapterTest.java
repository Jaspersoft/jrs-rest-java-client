package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedList;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
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
public class InputControlsAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ReportInputControlsListWrapper> requestMock;
    @Mock
    private OperationResult<ReportInputControlsListWrapper> operationResultMock;
    @Mock
    private InputControlsValuesAdapter inputControlsValuesAdapterMock;

    private String uri = "uri";

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_uri_is_null() {
        // When
        new InputControlsAdapter(sessionStorageMock).forReport(null).get();
        // Then
        // should be thrown an exception
    }


    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // Given
        InputControlsAdapter adapter = new InputControlsAdapter(sessionStorageMock);
        // When
        SessionStorage retrieved = adapter.getSessionStorage();
        // Then
        assertSame(retrieved, sessionStorageMock);
    }


    @Test
    public void should_return_adapter_instance() throws Exception {
        // Given
        InputControlsAdapter adapter = new InputControlsAdapter(sessionStorageMock);

        try (MockedConstruction<InputControlsValuesAdapter> mockedAdapter =
                     Mockito.mockConstruction(InputControlsValuesAdapter.class)) {
            // When
            InputControlsValuesAdapter retrieved = adapter.forReport("/uri").values();
            // Then
            assertNotNull(retrieved);
            assertSame(retrieved, mockedAdapter.constructed().get(0));
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        // When
        new InputControlsAdapter(null);
        // Then
        // should be thrown an exception
    }

    @Test
    public void should_return_proper_operation_result_when_invoke_get() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ReportInputControlsListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls"}))
            ).thenReturn(requestMock);

            doReturn(operationResultMock)
                    .when(requestMock)
                    .get();

            InputControlsAdapter adapterSpy = spy(new InputControlsAdapter(sessionStorageMock));
            adapterSpy.forReport(uri);

            // When
            OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy.get();

            // Then
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ReportInputControlsListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls"})));

            Mockito.verify(requestMock).get();
            assertEquals(getInternalState(adapterSpy, "containerUri"), uri);
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_proper_operation_result_when_invoke_reorder() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ReportInputControlsListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls"}))
            ).thenReturn(requestMock);

            doReturn(operationResultMock)
                    .when(requestMock)
                    .put(any());

            InputControlsAdapter adapterSpy = spy(new InputControlsAdapter(sessionStorageMock));
            adapterSpy.forReport(uri);

            // When
            OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy
                    .reorder(new LinkedList<ReportInputControl>());

            // Then
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ReportInputControlsListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls"})));

            Mockito.verify(requestMock).put(any());
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_invoke_adding_parameter() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ReportInputControlsListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls"}))
            ).thenReturn(requestMock);

            doReturn(requestMock)
                    .when(requestMock)
                    .addParam("exclude", "state");
            doReturn(operationResultMock)
                    .when(requestMock)
                    .get();

            InputControlsAdapter adapterSpy = spy(new InputControlsAdapter(sessionStorageMock));
            adapterSpy.forReport(uri).excludeState(true);

            // When
            OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy.get();

            // Then
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ReportInputControlsListWrapper.class),
                    eq(new String[]{"reports", uri, "inputControls"})));

            Mockito.verify(requestMock).get();
            Mockito.verify(requestMock).addParam("exclude", "state");
            assertEquals(getInternalState(adapterSpy, "containerUri"), uri);
            assertEquals(getInternalState(adapterSpy, "excludeState"), Boolean.TRUE);
            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, operationResultMock, inputControlsValuesAdapterMock);
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
