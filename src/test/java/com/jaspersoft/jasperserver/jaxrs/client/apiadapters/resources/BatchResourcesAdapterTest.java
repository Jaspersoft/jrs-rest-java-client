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
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.BatchResourcesAdapter}
 */
public class BatchResourcesAdapterTest {

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

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, resultMock, objectJerseyRequestMock, objectOperationResultMock);
        mockitoSession.close();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_parameter_of_adapter() {

        BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);

        BatchResourcesAdapter retrieved = adapter.parameter(ResourceSearchParameter.FOLDER_URI, "uri_");

        assertSame(retrieved, adapter);
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) getInternalState(retrieved, "params");
        String retrievedUri = retrievedParams.get("folderUri").get(0);
        assertSame(retrievedUri, "uri_");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_invoke_all_private_logic_and_return_proper_operation_result() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientResourceListWrapper.class),
                    eq(new String[]{"resources"}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();
            BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);

            OperationResult<ClientResourceListWrapper> retrievedResult = adapter.search();

            assertNotNull(retrievedResult);
            assertSame(retrievedResult, resultMock);

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientResourceListWrapper.class),
                    eq(new String[]{"resources"}),
                    any(DefaultErrorHandler.class)));

            verify(requestMock).get();
            verify(requestMock).addParams(any(MultivaluedHashMap.class));
            verifyNoMoreInteractions(resultMock);
        }
    }

    @Test(enabled = false)
    public void should_return_proper_RequestExecution_with_entity() throws ExecutionException, InterruptedException {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientResourceListWrapper.class), eq(new String[]{"/resources"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
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

            RequestExecution retrieved = adapter.asyncSearch(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            assertNotNull(retrieved.getFuture());

            Mockito.verify(callback, times(1)).execute(resultMock);
            Mockito.verify(requestMock, times(1)).get();
        }
    }

    @Test
    public void should_delete_resource_asynchronously_and_return_proper_RequestExecution() throws InterruptedException {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
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

            RequestExecution retrieved = adapter.asyncDelete(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            assertNotNull(retrieved.getFuture());
            Mockito.verify(callback, times(1)).execute(objectOperationResultMock);
            Mockito.verify(objectJerseyRequestMock, times(1)).delete();
        }
    }

    @Test
    public void should_delete_resource_and_return_operation_result() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"resources"}), any(DefaultErrorHandler.class))).thenReturn(objectJerseyRequestMock);
            doReturn(objectOperationResultMock).when(objectJerseyRequestMock).delete();

            BatchResourcesAdapter adapter = new BatchResourcesAdapter(sessionStorageMock);

            OperationResult retrieved = adapter.delete();

            assertNotNull(retrieved);
            assertSame(retrieved, objectOperationResultMock);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInternalState(Object target, String fieldName) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (T) field.get(target);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
