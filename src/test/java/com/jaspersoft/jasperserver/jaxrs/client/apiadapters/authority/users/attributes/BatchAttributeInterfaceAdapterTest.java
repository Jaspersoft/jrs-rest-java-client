package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersAttributesParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.InOrder;
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
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter}.
 *
 * @author Alexander Krasnyanskiy
 */
@PrepareForTest({JerseyRequest.class, BatchAttributeInterfaceAdapter.class})
public class BatchAttributeInterfaceAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private MultivaluedHashMap<String, String> mapWithParamsMock;

    @Mock
    private BatchAttributeInterfaceAdapter expectedAdapterMock;

    @Mock
    private JerseyRequest<UserAttributesListWrapper> requestMock;

    @Mock
    private RequestBuilder<UserAttributesListWrapper> builderMock;

    @Mock
    private OperationResult<UserAttributesListWrapper> expectedOperationResultMock;

    @Mock
    private UserAttributesListWrapper listWrapperMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#BatchAttributeInterfaceAdapter(com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage, String)}.
     */
    @SuppressWarnings("unchecked")
    public void should_create_object_with_proper_BatchAttributeInterfaceAdapter_constructor() throws Exception {

        // When
        BatchAttributeInterfaceAdapter created = new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri");

        // Than
        assertNotNull(created);
        assertSame(created.getSessionStorage(), sessionStorageMock);

        MultivaluedHashMap<String, String> params = (MultivaluedHashMap<String, String>) getInternalState(created, "params");
        assertNotNull(params);
        assertTrue(params.size() == 0);

        String uri = (String) getInternalState(created, "uri");
        assertEquals(uri, "my/cool/uri");
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#param(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersAttributesParameter, String)}.
     */
    @SuppressWarnings("unchecked")
    public void should_add_value_to_params_map_and_return_this() {

        // Given
        BatchAttributeInterfaceAdapter expected = new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri");

        // When
        BatchAttributeInterfaceAdapter retrieved = expected.param(UsersAttributesParameter.NAME, "Attr1");
        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) Whitebox.getInternalState(expected, "params");

        assertSame(retrieved, expected);
        assertTrue(retrievedParams.size() == 1);
        assertEquals(retrievedParams.getFirst("name"), "Attr1");
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#get()}.
     */
    @SuppressWarnings("unchecked")
    public void should_return_proper_OperationResult() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(UserAttributesListWrapper.class),
                eq(new String[]{"my/cool/uri", "/attributes"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);

        doReturn(builderMock).when(requestMock).addParams(any(MultivaluedHashMap.class));
        doReturn(expectedOperationResultMock).when(requestMock).get();
        InOrder inOrder = Mockito.inOrder(requestMock);

        BatchAttributeInterfaceAdapter adapter = new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri");

        // When
        OperationResult<UserAttributesListWrapper> retrieved = adapter.get();

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(UserAttributesListWrapper.class),
                eq(new String[]{"my/cool/uri", "/attributes"}), any(DefaultErrorHandler.class));

        assertSame(retrieved, expectedOperationResultMock);
        inOrder.verify(requestMock).addParams(any(MultivaluedHashMap.class));
        inOrder.verify(requestMock).get();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#asyncGet(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_fire_get_method_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        BatchAttributeInterfaceAdapter adapterSpy = spy(new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri"));

        final Callback<OperationResult<UserAttributesListWrapper>, Void> callback = spy(new Callback<OperationResult<UserAttributesListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<UserAttributesListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(requestMock).when(adapterSpy, "buildRequest");
        doReturn(expectedOperationResultMock).when(requestMock).get();
        doReturn(null).when(callback).execute(expectedOperationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Than
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(expectedOperationResultMock);
        verify(requestMock, times(1)).get();
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#asyncDelete(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_fire_delete_method_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        BatchAttributeInterfaceAdapter adapterSpy = spy(new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri"));

        final Callback<OperationResult<UserAttributesListWrapper>, Void> callback = spy(new Callback<OperationResult<UserAttributesListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<UserAttributesListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(requestMock).when(adapterSpy, "buildRequest");
        doReturn(expectedOperationResultMock).when(requestMock).delete();
        doReturn(null).when(callback).execute(expectedOperationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncDelete(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Than
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(expectedOperationResultMock);
        verify(requestMock, times(1)).delete();
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#asyncCreateOrUpdate(com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_fire_asyncCreateOrUpdate_method_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        BatchAttributeInterfaceAdapter adapterSpy = spy(new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri"));

        final Callback<OperationResult<UserAttributesListWrapper>, Void> callback = spy(new Callback<OperationResult<UserAttributesListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<UserAttributesListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(requestMock).when(adapterSpy, "buildRequest");
        doReturn(expectedOperationResultMock).when(requestMock).put(listWrapperMock);
        doReturn(null).when(callback).execute(expectedOperationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(listWrapperMock, callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Than
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(expectedOperationResultMock);
        verify(requestMock, times(1)).put(listWrapperMock);
    }

    @Test
    /**
     * -- {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#createOrUpdate(com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper)}
     */
    public void should_invoke_createOrUpdate_method_and_return_proper_op_result() throws Exception {

        // Given
        BatchAttributeInterfaceAdapter adapterSpy = spy(new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri"));
        doReturn(requestMock).when(adapterSpy, "buildRequest");
        doReturn(expectedOperationResultMock).when(requestMock).put(listWrapperMock);

        // When
        OperationResult retrieved = adapterSpy.createOrUpdate(listWrapperMock);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        assertSame(retrieved, expectedOperationResultMock);
    }

    @Test
    /**
     * -- for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter#delete()}
     */
    public void should_delete_user_attribute() throws Exception {

        // Given
        BatchAttributeInterfaceAdapter adapterSpy = spy(new BatchAttributeInterfaceAdapter(sessionStorageMock, "my/cool/uri"));
        doReturn(requestMock).when(adapterSpy, "buildRequest");
        doReturn(expectedOperationResultMock).when(requestMock).delete();
        doReturn(builderMock).when(requestMock).addParams(any(MultivaluedHashMap.class));

        InOrder inOrder = Mockito.inOrder(requestMock);

        // When
        OperationResult retrieved = adapterSpy.delete();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        assertSame(retrieved, expectedOperationResultMock);
        inOrder.verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        inOrder.verify(requestMock, times(1)).delete();
        inOrder.verifyNoMoreInteractions();
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, mapWithParamsMock, expectedAdapterMock, requestMock, builderMock,
                expectedOperationResultMock, listWrapperMock);
    }
}