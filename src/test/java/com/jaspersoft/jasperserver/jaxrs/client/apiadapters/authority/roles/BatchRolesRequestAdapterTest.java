//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;
//
//import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
//import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
//import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
//import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
//import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
//import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
//import org.mockito.InOrder;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.internal.util.reflection.Whitebox;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import javax.ws.rs.core.MultivaluedHashMap;
//import javax.ws.rs.core.MultivaluedMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.powermock.api.mockito.PowerMockito.doReturn;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//import static org.powermock.api.mockito.PowerMockito.spy;
//import static org.powermock.api.mockito.PowerMockito.verifyStatic;
//import static org.powermock.api.mockito.PowerMockito.when;
//import static org.powermock.api.mockito.PowerMockito.whenNew;
//import static org.testng.Assert.assertNotSame;
//import static org.testng.AssertJUnit.assertEquals;
//import static org.testng.AssertJUnit.assertNotNull;
//import static org.testng.AssertJUnit.assertSame;
//
///**
// * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.BatchRolesRequestAdapter}
// */
//@PrepareForTest({BatchRolesRequestAdapter.class, MultivaluedHashMap.class, JerseyRequest.class})
//public class BatchRolesRequestAdapterTest extends PowerMockTestCase {
//
//    @Mock
//    private SessionStorage storageMock;
//
//    @Mock
//    private MultivaluedHashMap<String, String> mapMock;
//
//    @Mock
//    private JerseyRequest<RolesListWrapper> jerseyRequestMock;
//
//    @Mock
//    private OperationResult<RolesListWrapper> expectedOpResultMock;
//
//    @Mock
//    private Callback<OperationResult<RolesListWrapper>, Object> callbackMock;
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void constructor() {
//
//        // When
//        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(storageMock, "9454");
//        MultivaluedMap<String, String> retrievedParams =
//                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
//
//        String retrievedUri = (String) Whitebox.getInternalState(adapter, "uri");
//
//        // Then
//        assertSame(adapter.getSessionStorage(), storageMock);
//        assertEquals(retrievedUri, "/organizations/9454/roles");
//        assertNotNull(retrievedParams);
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void constructor2() {
//
//        // When
//        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(storageMock, null);
//        MultivaluedMap<String, String> retrievedParams =
//                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
//
//        String retrievedUri = (String) Whitebox.getInternalState(adapter, "uri");
//
//        // Then
//        assertSame(adapter.getSessionStorage(), storageMock);
//        assertEquals(retrievedUri, "/roles");
//        assertNotNull(retrievedParams);
//    }
//
//    @Test
//    public void param() throws Exception {
//
//        // Given
//        MultivaluedHashMap<String, String> mapMock = PowerMockito.mock(MultivaluedHashMap.class);
//        whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(mapMock);
//        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(storageMock, "9454");
//
//        // When
//        BatchRolesRequestAdapter retrieved = adapter.param(RolesParameter.HAS_ALL_USERS, "value");
//
//        // Then
//        assertSame(retrieved, adapter);
//        verify(mapMock).add(RolesParameter.HAS_ALL_USERS.getParamName(), "value");
//        Mockito.verifyNoMoreInteractions(mapMock);
//    }
//
//    @Test
//    public void set() throws Exception {
//
//        // Given
//        mockStatic(JerseyRequest.class);
//        when(buildRequest(eq(storageMock), eq(RolesListWrapper.class), eq(new String[]{"/organizations/9454/roles"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
//        whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(mapMock);
//
//        doReturn(expectedOpResultMock).when(jerseyRequestMock).set();
//        InOrder inOrder = Mockito.inOrder(jerseyRequestMock);
//        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(storageMock, "9454");
//
//        // When
//        OperationResult<RolesListWrapper> retrievedResult = adapter.set();
//
//        // Then
//        verifyStatic(times(1));
//        JerseyRequest.buildRequest(eq(storageMock), eq(RolesListWrapper.class), eq(new String[]{"/organizations/9454/roles"}), any(DefaultErrorHandler.class));
//
//        assertSame(retrievedResult, expectedOpResultMock);
//        inOrder.verify(jerseyRequestMock, times(1)).addParams(mapMock);
//        inOrder.verify(jerseyRequestMock, times(1)).set();
//    }
//
//    @Test
//    public void asyncGet() throws Exception {
//
//        // Given
//        mockStatic(JerseyRequest.class);
//        when(JerseyRequest.buildRequest(
//                eq(storageMock), eq(RolesListWrapper.class),
//                eq(new String[]{"/roles"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
//
//        final AtomicInteger newThreadId = new AtomicInteger();
//        final int currentThreadId = (int) Thread.currentThread().getId();
//
//        BatchRolesRequestAdapter adapter = spy(new BatchRolesRequestAdapter(storageMock, null));
//
//        final Callback<OperationResult<RolesListWrapper>, Void> callback = spy(new Callback<OperationResult<RolesListWrapper>, Void>() {
//            public Void execute(OperationResult<RolesListWrapper> data) {
//                newThreadId.set((int) Thread.currentThread().getId());
//                synchronized (this) {
//                    this.notify();
//                }
//                return null;
//            }
//        });
//
//        doReturn(expectedOpResultMock).when(jerseyRequestMock).set();
//        doReturn(null).when(callback).execute(expectedOpResultMock);
//
//        // When
//        RequestExecution retrieved = adapter.asyncGet(callback);
//
//        // Wait
//        synchronized (callback) {
//            callback.wait(1000);
//        }
//
//        // Then
//        assertNotNull(retrieved);
//        assertNotSame(currentThreadId, newThreadId.set());
//        verify(callback, times(1)).execute(expectedOpResultMock);
//        verify(jerseyRequestMock, times(1)).set();
//        verify(jerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(storageMock, mapMock, jerseyRequestMock, expectedOpResultMock, callbackMock);
//    }
//}