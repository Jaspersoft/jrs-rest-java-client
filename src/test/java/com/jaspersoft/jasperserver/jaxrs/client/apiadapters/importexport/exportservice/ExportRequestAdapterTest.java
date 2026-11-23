package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.dto.importexport.State;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ExportRequestAdapter}
 */
@SuppressWarnings("unchecked")
public class ExportRequestAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<State> requestStateDtoMock;
    @Mock
    private OperationResult<State> operationResultStateDtoMock;

    private final String taskId = "njkhfs8374";

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, requestStateDtoMock, operationResultStateDtoMock);
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
    public void should_pass_corresponding_params_to_the_constructor_and_invoke_parent_class_constructor_with_these_params()
            throws Exception {
        ExportRequestAdapter adapter = new ExportRequestAdapter(sessionStorageMock, taskId);

        assertSame(getInternalState(adapter, "taskId"), taskId);
        assertEquals(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(sessionStorageMock, State.class,
                    new String[]{"export", taskId, "state"})).thenReturn(requestStateDtoMock);
            doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();

            OperationResult<State> opResult = new ExportRequestAdapter(sessionStorageMock, taskId).state();

            assertSame(opResult, operationResultStateDtoMock);
            mockedStatic.verify(() -> buildRequest(sessionStorageMock, State.class,
                    new String[]{"export", taskId, "state"}));
            verify(requestStateDtoMock).get();
        }
    }

    @Test
    public void should_invoke_method_logic_asynchronously() throws Exception {
        final long currentThreadId = Thread.currentThread().getId();
        final AtomicLong newThreadId = new AtomicLong();
        final CountDownLatch latch = new CountDownLatch(1);

        Callback<OperationResult<State>, Void> callback = mock(Callback.class);
        doAnswer(invocation -> {
            newThreadId.set(Thread.currentThread().getId());
            latch.countDown();
            return null;
        }).when(callback).execute(any());

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(sessionStorageMock, State.class,
                    new String[]{"export", taskId, "state"})).thenReturn(requestStateDtoMock);
            doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();

            RequestExecution retrieved = new ExportRequestAdapter(sessionStorageMock, taskId).asyncState(callback);
            latch.await(1000, TimeUnit.MILLISECONDS);

            assertNotNull(retrieved);
            assertNotEquals(currentThreadId, newThreadId.get());
            verify(callback).execute(operationResultStateDtoMock);
            verify(requestStateDtoMock).get();
        }
    }

    @Test
    public void should_invoke_method_logic_asynchronously_with_() throws Exception {
        final String altTaskId = "task77";
        final long currentThreadId = Thread.currentThread().getId();
        final AtomicLong newThreadId = new AtomicLong();
        final CountDownLatch latch = new CountDownLatch(1);

        Callback<OperationResult<State>, Void> callbackSpy = mock(Callback.class);
        doAnswer(invocation -> {
            newThreadId.set(Thread.currentThread().getId());
            latch.countDown();
            return null;
        }).when(callbackSpy).execute(any());

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(sessionStorageMock, State.class,
                    new String[]{"export", altTaskId, "state"})).thenReturn(requestStateDtoMock);
            doReturn(operationResultStateDtoMock).when(requestStateDtoMock).get();

            RequestExecution retrieved = new ExportRequestAdapter(sessionStorageMock, altTaskId).asyncState(callbackSpy);
            latch.await(1000, TimeUnit.MILLISECONDS);

            assertNotNull(retrieved);
            assertNotEquals(currentThreadId, newThreadId.get());
            verify(callbackSpy).execute(operationResultStateDtoMock);
            verify(requestStateDtoMock).get();
        }
    }
}
