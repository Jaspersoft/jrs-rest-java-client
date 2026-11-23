package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.dto.importexport.ImportTask;
import com.jaspersoft.jasperserver.dto.importexport.State;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ImportRequestAdapter}
 */
@SuppressWarnings("unchecked")
public class ImportRequestAdapterTest {

    public static final String TASK_ID = "taskId";

    @Mock
    private SessionStorage storageMock;
    @Mock
    private JerseyRequest<State> stateRequestMock;
    @Mock
    private JerseyRequest<ImportTask> taskRequestMock;
    @Mock
    private OperationResult<State> stateOperationResultMock;
    @Mock
    private OperationResult<ImportTask> taskOperationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(storageMock, stateRequestMock, taskRequestMock, stateOperationResultMock, taskOperationResultMock);
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
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, TASK_ID);

        assertSame(getInternalState(adapter, "taskId"), TASK_ID);
        assertSame(adapter.getSessionStorage(), storageMock);
    }

    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(storageMock), eq(State.class),
                    eq(new String[]{"import", TASK_ID, "state"}),
                    any(DefaultErrorHandler.class))).thenReturn(stateRequestMock);
            doReturn(stateOperationResultMock).when(stateRequestMock).get();

            OperationResult<State> opResult = new ImportRequestAdapter(storageMock, TASK_ID).state();

            assertSame(opResult, stateOperationResultMock);
            mockedStatic.verify(() -> buildRequest(eq(storageMock), eq(State.class),
                    eq(new String[]{"import", TASK_ID, "state"}), any(DefaultErrorHandler.class)));
            verify(stateRequestMock).get();
        }
    }

    @Test(testName = "getTask")
    public void should_return_proper_OperationResult_object_when_get_task() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(storageMock, ImportTask.class,
                    new String[]{"import", TASK_ID})).thenReturn(taskRequestMock);
            doReturn(taskOperationResultMock).when(taskRequestMock).get();

            OperationResult<ImportTask> opResult = new ImportRequestAdapter(storageMock, TASK_ID).getTask();

            assertSame(opResult, taskOperationResultMock);
            mockedStatic.verify(() -> buildRequest(storageMock, ImportTask.class,
                    new String[]{"import", TASK_ID}));
            verify(taskRequestMock).get();
        }
    }

    @Test(testName = "restartTask")
    public void should_return_proper_OperationResult_object_when_restart_task() {
        ImportTask importTask = new ImportTask();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(storageMock, ImportTask.class,
                    new String[]{"import", TASK_ID})).thenReturn(taskRequestMock);
            doReturn(taskOperationResultMock).when(taskRequestMock).put(importTask);

            OperationResult<ImportTask> opResult = new ImportRequestAdapter(storageMock, TASK_ID).restartTask(importTask);

            assertSame(opResult, taskOperationResultMock);
            mockedStatic.verify(() -> buildRequest(storageMock, ImportTask.class,
                    new String[]{"import", TASK_ID}));
            verify(taskRequestMock).put(importTask);
        }
    }

    @Test
    public void should_retrieve_state_asynchronously() throws Exception {
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
            mockedStatic.when(() -> buildRequest(storageMock, State.class,
                    new String[]{"import", TASK_ID, "state"})).thenReturn(stateRequestMock);
            doReturn(stateOperationResultMock).when(stateRequestMock).get();

            RequestExecution retrieved = new ImportRequestAdapter(storageMock, TASK_ID).asyncState(callback);
            latch.await(1000, TimeUnit.MILLISECONDS);

            Assert.assertNotNull(retrieved);
            assertNotEquals(currentThreadId, newThreadId.get());
            verify(callback).execute(stateOperationResultMock);
            verify(stateRequestMock).get();
        }
    }
}
