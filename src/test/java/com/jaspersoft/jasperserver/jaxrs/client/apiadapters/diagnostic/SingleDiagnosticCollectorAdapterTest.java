package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Field;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@SuppressWarnings("unchecked")
public class SingleDiagnosticCollectorAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<CollectorSettings> jerseyRequestMock;
    @Mock
    private OperationResult<CollectorSettings> operationResultMock;
    @Mock
    private JerseyRequest<InputStream> jerseyRequestContentMock;
    @Mock
    private OperationResult<InputStream> operationResultContentMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, jerseyRequestMock, jerseyRequestContentMock,
                operationResultMock, operationResultContentMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_set_proper_session_storage() {
        SingleDiagnosticCollectorAdapter adapter =
                new SingleDiagnosticCollectorAdapter(sessionStorageMock, new CollectorSettings());
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_set_proper_collector() throws Exception {
        CollectorSettings collectorSettings = new CollectorSettings();
        SingleDiagnosticCollectorAdapter adapter =
                new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettings);
        assertSame(getInternalState(adapter, "collector"), collectorSettings);
    }

    @Test
    public void should_return_proper_operation_result_when_create() {
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).post(collectorSettingsMock);

            OperationResult<CollectorSettings> retrieved =
                    new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock).create();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).post(collectorSettingsMock);
        }
    }

    @Test
    public void should_return_proper_operation_result_when_delete() {
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        doReturn("someId").when(collectorSettingsMock).getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).delete();

            OperationResult<CollectorSettings> retrieved =
                    new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock).delete();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).delete();
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_collector_metadata() {
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        doReturn("someId").when(collectorSettingsMock).getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            OperationResult<CollectorSettings> retrieved =
                    new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock).collectorSettings();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_operation_result_when_update_collector() {
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        doReturn("someId").when(collectorSettingsMock).getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).put(collectorSettingsMock);

            OperationResult<CollectorSettings> retrieved =
                    new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock)
                            .updateCollectorSettings(collectorSettingsMock);

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).put(collectorSettingsMock);
        }
    }

    @Test
    public void should_return_proper_operation_result_when_update_collector_settings() {
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        doReturn("someId").when(collectorSettingsMock).getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(jerseyRequestMock).when(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
            doReturn(operationResultMock).when(jerseyRequestMock).post(any(PatchDescriptor.class));

            OperationResult<CollectorSettings> retrieved =
                    new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock)
                            .updateCollectorSettings(new PatchDescriptor());

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettings.class),
                    eq(new String[]{"diagnostic", "collectors", "someId"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
            verify(jerseyRequestMock).post(any(PatchDescriptor.class));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_collector_content() {
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        doReturn("someId").when(collectorSettingsMock).getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputStream.class),
                    eq(new String[]{"diagnostic", "collectors", "someId", "content"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestContentMock);
            doReturn(operationResultContentMock).when(jerseyRequestContentMock).get();

            OperationResult<InputStream> retrieved =
                    new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock).collectorContent();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultContentMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputStream.class),
                    eq(new String[]{"diagnostic", "collectors", "someId", "content"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestContentMock).setAccept("application/zip");
            verify(jerseyRequestContentMock).get();
        }
    }
}
