package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;
import com.jaspersoft.jasperserver.dto.importexport.State;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ExportFailedException;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
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
    private String[] fakeArrayPath = new String[]{"export", taskId, "state"};

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
                        eq(new String[]{"export", taskId, "state"})))
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
        PowerMockito.doNothing().when(callback).execute(operationResultStateDtoMock);

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
                eq(new String[]{"export", "task77", "state"}));

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
        PowerMockito.doNothing().when(callbackSpy).execute(operationResultStateDtoMock);

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

    @AfterMethod
    public void after() {
        reset(stateMock, sessionStorageMock, requestStateDtoMock, requestInputStreamMock,
                operationResultStateDtoMock, operationResultInputStreamMock, callback, streamRequestBuilderMock,
                operationResultObjectCallback,descriptorMock);
    }
}



