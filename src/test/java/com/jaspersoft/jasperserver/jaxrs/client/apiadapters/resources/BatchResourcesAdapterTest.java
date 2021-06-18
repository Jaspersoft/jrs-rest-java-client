package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceSearchParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter}
 */
@PrepareForTest(JerseyRequest.class)
public class BatchResourcesAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientResourceListWrapper> requestMock;

    @Mock
    private OperationResult<ClientResourceListWrapper> resultMock;

    @Mock
    private JerseyRequest<Object> objectJerseyRequestMock;

    @Mock
    private OperationResult<Object> objectOperationResultMock;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }


    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock, objectJerseyRequestMock, objectOperationResultMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter#parameter(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceSearchParameter, String)}
     */
    @SuppressWarnings("unchecked")
    public void should_set_parameter_of_adapter() {

        /** Given **/
        BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);

        /** When  **/
        BatchResourcesAdapter retrieved = adapter.parameter(ResourceSearchParameter.FOLDER_URI, "uri_");

        /** Then **/
        assertSame(retrieved, adapter);
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) Whitebox.getInternalState(retrieved, "params");
        String retrievedUri = retrievedParams.get("folderUri").get(0);
        assertSame(retrievedUri, "uri_");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter#search()}
     */
    @SuppressWarnings("unchecked")
    public void should_invoke_all_private_logic_and_return_proper_operation_result() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ClientResourceListWrapper.class),
                eq(new String[]{"resources"}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(resultMock).when(requestMock).get();
        BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);

        /** When **/
        OperationResult<ClientResourceListWrapper> retrievedResult = adapter.search();

        /** Then **/
        assertNotNull(retrievedResult);
        assertSame(retrievedResult, resultMock);

        verifyStatic();
        buildRequest(eq(sessionStorageMock),
                eq(ClientResourceListWrapper.class),
                eq(new String[]{"resources"}),
                any(DefaultErrorHandler.class));

        verify(requestMock).get();
        verify(requestMock).addParams(any(MultivaluedHashMap.class));
        verifyNoMoreInteractions(resultMock);
    }

    @Test (enabled = false)
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter#asyncSearch(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_return_proper_RequestExecution_with_entity() throws ExecutionException, InterruptedException {

        /** Given **/

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientResourceListWrapper.class), eq(new String[]{"/resources"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(resultMock).when(requestMock).get();

        final AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        Callback<OperationResult<ClientResourceListWrapper>, Void> callback = Mockito.spy(new Callback<OperationResult<ClientResourceListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<ClientResourceListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);


        /** When **/
        RequestExecution retrieved = adapter.asyncSearch(callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved.getFuture());
        assertTrue(retrieved.getFuture().isDone());

        Mockito.verify(callback, times(1)).execute(resultMock);
        Mockito.verify(requestMock, times(1)).get();
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter#asyncDelete(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_delete_resource_asynchronously_and_return_proper_RequestExecution() throws InterruptedException {

        /** Given **/
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
        doReturn(objectOperationResultMock).when(objectJerseyRequestMock).delete();

        final AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        Callback<OperationResult, Void> callback = Mockito.spy(new Callback<OperationResult, Void>() {
            @Override
            public Void execute(OperationResult data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);


        /** When **/
        RequestExecution retrieved = adapter.asyncDelete(callback);


        /** Wait **/
        synchronized (callback) {
            callback.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        assertNotNull(retrieved.getFuture());
        Mockito.verify(callback, times(1)).execute(objectOperationResultMock);
        Mockito.verify(objectJerseyRequestMock, times(1)).delete();
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter#delete()}
     */
    public void should_delete_resource_and_return_operation_result() {

        /** Given **/
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
        doReturn(objectOperationResultMock).when(objectJerseyRequestMock).delete();

        BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);

        /** When **/
        OperationResult retrieved = adapter.delete();

        /** Then **/
        assertNotNull(retrieved);
        assertSame(retrieved, objectOperationResultMock);
    }

}