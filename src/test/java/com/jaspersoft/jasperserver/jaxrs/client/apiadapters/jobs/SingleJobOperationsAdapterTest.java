package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.JRSVersion;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.Job;
import com.jaspersoft.jasperserver.jaxrs.client.dto.jobs.JobState;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.SingleJobOperationsAdapter}
 * @deprecated
 */
@PrepareForTest({JerseyRequest.class})
public class SingleJobOperationsAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<Job> jobRequestMock;

    @Mock
    private JerseyRequest objectJerseyRequestMock;

    @Mock
    private JerseyRequest<JobState> jobStateJerseyRequestMock;

    @Mock
    private OperationResult<JobState> jobStateOperationResultMock;

    @Mock
    private OperationResult<Job> jobOperationResultMock;

    @Mock
    private OperationResult operationResultMock;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private Job jobMock;

    private final String expectedJobId = "3819";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_create_proper_SingleJobOperationsAdapter_object() throws IllegalAccessException {

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Field field = field(SingleJobOperationsAdapter.class, "jobId");
        String retrievedJobId = (String) field.get(adapter);
        assertEquals(retrievedJobId, expectedJobId);
    }

    @Test(testName = "group")
    public void should_return_proper_job_operation_result_when_JRS_version_is_v5_6_1() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}))).thenReturn(jobRequestMock);
        when(jobRequestMock.get()).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.get();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}));
        verify(jobRequestMock, times(1)).get();
        verify(jobRequestMock, times(1)).setAccept("application/job+xml");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test
    public void should_return_proper_job_operation_result_when_JRS_version_is_v4_7_0() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}))).thenReturn(jobRequestMock);
        when(jobRequestMock.get()).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.get();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}));
        verify(jobRequestMock, times(1)).get();
        verify(jobRequestMock, times(1)).setAccept("application/job+json");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test
    public void should_proper_op_result_instance() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobState.class),
                eq(new String[]{"jobs", expectedJobId, "state"}))).thenReturn(jobStateJerseyRequestMock);
        when(jobStateJerseyRequestMock.get()).thenReturn(jobStateOperationResultMock);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<JobState> retrieved = adapter.state();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JobState.class),
                eq(new String[]{"jobs", expectedJobId, "state"}));
        verify(jobStateJerseyRequestMock, times(1)).get();
        assertSame(retrieved, jobStateOperationResultMock);
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_5_6_1() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
        when(jobRequestMock.post(jobMock)).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v5_6_1);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.XML);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.XML);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.update(jobMock);

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class));
        verify(jobRequestMock, times(1)).post(jobMock);
        verify(jobRequestMock, times(1)).setContentType("application/job+xml");
        verify(jobRequestMock, times(1)).setAccept("application/job+xml");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test(testName = "update")
    public void should_return_prepared_op_result_when_jrs_version_is_4_7_0() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);
        when(jobRequestMock.post(jobMock)).thenReturn(jobOperationResultMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getJrsVersion()).thenReturn(JRSVersion.v4_7_0);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult<Job> retrieved = adapter.update(jobMock);

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Job.class), eq(new String[]{"jobs", expectedJobId}), any(JobValidationErrorHandler.class));
        verify(jobRequestMock, times(1)).post(jobMock);
        verify(jobRequestMock, times(1)).setContentType("application/job+json");
        verify(jobRequestMock, times(1)).setAccept("application/job+json");

        assertNotNull(retrieved);
        assertSame(retrieved, jobOperationResultMock);
    }

    @Test(testName = "delete")
    public void should_proper_op_result_instance_for_deleting_operation() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class),
                eq(new String[]{"jobs", expectedJobId}))).thenReturn(objectJerseyRequestMock);
        when(objectJerseyRequestMock.delete()).thenReturn(operationResultMock);

        // When
        SingleJobOperationsAdapter adapter = spy(new SingleJobOperationsAdapter(sessionStorageMock, expectedJobId));
        OperationResult retrieved = adapter.delete();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"jobs", expectedJobId}));
        verify(objectJerseyRequestMock, times(1)).delete();
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_RequestExecution_when_environment_is_specified() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(Job.class),
                eq(new String[]{"jobs", "123435326"}),
                any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);

        SingleJobOperationsAdapter adapterSpy = PowerMockito.spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        Callback<OperationResult<Job>, Void> callback = PowerMockito.spy(new Callback<OperationResult<Job>, Void>() {
            public Void execute(OperationResult<Job> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(jobOperationResultMock).when(jobRequestMock).post(jobMock);
        PowerMockito.doReturn(null).when(callback).execute(jobOperationResultMock);
        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(JRSVersion.v5_1_0).when(configurationMock).getJrsVersion();

        InOrder inOrder = Mockito.inOrder(jobRequestMock);

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

        inOrder.verify(jobRequestMock, times(1)).setContentType("application/job+json");
        inOrder.verify(jobRequestMock, times(1)).setAccept("application/job+json");
        inOrder.verify(jobRequestMock, times(1)).post(jobMock);
    }

    @Test
    public void should_return_RequestExecution_when_environment_is_specified_with_5_6_1_jrs_server_version() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(Job.class),
                eq(new String[]{"jobs", "123435326"}),
                any(JobValidationErrorHandler.class))).thenReturn(jobRequestMock);

        SingleJobOperationsAdapter adapterSpy = PowerMockito.spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        Callback<OperationResult<Job>, Void> callback = PowerMockito.spy(new Callback<OperationResult<Job>, Void>() {
            public Void execute(OperationResult<Job> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(jobOperationResultMock).when(jobRequestMock).post(jobMock);
        PowerMockito.doReturn(null).when(callback).execute(jobOperationResultMock);
        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();

        InOrder inOrder = Mockito.inOrder(jobRequestMock);

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

        inOrder.verify(jobRequestMock).setContentType("application/job+xml");
        inOrder.verify(jobRequestMock).setAccept("application/job+xml");
        inOrder.verify(jobRequestMock, times(1)).post(jobMock);
    }

    @Test
    public void should_invoke_asynchronous_method_get_and_return_RequestExecution_with_Future() throws InterruptedException {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(Job.class),
                eq(new String[]{"jobs", "123435326"})))
                .thenReturn(jobRequestMock);

        SingleJobOperationsAdapter adapterSpy = PowerMockito.spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        Callback<OperationResult<Job>, Void> callback = PowerMockito.spy(new Callback<OperationResult<Job>, Void>() {
            public Void execute(OperationResult<Job> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(jobOperationResultMock).when(jobRequestMock).get();
        PowerMockito.doReturn(null).when(callback).execute(jobOperationResultMock);
        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(JRSVersion.v5_6_1).when(configurationMock).getJrsVersion();
        PowerMockito.doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        InOrder inOrder = Mockito.inOrder(jobRequestMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
        Mockito.verify(callback, times(1)).execute(jobOperationResultMock);

        inOrder.verify(jobRequestMock).setAccept("application/job+json");
        inOrder.verify(jobRequestMock, times(1)).get();
    }


    @Test
    public void should_invoke_asynchronous_method_get_and_return_RequestExecution_with_Future_when_jrs_version_is_lower_Then_5_5_0() throws InterruptedException {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(Job.class),
                eq(new String[]{"jobs", "123435326"})))
                .thenReturn(jobRequestMock);

        SingleJobOperationsAdapter adapterSpy = PowerMockito.spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        Callback<OperationResult<Job>, Void> callback = PowerMockito.spy(new Callback<OperationResult<Job>, Void>() {
            public Void execute(OperationResult<Job> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(jobOperationResultMock).when(jobRequestMock).get();
        PowerMockito.doReturn(null).when(callback).execute(jobOperationResultMock);
        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(JRSVersion.v4_7_0).when(configurationMock).getJrsVersion();
        //PowerMockito.doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();

        InOrder inOrder = Mockito.inOrder(jobRequestMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
        Mockito.verify(callback, times(1)).execute(jobOperationResultMock);

        inOrder.verify(jobRequestMock).setAccept("application/job+json");
        inOrder.verify(jobRequestMock, times(1)).get();
    }


    @Test
    public void should_delete_job_asynchronously() throws InterruptedException {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(Object.class),
                eq(new String[]{"jobs", "123435326"})))
                .thenReturn(objectJerseyRequestMock);

        SingleJobOperationsAdapter adapterSpy = PowerMockito.spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        Callback<OperationResult, Void> callback = PowerMockito.spy(new Callback<OperationResult, Void>() {
            public Void execute(OperationResult data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(operationResultMock).when(objectJerseyRequestMock).delete();
        PowerMockito.doReturn(null).when(callback).execute(operationResultMock);

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

    @Test
    public void should_retrieve_state_asynchronously() throws InterruptedException {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(JobState.class),
                eq(new String[]{"jobs", "123435326", "state"})))
                .thenReturn(jobStateJerseyRequestMock);

        SingleJobOperationsAdapter adapterSpy = PowerMockito.spy(new SingleJobOperationsAdapter(sessionStorageMock, "123435326"));
        Callback<OperationResult<JobState>, Void> callback = PowerMockito.spy(new Callback<OperationResult<JobState>, Void>() {
            public Void execute(OperationResult<JobState> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(jobStateOperationResultMock).when(jobStateJerseyRequestMock).get();
        PowerMockito.doReturn(null).when(callback).execute(jobStateOperationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncState(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
        Mockito.verify(jobStateJerseyRequestMock, times(1)).get();
        Mockito.verify(callback, times(1)).execute(jobStateOperationResultMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, jobRequestMock, jobOperationResultMock, configurationMock);
    }
}