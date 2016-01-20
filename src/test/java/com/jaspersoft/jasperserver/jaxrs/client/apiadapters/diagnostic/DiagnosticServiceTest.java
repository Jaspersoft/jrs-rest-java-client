package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
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
@PrepareForTest({DiagnosticService.class, SingleDiagnosticCollectorAdapter.class, BatchDiagnosticCollectorsAdapter.class})
public class DiagnosticServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private BatchDiagnosticCollectorsAdapter batchAdapterMock;
    @Mock
    private SingleDiagnosticCollectorAdapter singleAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        SessionStorage retrieved = service.getSessionStorage();
        //Then
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_in_parent_adapter() {
        // When
        new DiagnosticService(null);
        //Then
        // exception should be thrown
    }

    @Test
    public void should_return_proper_single_adapter_by_collector_settings_instance() throws Exception {

        // Given
        CollectorSettings collector = new CollectorSettings();
        whenNew(SingleDiagnosticCollectorAdapter.class).withArguments(sessionStorageMock, collector).thenReturn(singleAdapterMock);

        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        SingleDiagnosticCollectorAdapter retrieved = service.forCollector(collector);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, singleAdapterMock);
        verifyNew(SingleDiagnosticCollectorAdapter.class, times(1)).withArguments(sessionStorageMock, collector);
    }

    @Test
    public void should_return_proper_single_adapter_by_collector_settings_id() throws Exception {

        // Given
        CollectorSettings collectorSettingsMock = mock(CollectorSettings.class);
        collectorSettingsMock.setId("id");
        whenNew(CollectorSettings.class).withNoArguments().thenReturn(collectorSettingsMock);
        whenNew(SingleDiagnosticCollectorAdapter.class).withArguments(sessionStorageMock, collectorSettingsMock).thenReturn(singleAdapterMock);

        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        SingleDiagnosticCollectorAdapter retrieved = service.forCollector("id");

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, singleAdapterMock);
        verifyNew(SingleDiagnosticCollectorAdapter.class, times(1)).withArguments(sessionStorageMock, collectorSettingsMock);
    }

    @Test
    public void should_invoke_proper_constructor_and_pass_proper_session_storage_instance() throws Exception {

        // Given
        whenNew(BatchDiagnosticCollectorsAdapter.class).withArguments(sessionStorageMock).thenReturn(batchAdapterMock);

        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        BatchDiagnosticCollectorsAdapter retrieved = service.allCollectors();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, batchAdapterMock);
        verifyNew(BatchDiagnosticCollectorsAdapter.class, times(1)).withArguments(sessionStorageMock);
    }


    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_collector_instance_is_null() throws Exception {

        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        service.forCollector((CollectorSettings) null);

        // Then
        //exception should be thrown
    }


    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_collector_id_is_null() throws Exception {

        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        service.forCollector((String) null);

        // Then
        //exception should be thrown
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_collector_id_is_empty() throws Exception {

        // When
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        service.forCollector("");

        // Then
        //exception should be thrown
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleAdapterMock, batchAdapterMock);
    }

}