package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;


import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettingsList;
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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
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
public class BatchDiagnosticCollectorsAdapterTest extends PowerMockTestCase {
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

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_set_proper_session_storage() {
        // When
        BatchDiagnosticCollectorsAdapter adapter = new BatchDiagnosticCollectorsAdapter(sessionStorageMock);
        //Then
        assertSame(Whitebox.getInternalState(adapter, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_operation_result_when_get_collectors_metadata() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettingsList.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).get();

        BatchDiagnosticCollectorsAdapter adapter = new BatchDiagnosticCollectorsAdapter(sessionStorageMock);

        // When
        OperationResult<CollectorSettingsList> retrieved = adapter.collectorsSettings();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettingsList.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_delete_collectors() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettingsList.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).delete();

        BatchDiagnosticCollectorsAdapter adapter = new BatchDiagnosticCollectorsAdapter(sessionStorageMock);

        // When /
        OperationResult<CollectorSettingsList> retrieved = adapter.delete();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).delete();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettingsList.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class));

    }

    @Test
    public void should_return_proper_operation_result_when_update_collectors_settings() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettingsList.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(jerseyRequestMock).when(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
        doReturn(operationResultMock).when(jerseyRequestMock).post(any(PatchDescriptor.class));

        BatchDiagnosticCollectorsAdapter adapter = new BatchDiagnosticCollectorsAdapter(sessionStorageMock);

        // When /
        OperationResult<CollectorSettingsList> retrieved = adapter.updateCollectorsSettings(new PatchDescriptor());

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
        verify(jerseyRequestMock).post(any(PatchDescriptor.class));
        verify(jerseyRequestMock).addHeader("X-HTTP-Method-Override", "PATCH");
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(CollectorSettingsList.class),
                eq(new String[]{"diagnostic", "collectors"}),
                any(DefaultErrorHandler.class));

    }


    @Test
    public void should_return_proper_operation_result_when_get_collector_content() {
        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"diagnostic", "collectors", "content"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestContentMock);
        doReturn(jerseyRequestContentMock).when(jerseyRequestContentMock).setAccept("application/zip");
        doReturn(operationResultContentMock).when(jerseyRequestContentMock).get();

        BatchDiagnosticCollectorsAdapter adapter = new BatchDiagnosticCollectorsAdapter(sessionStorageMock);

        // When /
        OperationResult<InputStream> retrieved = adapter.collectorsContent();

        // Then /
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultContentMock);
        verify(jerseyRequestContentMock).setAccept("application/zip");
        verify(jerseyRequestContentMock).get();
        verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(InputStream.class),
                eq(new String[]{"diagnostic", "collectors", "content"}),
                any(DefaultErrorHandler.class));

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jerseyRequestMock, jerseyRequestContentMock, operationResultMock, operationResultContentMock);
    }

}