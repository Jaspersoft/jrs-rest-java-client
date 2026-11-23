package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew;

import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientAlertSummariesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.BatchAlertsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.lang.reflect.Field;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
public class BatchAlertsOperationsAdapterTest {

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

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
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

    @Test(testName = "constructor")
    public void should_create_proper_SingleAlertOperationsAdapter_object() throws Exception {
        BatchAlertsOperationsAdapter adapter = new BatchAlertsOperationsAdapter(sessionStorageMock);

        assertSame(getInternalState(adapter, "sessionStorage"), sessionStorageMock);
        assertEquals(getInternalState(adapter, "params"), new MultivaluedHashMap<String, String>());
    }

    @Test
    public void should_return_all_jobs() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientAlertSummariesListWrapper.class),
                    eq(new String[]{"alerts"}))).thenReturn(alertSummaryListWrapperJerseyRequest);
            when(alertSummaryListWrapperJerseyRequest.addParams(org.mockito.ArgumentMatchers.any()))
                    .thenReturn(alertSummaryListWrapperJerseyRequest);
            when(alertSummaryListWrapperJerseyRequest.get()).thenReturn(alertSummaryListWrapperOperationResult);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);

            BatchAlertsOperationsAdapter adapter = spy(new BatchAlertsOperationsAdapter(sessionStorageMock));
            OperationResult<ClientAlertSummariesListWrapper> retrieved = adapter.searchAlerts();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientAlertSummariesListWrapper.class),
                    eq(new String[]{"alerts"})), times(1));
            verify(alertSummaryListWrapperJerseyRequest, times(1)).get();
            assertNotNull(retrieved);
            assertSame(retrieved, alertSummaryListWrapperOperationResult);
        }
    }
}
