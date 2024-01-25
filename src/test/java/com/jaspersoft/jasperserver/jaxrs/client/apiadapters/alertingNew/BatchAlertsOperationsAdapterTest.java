package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew;

import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientAlertSummariesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.BatchAlertsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.*;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({JerseyRequest.class})
public class BatchAlertsOperationsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientAlertSummariesListWrapper> alertSummaryListWrapperJerseyRequest;

    @Mock
    private OperationResult<ClientAlertSummariesListWrapper> alertSummaryListWrapperOperationResult;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private ClientReportAlert alertMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleAlertOperationsAdapter_object() throws IllegalAccessException {

        // When
        BatchAlertsOperationsAdapter adapter = new BatchAlertsOperationsAdapter(sessionStorageMock);

        // Then
        assertEquals(Whitebox.getInternalState(adapter, "sessionStorage"), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(adapter, "params"), new MultivaluedHashMap<String, String>());
    }

    @Test
    public void should_return_all_jobs() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientAlertSummariesListWrapper.class),
                eq(new String[]{"alerts"}))).thenReturn(alertSummaryListWrapperJerseyRequest);
        when(alertSummaryListWrapperJerseyRequest.get()).thenReturn(alertSummaryListWrapperOperationResult);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);

        // When
        BatchAlertsOperationsAdapter adapter = spy(new BatchAlertsOperationsAdapter(sessionStorageMock));
        OperationResult<ClientAlertSummariesListWrapper> retrieved = adapter.searchAlerts();

        // Then
        verifyStatic(JerseyRequest.class, times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientAlertSummariesListWrapper.class), eq(new String[]{"alerts"}));
        verify(alertSummaryListWrapperJerseyRequest, times(1)).get();
        assertNotNull(retrieved);
        assertSame(retrieved, alertSummaryListWrapperOperationResult);

    }

}
