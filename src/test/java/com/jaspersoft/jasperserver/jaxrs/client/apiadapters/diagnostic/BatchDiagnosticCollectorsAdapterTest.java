package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettingsList;
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

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
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
public class BatchDiagnosticCollectorsAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<CollectorSettingsList> jerseyRequestMock;
    @Mock
    private OperationResult<CollectorSettingsList> operationResultMock;
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

    @Test
    public void should_set_proper_session_storage() {
        BatchDiagnosticCollectorsAdapter adapter = new BatchDiagnosticCollectorsAdapter(sessionStorageMock);
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_operation_result_when_get_collectors_metadata() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettingsList.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).get();

            OperationResult<CollectorSettingsList> retrieved =
                    new BatchDiagnosticCollectorsAdapter(sessionStorageMock).collectorsSettings();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettingsList.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).get();
        }
    }

    @Test
    public void should_return_proper_operation_result_when_delete_collectors() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettingsList.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(operationResultMock).when(jerseyRequestMock).delete();

            OperationResult<CollectorSettingsList> retrieved =
                    new BatchDiagnosticCollectorsAdapter(sessionStorageMock).delete();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettingsList.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).delete();
        }
    }

    @Test
    public void should_return_proper_operation_result_when_update_collectors_settings() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettingsList.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(jerseyRequestMock).when(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
            doReturn(operationResultMock).when(jerseyRequestMock).post(any(PatchDescriptor.class));

            OperationResult<CollectorSettingsList> retrieved =
                    new BatchDiagnosticCollectorsAdapter(sessionStorageMock).updateCollectorsSettings(new PatchDescriptor());

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(CollectorSettingsList.class),
                    eq(new String[]{"diagnostic", "collectors"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
            verify(jerseyRequestMock).post(any(PatchDescriptor.class));
        }
    }

    @Test
    public void should_return_proper_operation_result_when_get_collector_content() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputStream.class),
                    eq(new String[]{"diagnostic", "collectors", "content"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestContentMock);
            doReturn(operationResultContentMock).when(jerseyRequestContentMock).get();

            OperationResult<InputStream> retrieved =
                    new BatchDiagnosticCollectorsAdapter(sessionStorageMock).collectorsContent();

            assertNotNull(retrieved);
            assertSame(retrieved, operationResultContentMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(InputStream.class),
                    eq(new String[]{"diagnostic", "collectors", "content"}),
                    any(DefaultErrorHandler.class)));
            verify(jerseyRequestContentMock).setAccept("application/zip");
            verify(jerseyRequestContentMock).get();
        }
    }
}
