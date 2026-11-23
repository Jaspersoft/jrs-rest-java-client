package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertingState;
import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.AlertValidationErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.SingleAlertOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SingleAlertOperationsAdapter}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SingleAlertOperationsAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ClientReportAlert> alertRequestMock;
    @Mock
    private JerseyRequest objectJerseyRequestMock;
    @Mock
    private JerseyRequest<ClientAlertingState> alertStateJerseyRequestMock;
    @Mock
    private OperationResult<ClientAlertingState> alertStateOperationResultMock;
    @Mock
    private OperationResult<ClientReportAlert> alertOperationResultMock;
    @Mock
    private OperationResult operationResultMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private ClientReportAlert alertMock;

    private final String expectedAlertId = "3819";
    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, alertRequestMock, alertOperationResultMock, configurationMock);
        mockitoSession.close();
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleAlertOperationsAdapter_object() throws Exception {
        // When
        SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Field field = SingleAlertOperationsAdapter.class.getDeclaredField("jobId");
        field.setAccessible(true);
        String retrievedAlertId = (String) field.get(adapter);
        assertEquals(retrievedAlertId, expectedAlertId);
    }

    @Test(testName = "group")
    public void should_return_proper_Alert_operation_result_when_JRS_version_is_v5_6_1() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId}))).thenReturn(alertRequestMock);
            when(alertRequestMock.get()).thenReturn(alertOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);

            SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));
            OperationResult<ClientReportAlert> retrieved = adapter.getJob();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId})), times(1));
            verify(alertRequestMock, times(1)).get();
            verify(alertRequestMock, times(1)).setAccept("application/alert+xml");
            assertNotNull(retrieved);
            assertSame(retrieved, alertOperationResultMock);
        }
    }

    @Test
    public void should_return_proper_alert_operation_result_when_JRS_version_is_v4_7_0() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId}))).thenReturn(alertRequestMock);
            when(alertRequestMock.get()).thenReturn(alertOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);

            SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));
            OperationResult<ClientReportAlert> retrieved = adapter.getJob();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId})), times(1));
            verify(alertRequestMock, times(1)).get();
            verify(alertRequestMock, times(1)).setAccept("application/alert+json");
            assertNotNull(retrieved);
            assertSame(retrieved, alertOperationResultMock);
        }
    }

    @Test
    public void should_proper_op_result_instance() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientAlertingState.class),
                    eq(new String[]{"alerts", expectedAlertId, "state"}))).thenReturn(alertStateJerseyRequestMock);
            when(alertStateJerseyRequestMock.get()).thenReturn(alertStateOperationResultMock);

            SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));
            OperationResult<ClientAlertingState> retrieved = adapter.jobState();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientAlertingState.class),
                    eq(new String[]{"alerts", expectedAlertId, "state"})), times(1));
            verify(alertStateJerseyRequestMock, times(1)).get();
            assertSame(retrieved, alertStateOperationResultMock);
        }
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_5_6_1() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId}), any(AlertValidationErrorHandler.class))).thenReturn(alertRequestMock);
            when(alertRequestMock.post(alertMock)).thenReturn(alertOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);

            SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));
            OperationResult<ClientReportAlert> retrieved = adapter.update(alertMock);

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId}), any(AlertValidationErrorHandler.class)), times(1));
            verify(alertRequestMock, times(1)).post(alertMock);
            verify(alertRequestMock, times(1)).setContentType("application/alert+xml");
            verify(alertRequestMock, times(1)).setAccept("application/alert+xml");
            assertNotNull(retrieved);
            assertSame(retrieved, alertOperationResultMock);
        }
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_4_7_0() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId}), any(AlertValidationErrorHandler.class))).thenReturn(alertRequestMock);
            when(alertRequestMock.post(alertMock)).thenReturn(alertOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));
            OperationResult<ClientReportAlert> retrieved = adapter.update(alertMock);

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class), eq(new String[]{"alerts", expectedAlertId}), any(AlertValidationErrorHandler.class)), times(1));
            verify(alertRequestMock, times(1)).post(alertMock);
            verify(alertRequestMock, times(1)).setContentType("application/alert+json");
            verify(alertRequestMock, times(1)).setAccept("application/alert+json");
            assertNotNull(retrieved);
            assertSame(retrieved, alertOperationResultMock);
        }
    }

    @Test(testName = "delete")
    public void should_proper_op_result_instance_for_deleting_operation() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class),
                    eq(new String[]{"alerts", expectedAlertId}))).thenReturn(objectJerseyRequestMock);
            when(objectJerseyRequestMock.delete()).thenReturn(operationResultMock);

            SingleAlertOperationsAdapter adapter = spy(new SingleAlertOperationsAdapter(sessionStorageMock, expectedAlertId));
            OperationResult retrieved = adapter.delete();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"alerts", expectedAlertId})), times(1));
            verify(objectJerseyRequestMock, times(1)).delete();
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_RequestExecution_when_environment_is_specified() throws Exception {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class),
                    eq(new String[]{"alerts", "123435326"}), any(AlertValidationErrorHandler.class))).thenReturn(alertRequestMock);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(JRSVersion.v5_1_0).when(configurationMock).getJrsVersion();
            doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            doReturn(alertOperationResultMock).when(alertRequestMock).post(alertMock);

            SingleAlertOperationsAdapter adapterSpy = spy(new SingleAlertOperationsAdapter(sessionStorageMock, "123435326"));

            Callback<OperationResult<ClientReportAlert>, Void> callback = spy(new Callback<OperationResult<ClientReportAlert>, Void>() {
                public Void execute(OperationResult<ClientReportAlert> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            // When
            RequestExecution retrieved = adapterSpy.asyncUpdate(alertMock, callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            Assert.assertNotNull(retrieved);
            Assert.assertNotSame(currentThreadId, newThreadId.get());
            Mockito.verify(callback, times(1)).execute(alertOperationResultMock);
            InOrder inOrder = Mockito.inOrder(alertRequestMock);
            inOrder.verify(alertRequestMock, times(1)).setContentType("application/alert+json");
            inOrder.verify(alertRequestMock, times(1)).setAccept("application/alert+json");
            inOrder.verify(alertRequestMock, times(1)).post(alertMock);
        }
    }

    @Test
    public void should_return_RequestExecution_when_environment_is_specified_with_5_6_1_jrs_server_version() throws Exception {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class),
                    eq(new String[]{"alerts", "123435326"}), any(AlertValidationErrorHandler.class))).thenReturn(alertRequestMock);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();
            doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
            doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
            doReturn(alertOperationResultMock).when(alertRequestMock).post(alertMock);

            SingleAlertOperationsAdapter adapterSpy = spy(new SingleAlertOperationsAdapter(sessionStorageMock, "123435326"));

            Callback<OperationResult<ClientReportAlert>, Void> callback = spy(new Callback<OperationResult<ClientReportAlert>, Void>() {
                public Void execute(OperationResult<ClientReportAlert> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            // When
            RequestExecution retrieved = adapterSpy.asyncUpdate(alertMock, callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            Assert.assertNotNull(retrieved);
            Assert.assertNotSame(currentThreadId, newThreadId.get());
            Mockito.verify(callback, times(1)).execute(alertOperationResultMock);
            InOrder inOrder = Mockito.inOrder(alertRequestMock);
            inOrder.verify(alertRequestMock).setContentType("application/alert+xml");
            inOrder.verify(alertRequestMock).setAccept("application/alert+xml");
            inOrder.verify(alertRequestMock, times(1)).post(alertMock);
        }
    }

    @Test
    public void should_invoke_asynchronous_method_get_and_return_RequestExecution_with_Future() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class),
                    eq(new String[]{"alerts", "123435326"}))).thenReturn(alertRequestMock);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            doReturn(alertOperationResultMock).when(alertRequestMock).get();

            SingleAlertOperationsAdapter adapterSpy = spy(new SingleAlertOperationsAdapter(sessionStorageMock, "123435326"));

            Callback<OperationResult<ClientReportAlert>, Void> callback = spy(new Callback<OperationResult<ClientReportAlert>, Void>() {
                public Void execute(OperationResult<ClientReportAlert> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            // When
            RequestExecution retrieved = adapterSpy.asyncGetJob(callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            Assert.assertNotNull(retrieved);
            Assert.assertNotSame(currentThreadId, newThreadId.get());
            Mockito.verify(callback, times(1)).execute(alertOperationResultMock);
            InOrder inOrder = Mockito.inOrder(alertRequestMock);
            inOrder.verify(alertRequestMock).setAccept("application/alert+json");
            inOrder.verify(alertRequestMock, times(1)).get();
        }
    }

    @Test
    public void should_invoke_asynchronous_method_get_and_return_RequestExecution_with_Future_when_jrs_version_is_lower_Then_5_5_0() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportAlert.class),
                    eq(new String[]{"alerts", "123435326"}))).thenReturn(alertRequestMock);
            doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
            doReturn(JRSVersion.v4_7_0).when(configurationMock).getJrsVersion();
            doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
            doReturn(alertOperationResultMock).when(alertRequestMock).get();

            SingleAlertOperationsAdapter adapterSpy = spy(new SingleAlertOperationsAdapter(sessionStorageMock, "123435326"));

            Callback<OperationResult<ClientReportAlert>, Void> callback = spy(new Callback<OperationResult<ClientReportAlert>, Void>() {
                public Void execute(OperationResult<ClientReportAlert> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            // When
            RequestExecution retrieved = adapterSpy.asyncGetJob(callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            Assert.assertNotNull(retrieved);
            Assert.assertNotSame(currentThreadId, newThreadId.get());
            Mockito.verify(callback, times(1)).execute(alertOperationResultMock);
            InOrder inOrder = Mockito.inOrder(alertRequestMock);
            inOrder.verify(alertRequestMock).setAccept("application/alert+json");
            inOrder.verify(alertRequestMock, times(1)).get();
        }
    }

    @Test
    public void should_delete_job_asynchronously() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class),
                    eq(new String[]{"alerts", "123435326"}))).thenReturn(objectJerseyRequestMock);
            doReturn(operationResultMock).when(objectJerseyRequestMock).delete();

            SingleAlertOperationsAdapter adapterSpy = spy(new SingleAlertOperationsAdapter(sessionStorageMock, "123435326"));

            Callback<OperationResult, Void> callback = spy(new Callback<OperationResult, Void>() {
                public Void execute(OperationResult data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            // When
            RequestExecution retrieved = adapterSpy.asyncDelete(callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            Assert.assertNotNull(retrieved);
            Assert.assertNotSame(currentThreadId, newThreadId.get());
            Mockito.verify(objectJerseyRequestMock, times(1)).delete();
            Mockito.verify(callback, times(1)).execute(operationResultMock);
        }
    }

    @Test
    public void should_retrieve_state_asynchronously() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientAlertingState.class),
                    eq(new String[]{"alerts", "123435326", "state"}))).thenReturn(alertStateJerseyRequestMock);
            doReturn(alertStateOperationResultMock).when(alertStateJerseyRequestMock).get();

            SingleAlertOperationsAdapter adapterSpy = spy(new SingleAlertOperationsAdapter(sessionStorageMock, "123435326"));

            Callback<OperationResult<ClientAlertingState>, Void> callback = spy(new Callback<OperationResult<ClientAlertingState>, Void>() {
                public Void execute(OperationResult<ClientAlertingState> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            // When
            RequestExecution retrieved = adapterSpy.asyncJobState(callback);

            // Wait
            synchronized (callback) {
                callback.wait(1000);
            }

            // Then
            Assert.assertNotNull(retrieved);
            Assert.assertNotSame(currentThreadId, newThreadId.get());
            Mockito.verify(alertStateJerseyRequestMock, times(1)).get();
            Mockito.verify(callback, times(1)).execute(alertStateOperationResultMock);
        }
    }
}
