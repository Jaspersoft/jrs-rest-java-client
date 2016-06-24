package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;


import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertSame;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
@PrepareForTest(JerseyRequest.class)
public class SingleDiagnosticCollectorAdapterTest extends PowerMockTestCase {
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

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_set_proper_session_storage() {
        // When
        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, new CollectorSettings());
        //Then
        assertSame(Whitebox.getInternalState(adapter, "sessionStorage"), sessionStorageMock);
    }


    @Test
    public void should_set_proper_collector() {
        // When
        CollectorSettings collectorSettings = new CollectorSettings();
        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettings);
        //Then
        assertSame(Whitebox.getInternalState(adapter, "collector"), collectorSettings);
    }

    @Test
    public void should_return_proper_operation_result_when_create() {
        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).post(collectorSettingsMock);

        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock);

        // When /
        OperationResult<CollectorSettings> retrieved = adapter.create();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).post(collectorSettingsMock);
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_delete() {
        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).delete();
        doReturn("someId").when(collectorSettingsMock).getId();

        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock);

        // When /
        OperationResult<CollectorSettings> retrieved = adapter.delete();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).delete();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class));

    }


    @Test
    public void should_return_proper_operation_result_when_get_collector_metadata() {
        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).get();
        doReturn("someId").when(collectorSettingsMock).getId();

        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock);

        // When /
        OperationResult<CollectorSettings> retrieved = adapter.collectorSettings();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_update_collector() {
        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).put(collectorSettingsMock);
        doReturn("someId").when(collectorSettingsMock).getId();

        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock);

        // When /
        OperationResult<CollectorSettings> retrieved = adapter.updateCollectorSettings(collectorSettingsMock);

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).put(collectorSettingsMock);
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_update_collector_settings() {
        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(jerseyRequestMock).when(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
        doReturn(operationResultMock).when(jerseyRequestMock).post(any(PatchDescriptor.class));
        doReturn("someId").when(collectorSettingsMock).getId();

        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock);

        // When /
        OperationResult<CollectorSettings> retrieved = adapter.updateCollectorSettings(new PatchDescriptor());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).post(any(PatchDescriptor.class));
        verify(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettings.class),
                eq(new String[]{"diagnostic", "collectors", "someId"}),
                any(DefaultErrorHandler.class));

    }


    @Test
    public void should_return_proper_operation_result_when_get_collector_content() {
        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"diagnostic", "collectors", "someId", "content"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestContentMock);
        doReturn(jerseyRequestContentMock).when(jerseyRequestContentMock).setAccept("application/zip");
        doReturn(operationResultContentMock).when(jerseyRequestContentMock).get();
        doReturn("someId").when(collectorSettingsMock).getId();

        SingleDiagnosticCollectorAdapter adapter = new SingleDiagnosticCollectorAdapter(sessionStorageMock, collectorSettingsMock);

        // When /
        OperationResult<InputStream> retrieved = adapter.collectorContent();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultContentMock);
        verify(jerseyRequestContentMock).setAccept("application/zip");
        verify(jerseyRequestContentMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"diagnostic", "collectors", "someId", "content"}),
                any(DefaultErrorHandler.class));

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jerseyRequestMock, jerseyRequestContentMock, operationResultMock, operationResultContentMock);
    }

}