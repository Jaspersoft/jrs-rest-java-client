package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.importexport.State;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter}
 */
@PrepareForTest({JerseyRequest.class, ExportRequestAdapter.class, ExportFailedException.class, Thread.class})
public class ExportRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private State stateMock;

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<State> requestStateDtoMock;

    @Mock
    private JerseyRequest<InputStream> requestInputStreamMock;

    @Mock
    private OperationResult<State> operationResultStateDtoMock;

    @Mock
    private OperationResult<InputStream> operationResultInputStreamMock;

    @Mock
    private Callback<OperationResult<InputStream>, Object> callback;

    @Mock
    private RequestBuilder<InputStream> streamRequestBuilderMock;

    @Mock
    private Callback<OperationResult<State>, Object> operationResultObjectCallback;

    @Mock
    private ErrorDescriptor descriptorMock;

    private ExportRequestAdapter adapterSpy;
    private String taskId = "njkhfs8374";
    private String[] fakeArrayPath = new String[]{"/export", taskId, "/state"};

    @BeforeMethod
    public void before() {
        initMocks(this);
        adapterSpy = Mockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));
    }

    @Test(testName = "constructor")
    public void should_pass_corresponding_params_to_the_constructor_and_invoke_parent_class_constructor_with_these_params()
            throws IllegalAccessException {

        // When
        ExportRequestAdapter adapter = new ExportRequestAdapter(sessionStorageMock, taskId);
        SessionStorage retrievedSessionStorage = adapter.getSessionStorage();

        Field field = field(ExportRequestAdapter.class, "taskId");
        Object retrievedField = field.get(adapter);

        // Then
        assertSame(retrievedField, taskId);
        assertEquals(retrievedSessionStorage, sessionStorageMock);
    }

    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock, State.class, fakeArrayPath)).thenReturn(requestStateDtoMock);
        when(requestStateDtoMock.get()).thenReturn(operationResultStateDtoMock);

        // When
        ExportRequestAdapter adapter = new ExportRequestAdapter(sessionStorageMock, taskId);
        OperationResult<State> opResult = adapter.state();

        // Then
        assertSame(opResult, operationResultStateDtoMock);
    }

    @Test(testName = "fetch")
    public void should_retrieve_streamed_OperationResult_object_when_status_is_finished() {

        // Given
        mockStatic(JerseyRequest.class);
        doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        doReturn("finished").when(stateMock).getPhase();

        when(JerseyRequest.buildRequest(sessionStorageMock, InputStream.class,
                new String[]{"/export", taskId, "/exportFile"})).thenReturn(requestInputStreamMock);

        doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();

        // When
        OperationResult<InputStream> retrieved = adapterSpy.fetch();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultInputStreamMock);
    }

    @Test(enabled = false)
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter#asyncFetch(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_fetch_an_export_as_IS_when_job_finished() throws Exception {

        // Given
        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/export", taskId, "/exportFile"})))
                .thenReturn(requestInputStreamMock);

        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(State.class),
                eq(new String[]{"/export", taskId, "/state"})))
                .thenReturn(requestStateDtoMock);

        PowerMockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();
        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();
        PowerMockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        PowerMockito.doReturn("finished").when(stateMock).getPhase();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        Mockito.verify(callbackSpy).execute(operationResultInputStreamMock);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);
    }

    @Test(enabled = false)
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter#asyncFetch(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_throw_an_exception_in_separate_thread() throws Exception {

        // Given
        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/export", taskId, "/exportFile"})))
                .thenReturn(requestInputStreamMock);

        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(State.class),
                eq(new String[]{"/export", taskId, "/state"})))
                .thenReturn(requestStateDtoMock);

        PowerMockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();
        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();
        PowerMockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        PowerMockito.doReturn(null).when(stateMock).getError();
        PowerMockito.doReturn("msg").when(stateMock).getMessage();

        PowerMockito
                .doReturn("inprogress")
                .doReturn("failed") // task was failed
                .when(stateMock)
                .getPhase();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        Mockito.verify(callbackSpy, never()).execute(operationResultInputStreamMock);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter#asyncState(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_invoke_method_logic_asynchronously() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        PowerMockito.when(
                buildRequest(
                        eq(sessionStorageMock),
                        eq(State.class),
                        eq(new String[]{"/export", taskId, "/state"})))
                .thenReturn(requestStateDtoMock);

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportRequestAdapter taskAdapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        final Callback<OperationResult<State>, Void> callback = spy(new Callback<OperationResult<State>, Void>() {
            public Void execute(OperationResult<State> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();
        PowerMockito.doReturn(null).when(callback).execute(operationResultStateDtoMock);

        // When
        RequestExecution retrieved = taskAdapterSpy.asyncState(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        Assert.assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultStateDtoMock);
        verify(requestStateDtoMock, times(1)).get();
    }

    @Test
    public void should_invoke_method_logic_asynchronously_with_() throws Exception {

        /* Given */
        mockStatic(JerseyRequest.class);
        PowerMockito.doReturn(requestStateDtoMock).when(JerseyRequest.class, "buildRequest",
                eq(sessionStorageMock),
                eq(State.class),
                eq(new String[]{"/export", "task77", "/state"}));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ExportRequestAdapter requestAdapterSpy = new ExportRequestAdapter(sessionStorageMock, "task77");
        Callback<OperationResult<State>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<State>, Void>() {
            @Override
            public Void execute(OperationResult<State> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();
        PowerMockito.doReturn(null).when(callbackSpy).execute(operationResultStateDtoMock);

        /* When */
        RequestExecution retrieved = requestAdapterSpy.asyncState(callbackSpy);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(callbackSpy, times(1)).execute(operationResultStateDtoMock);
        Mockito.verify(requestStateDtoMock, times(1)).get();
    }


    @Test(enabled = false)
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter#asyncFetch(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_throw_an_exception_in_separate_thread_and_when_there_is_ErrorDescriptor() throws Exception {

        // Given
        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));
        ErrorDescriptor descriptorMock = PowerMockito.mock(ErrorDescriptor.class);

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/export", taskId, "/exportFile"})))
                .thenReturn(requestInputStreamMock);

        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(State.class),
                eq(new String[]{"/export", taskId, "/state"})))
                .thenReturn(requestStateDtoMock);

        PowerMockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();
        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();
        PowerMockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        PowerMockito.doReturn(descriptorMock).when(stateMock).getError();
        PowerMockito.doReturn("msg").when(stateMock).getMessage();

        PowerMockito
                .doReturn("failed")
                .doReturn("failed") // task was failed
                .when(stateMock)
                .getPhase();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        Mockito.verify(callbackSpy, never()).execute(operationResultInputStreamMock);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);
    }

    @Test(enabled = false)
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportRequestAdapter#asyncFetch(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_fetch_entity_when_second_thread_interrupter() throws Exception {

        /* Given */
        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));
        ErrorDescriptor descriptorMock = PowerMockito.mock(ErrorDescriptor.class);

        PowerMockito.mockStatic(JerseyRequest.class, Thread.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/export", taskId, "/exportFile"})))
                .thenReturn(requestInputStreamMock);

        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(State.class),
                eq(new String[]{"/export", taskId, "/state"})))
                .thenReturn(requestStateDtoMock);

        PowerMockito.doThrow(new InterruptedException()).when(Thread.class);
        Thread.sleep(500L);

        PowerMockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();
        PowerMockito.doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();
        PowerMockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        PowerMockito.doReturn(descriptorMock).when(stateMock).getError();
        PowerMockito.doReturn("msg").when(stateMock).getMessage();

        PowerMockito
                .doReturn("inprogress")
                .doReturn("inprogress")
                .doReturn("finished")
                .when(stateMock)
                .getPhase();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        /* When */
        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        /* Then */
        Mockito.verify(callbackSpy, times(1)).execute(operationResultInputStreamMock);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);
    }

    @Test
    public void should_throw_an_exception_when_fetching_failed() throws Exception {

        /* Given */
        ExportRequestAdapter adapterSpy = Mockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));
        ErrorDescriptor descriptorMock = Mockito.mock(ErrorDescriptor.class);

        Mockito.doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        Mockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        Mockito.doReturn("failed").doReturn("failed").when(stateMock).getPhase();
        Mockito.doReturn(descriptorMock).when(stateMock).getError();

        /* When */
        try {
            adapterSpy.fetch();
        } catch (ExportFailedException e) {

            /* Then */
            verify(adapterSpy, times(1)).state();
            verify(stateMock, times(2)).getPhase();
            assertNotNull(e);
            assertEquals(e.getMessage(), null);
        }
    }

    @Test
    public void should_throw_an_exception_when_fetching_failed_and_there_is_no_ErrorDescriptor() throws Exception {

        /* Given */
        ExportRequestAdapter adapterSpy = Mockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        Mockito.doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        Mockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        Mockito.doReturn("failed").doReturn("failed").when(stateMock).getPhase();
        Mockito.doReturn(null).when(stateMock).getError();

        /* When */
        try {
            adapterSpy.fetch();
        } catch (ExportFailedException e) {

            /* Then */
            verify(adapterSpy, times(1)).state();
            verify(stateMock, times(2)).getPhase();
            assertNotNull(e);
            assertEquals(e.getMessage(), null);
        }
    }


    @Test
    public void should_fetch_streamed_OperationResult() {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class, Thread.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class),
                eq(new String[]{"/export", taskId, "/exportFile"})))
                .thenReturn(requestInputStreamMock);

        ExportRequestAdapter adapterSpy = Mockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        Mockito.doReturn(operationResultStateDtoMock).when(adapterSpy).state();
        Mockito.doReturn(operationResultInputStreamMock).when(requestInputStreamMock).get();
        Mockito.doReturn(stateMock).when(operationResultStateDtoMock).getEntity();
        Mockito.doReturn("inprogress").doReturn("inprogress").doReturn("finished").when(stateMock).getPhase();

        /* When */

        OperationResult<InputStream> result = adapterSpy.fetch();

        /* Then */
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/export", taskId, "/exportFile"}));

        verify(requestInputStreamMock, times(1)).setAccept("application/zip");
        verify(requestInputStreamMock, times(1)).get();
    }

    @Test
    public void should_fetch_export_asynchronously() throws InterruptedException {

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        // ...
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/export", taskId, "/exportFile"}))).thenReturn(requestInputStreamMock);

        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        PowerMockito
                .doReturn(operationResultInputStreamMock)
                .when(adapterSpy)
                .state();
        PowerMockito
                .doReturn(operationResultInputStreamMock)
                .when(requestInputStreamMock)
                .get();
        PowerMockito
                .doReturn(stateMock)
                .when(operationResultInputStreamMock)
                .getEntity();
        PowerMockito
                .doReturn("inprogress")
                .doReturn("inprogress")
                .doReturn("finished")
                .when(stateMock)
                .getPhase();


        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        /* When */
        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(5000);
        }

        /* Then */
        Mockito.verify(callbackSpy, times(1)).execute(operationResultInputStreamMock);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);

        Mockito.verify(stateMock, times(3)).getPhase();
        Mockito.verify(operationResultInputStreamMock, times(2)).getEntity();
        Mockito.verify(requestInputStreamMock, times(1)).get();
    }

    @Test
    public void should_throw_exception_while_fetching_resource_() throws InterruptedException {

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        // ...
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/export", taskId, "/exportFile"}))).thenReturn(requestInputStreamMock);

        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        PowerMockito
                .doReturn(operationResultInputStreamMock)
                .when(adapterSpy)
                .state();
        PowerMockito
                .doReturn(operationResultInputStreamMock)
                .when(requestInputStreamMock)
                .get();
        PowerMockito
                .doReturn(stateMock)
                .when(operationResultInputStreamMock)
                .getEntity();
        PowerMockito
                .doReturn("failed")
                .doReturn("failed")
                .when(stateMock)
                .getPhase();
        PowerMockito
                .doReturn("msg_")
                .when(stateMock)
                .getMessage();


        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        /* When */
        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        /* Then */
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);

        try {
            retrieved.getFuture().get();
        } catch (ExecutionException e) {
            assertEquals(e.getMessage(), "com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException: msg_");
        }
    }

    @Test
    public void should_throw_exception_while_fetching_resource() throws InterruptedException {

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        // ...
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(InputStream.class), eq(new String[]{"/export", taskId, "/exportFile"}))).thenReturn(requestInputStreamMock);

        ExportRequestAdapter adapterSpy = PowerMockito.spy(new ExportRequestAdapter(sessionStorageMock, taskId));

        PowerMockito
                .doReturn(operationResultInputStreamMock)
                .when(adapterSpy)
                .state();
        PowerMockito
                .doReturn(operationResultInputStreamMock)
                .when(requestInputStreamMock)
                .get();
        PowerMockito
                .doReturn(stateMock)
                .when(operationResultInputStreamMock)
                .getEntity();
        PowerMockito
                .doReturn("failed")
                .doReturn("failed")
                .when(stateMock)
                .getPhase();
        PowerMockito
                .doReturn("msg_")
                .when(stateMock)
                .getMessage();
        PowerMockito
                .doReturn(descriptorMock)
                .when(stateMock)
                .getError();
        PowerMockito
                .doReturn("_msg")
                .when(descriptorMock)
                .getMessage();


        final Callback<OperationResult<InputStream>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<InputStream>, Void>() {
            @Override
            public Void execute(OperationResult<InputStream> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        /* When */
        RequestExecution retrieved = adapterSpy.asyncFetch(callbackSpy);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        /* Then */
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved);

        try {
            retrieved.getFuture().get();
        } catch (ExecutionException e) {
            assertEquals(e.getMessage(), "com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException: _msg");
        }
    }

    @AfterMethod
    public void after() {
        reset(stateMock, sessionStorageMock, requestStateDtoMock, requestInputStreamMock,
                operationResultStateDtoMock, operationResultInputStreamMock, callback, streamRequestBuilderMock,
                operationResultObjectCallback,descriptorMock);
    }
}



