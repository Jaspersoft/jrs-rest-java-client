package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportRequestAdapter}
 */
@PrepareForTest(JerseyRequest.class)
public class ImportRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage storageMock;

    @Mock
    private JerseyRequest<StateDto> requestMock;

    @Mock
    private OperationResult<StateDto> resultMock;

    private String taskId = "njkhfs8374";
    private String[] fakeArrayPath = new String[]{"/import", taskId, "/state"};

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    public void should_pass_corresponding_params_to_the_constructor_and_invoke_parent_class_constructor_with_these_params() throws IllegalAccessException {

        // Given
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, taskId);

        // When
        SessionStorage retrievedSessionStorage = adapter.getSessionStorage();
        String retrievedField = (String) Whitebox.getInternalState(adapter, "taskId");

        // Than
        assertSame(retrievedField, taskId);
        assertEquals(retrievedSessionStorage, storageMock);
    }
    @Test(testName = "state")
    public void should_return_proper_OperationResult_object() {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock), eq(StateDto.class), eq(fakeArrayPath), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        PowerMockito.when(requestMock.get()).thenReturn(resultMock);

        // When
        ImportRequestAdapter adapter = new ImportRequestAdapter(storageMock, taskId);
        OperationResult<StateDto> opResult = adapter.state();

        // Then
        assertSame(opResult, resultMock);
    }

    @Test
    public void test () throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock), eq(StateDto.class), eq(new String[]{"/import", taskId, "/state"}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).get();

        ImportRequestAdapter adapterSpy = PowerMockito.spy(new ImportRequestAdapter(storageMock, taskId));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<StateDto>, Void> callback = PowerMockito.spy(new Callback<OperationResult<StateDto>, Void>() {
            @Override
            public Void execute(OperationResult<StateDto> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncState(callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Than */
        Mockito.verify(requestMock).get();
        Mockito.verify(callback).execute(resultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @AfterMethod
    public void before() {
        reset(storageMock, requestMock, resultMock);
    }
}