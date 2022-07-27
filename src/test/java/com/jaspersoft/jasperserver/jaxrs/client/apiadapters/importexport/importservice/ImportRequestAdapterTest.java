package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.dto.importexport.ImportTask;
import com.jaspersoft.jasperserver.dto.importexport.State;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportRequestAdapter}
 */
@PrepareForTest(JerseyRequest.class)
public class ImportRequestAdapterTest extends PowerMockTestCase {

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


    private String[] fakeArrayPathForState = new String[]{"import", TASK_ID, "state"};
    private String[] fakeArrayPathForTask= new String[]{"import", TASK_ID};

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_pass_corresponding_params_to_the_constructor_and_invoke_parent_class_constructor_with_these_params() throws IllegalAccessException {

        // Given
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, TASK_ID);

        // When
        SessionStorage retrievedSessionStorage = adapter.getSessionStorage();
        String retrievedField = (String) Whitebox.getInternalState(adapter, TASK_ID);

        // Then
        assertSame(retrievedField, TASK_ID);
        assertEquals(retrievedSessionStorage, storageMock);
    }

    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock), eq(State.class), eq(fakeArrayPathForState), any(DefaultErrorHandler.class))).thenReturn(stateRequestMock);
        PowerMockito.when(stateRequestMock.get()).thenReturn(stateOperationResultMock);

        // When
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, TASK_ID);
        OperationResult<State> opResult = adapter.state();

        // Then
        assertSame(opResult, stateOperationResultMock);
    }

    @Test(testName = "getTask")
    public void should_return_proper_OperationResult_object_when_get_task() {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock),
                eq(ImportTask.class),
                eq(fakeArrayPathForTask))).thenReturn(taskRequestMock);
        PowerMockito.when(taskRequestMock.get()).thenReturn(taskOperationResultMock);

        // When
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, TASK_ID);
        OperationResult<ImportTask> opResult = adapter.getTask();

        // Then
        assertSame(opResult, taskOperationResultMock);
    }

    @Test(testName = "restartTask")
    public void should_return_proper_OperationResult_object_when_restart_task() {
        ImportTask importTask = new ImportTask();
        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock),
                eq(ImportTask.class),
                eq(fakeArrayPathForTask))).thenReturn(taskRequestMock);
        PowerMockito.when(taskRequestMock.put(importTask)).thenReturn(taskOperationResultMock);

        // When
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, TASK_ID);
        OperationResult<ImportTask> opResult = adapter.restartTask(importTask);

        // Then
        assertSame(opResult, taskOperationResultMock);
    }

    @Test
    public void should_retrieve_state_asynchronously () throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock),
                eq(State.class),
                eq(new String[]{"/import", TASK_ID, "/state"}))).thenReturn(stateRequestMock);
        PowerMockito.doReturn(stateOperationResultMock).when(stateRequestMock).get();

        ImportRequestAdapter adapterSpy = PowerMockito.spy(new ImportRequestAdapter(storageMock, TASK_ID));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<State>, Void> callback = PowerMockito.spy(new Callback<OperationResult<State>, Void>() {
            @Override
            public Void execute(OperationResult<State> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doNothing().when(callback).execute(stateOperationResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncState(callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @AfterMethod
    public void before() {
        reset(storageMock, stateRequestMock, stateOperationResultMock);
    }
}