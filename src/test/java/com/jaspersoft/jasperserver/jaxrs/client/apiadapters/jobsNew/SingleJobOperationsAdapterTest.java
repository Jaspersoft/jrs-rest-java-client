package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobsNew;

import com.jaspersoft.jasperserver.dto.job.ClientJobState;
import com.jaspersoft.jasperserver.dto.job.ClientReportJob;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobValidationErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SingleJobOperationsAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<ClientReportJob> jobRequestMock;
    @Mock
    private JerseyRequest objectJerseyRequestMock;
    @Mock
    private JerseyRequest<ClientJobState> jobStateJerseyRequestMock;
    @Mock
    private OperationResult<ClientJobState> jobStateOperationResultMock;
    @Mock
    private OperationResult<ClientReportJob> jobOperationResultMock;
    @Mock
    private OperationResult operationResultMock;
    @Mock
    private RestClientConfiguration configurationMock;
    @Mock
    private ClientReportJob jobMock;

    private final String expectedJobId = "3819";
    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, jobRequestMock, jobOperationResultMock, configurationMock);
        mockitoSession.close();
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleJobOperationsAdapter_object() throws Exception {
        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Field field = SingleJobOperationsAdapter.class.getDeclaredField("jobId");
        field.setAccessible(true);
        String retrievedJobId = (String) field.get(adapter);
        assertEquals(retrievedJobId, expectedJobId);
    }

    @Test(testName = "group")
    public void should_return_proper_job_operation_result_when_JRS_version_is_v5_6_1() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId}))).thenReturn(jobRequestMock);
            when(jobRequestMock.get()).thenReturn(jobOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);

            SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
            OperationResult<ClientReportJob> retrieved = adapter.getJob();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId})), times(1));
            verify(jobRequestMock, times(1)).get();
            verify(jobRequestMock, times(1)).setAccept("application/job+xml");
            assertNotNull(retrieved);
            assertSame(retrieved, jobOperationResultMock);
        }
    }

    @Test
    public void should_return_proper_job_operation_result_when_JRS_version_is_v4_7_0() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId}))).thenReturn(jobRequestMock);
            when(jobRequestMock.get()).thenReturn(jobOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);

            SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
            OperationResult<ClientReportJob> retrieved = adapter.getJob();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId})), times(1));
            verify(jobRequestMock, times(1)).get();
            verify(jobRequestMock, times(1)).setAccept("application/job+json");
            assertNotNull(retrieved);
            assertSame(retrieved, jobOperationResultMock);
        }
    }

    @Test
    public void should_proper_op_result_instance() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientJobState.class),
                    eq(new String[]{"jobs", expectedJobId, "state"}))).thenReturn(jobStateJerseyRequestMock);
            when(jobStateJerseyRequestMock.get()).thenReturn(jobStateOperationResultMock);

            SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
            OperationResult<ClientJobState> retrieved = adapter.jobState();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientJobState.class),
                    eq(new String[]{"jobs", expectedJobId, "state"})), times(1));
            verify(jobStateJerseyRequestMock, times(1)).get();
            assertSame(retrieved, jobStateOperationResultMock);
        }
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_5_6_1() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
            when(jobRequestMock.post(jobMock)).thenReturn(jobOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);

            SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
            OperationResult<ClientReportJob> retrieved = adapter.update(jobMock);

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class)), times(1));
            verify(jobRequestMock, times(1)).post(jobMock);
            verify(jobRequestMock, times(1)).setContentType("application/job+xml");
            verify(jobRequestMock, times(1)).setAccept("application/job+xml");
            assertNotNull(retrieved);
            assertSame(retrieved, jobOperationResultMock);
        }
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_4_7_0() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
            when(jobRequestMock.post(jobMock)).thenReturn(jobOperationResultMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

            SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
            OperationResult<ClientReportJob> retrieved = adapter.update(jobMock);

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientReportJob.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class)), times(1));
            verify(jobRequestMock, times(1)).post(jobMock);
            verify(jobRequestMock, times(1)).setContentType("application/job+json");
            verify(jobRequestMock, times(1)).setAccept("application/job+json");
            assertNotNull(retrieved);
            assertSame(retrieved, jobOperationResultMock);
        }
    }

    @Test(testName = "delete")
    public void should_proper_op_result_instance_for_deleting_operation() {
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class),
                    eq(new String[]{"jobs", expectedJobId}))).thenReturn(objectJerseyRequestMock);
            when(objectJerseyRequestMock.delete()).thenReturn(operationResultMock);

            SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
            OperationResult retrieved = adapter.delete();

            mockedStatic.verify(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"jobs", expectedJobId})), times(1));
            verify(objectJerseyRequestMock, times(1)).delete();
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_return_RequestExecution_when_environment_is_specified() throws Exception {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleJobOperationsAdapter adapterSpy = spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(JRSVersion.v5_1_0).when(configurationMock).getJrsVersion();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(jobOperationResultMock).when(adapterSpy).update(jobMock);

        Callback<OperationResult<ClientReportJob>, Void> callback = spy(new Callback<OperationResult<ClientReportJob>, Void>() {
            public Void execute(OperationResult<ClientReportJob> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        // When
        RequestExecution retrieved = adapterSpy.asyncUpdate(jobMock, callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
        Mockito.verify(callback, times(1)).execute(jobOperationResultMock);
        Mockito.verify(adapterSpy, times(1)).update(jobMock);
    }

    @Test
    public void should_return_RequestExecution_when_environment_is_specified_with_5_6_1_jrs_server_version() throws Exception {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleJobOperationsAdapter adapterSpy = spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();
        doReturn(MimeType.XML).when(configurationMock).getContentMimeType();
        doReturn(MimeType.XML).when(configurationMock).getAcceptMimeType();
        doReturn(jobOperationResultMock).when(adapterSpy).update(jobMock);

        Callback<OperationResult<ClientReportJob>, Void> callback = spy(new Callback<OperationResult<ClientReportJob>, Void>() {
            public Void execute(OperationResult<ClientReportJob> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        // When
        RequestExecution retrieved = adapterSpy.asyncUpdate(jobMock, callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
        Mockito.verify(callback, times(1)).execute(jobOperationResultMock);
        Mockito.verify(adapterSpy, times(1)).update(jobMock);
    }

    @Test
    public void should_invoke_asynchronous_method_get_and_return_RequestExecution_with_Future() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleJobOperationsAdapter adapterSpy = spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(jobOperationResultMock).when(adapterSpy).getJob();

        Callback<OperationResult<ClientReportJob>, Void> callback = spy(new Callback<OperationResult<ClientReportJob>, Void>() {
            public Void execute(OperationResult<ClientReportJob> data) {
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
        Mockito.verify(callback, times(1)).execute(jobOperationResultMock);
        Mockito.verify(adapterSpy, times(1)).getJob();
    }

    @Test
    public void should_invoke_asynchronous_method_get_and_return_RequestExecution_with_Future_when_jrs_version_is_lower_Then_5_5_0() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleJobOperationsAdapter adapterSpy = spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(JRSVersion.v4_7_0).when(configurationMock).getJrsVersion();
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(jobOperationResultMock).when(adapterSpy).getJob();

        Callback<OperationResult<ClientReportJob>, Void> callback = spy(new Callback<OperationResult<ClientReportJob>, Void>() {
            public Void execute(OperationResult<ClientReportJob> data) {
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
        Mockito.verify(callback, times(1)).execute(jobOperationResultMock);
        Mockito.verify(adapterSpy, times(1)).getJob();
    }

    @Test
    public void should_delete_job_asynchronously() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleJobOperationsAdapter adapterSpy = spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        doReturn(operationResultMock).when(adapterSpy).delete();

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
        Mockito.verify(adapterSpy, times(1)).delete();
        Mockito.verify(callback, times(1)).execute(operationResultMock);
    }

    @Test
    public void should_retrieve_state_asynchronously() throws InterruptedException {
        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleJobOperationsAdapter adapterSpy = spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        doReturn(jobStateOperationResultMock).when(adapterSpy).jobState();

        Callback<OperationResult<ClientJobState>, Void> callback = spy(new Callback<OperationResult<ClientJobState>, Void>() {
            public Void execute(OperationResult<ClientJobState> data) {
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
        Mockito.verify(adapterSpy, times(1)).jobState();
        Mockito.verify(callback, times(1)).execute(jobStateOperationResultMock);
    }
}
