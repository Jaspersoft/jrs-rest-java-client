//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;
//
//import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
//import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
//import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
//import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
//import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
//import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.powermock.api.mockito.PowerMockito.spy;
//import static org.powermock.api.mockito.PowerMockito.verifyStatic;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertNotSame;
//import static org.testng.Assert.assertSame;
//
///**
//* Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.BatchUsersRequestAdapter}
//*/
//@PrepareForTest({BatchUsersRequestAdapter.class, MultivaluedHashMap.class, JerseyRequest.class})
//public class BatchUsersRequestAdapterTest extends PowerMockTestCase {
//
//    @Mock
//    private SessionStorage storageMock;
//
//    @Mock
//    private JerseyRequest<UsersListWrapper> requestMock;
//
//    @Mock
//    private Callback<OperationResult<UsersListWrapper>, Object> callbackMock;
//
//    @Mock
//    private Object resultMock;
//
//    @Mock
//    private OperationResult<UsersListWrapper> operationResultMock;
//
//    @Mock
//    private RequestBuilder<UsersListWrapper> requestBuilderMock;
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void constructor1() {
//
//        // When
//        BatchUsersRequestAdapter adapter = new BatchUsersRequestAdapter(storageMock, "MyCoolOrg");
//        MultivaluedMap<String, String> params =
//                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
//        String uri = (String) Whitebox.getInternalState(adapter, "uri");
//
//        // Then
//        assertSame(adapter.getSessionStorage(), storageMock);
//        assertNotNull(params);
//        assertEquals(uri, "/organizations/MyCoolOrg/users");
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void constructor2() {
//
//        // When
//        BatchUsersRequestAdapter adapter = new BatchUsersRequestAdapter(storageMock, null);
//        MultivaluedMap<String, String> params =
//                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
//        String uri = (String) Whitebox.getInternalState(adapter, "uri");
//
//        // Then
//        assertSame(adapter.getSessionStorage(), storageMock);
//        assertNotNull(params);
//        assertEquals(uri, "/users");
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void should_set_param() throws Exception {
//
//        // Given
//        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(storageMock, "MyCoolOrg"));
//
//        MultivaluedHashMap<String, String> mapSpy = spy(new MultivaluedHashMap<String, String>());
//        Whitebox.setInternalState(adapterSpy, "params", mapSpy);
//
//        // When
//        BatchUsersRequestAdapter retrieved = adapterSpy.param(UsersParameter.HAS_ALL_REQUIRED_ROLES, "true");
//
//        // Then
//        assertSame(retrieved, adapterSpy);
//        verify(mapSpy, times(1)).add("hasAllRequiredRoles", "true");
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void should_retrieve_users() throws Exception {
//
//        // Given
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock), eq(UsersListWrapper.class),
//                eq(new String[]{"/organizations/MyCoolOrg/users"}),
//                any(DefaultErrorHandler.class))).thenReturn(requestMock);
//
//        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(storageMock, "MyCoolOrg"));
//        MultivaluedMap<String, String> params =
//                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");
//
//        PowerMockito.doReturn(operationResultMock).when(requestMock).set();
//        PowerMockito.doReturn(requestBuilderMock).when(requestMock).addParams(params);
//        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);
//
//        // When
//        OperationResult<UsersListWrapper> retrieved = adapterSpy.set();
//
//        // Then
//        verifyStatic(times(1));
//        JerseyRequest.buildRequest(eq(storageMock), eq(UsersListWrapper.class),
//                eq(new String[]{"/organizations/MyCoolOrg/users"}), any(DefaultErrorHandler.class));
//
//        verify(requestMock).addParams(params);
//        verify(requestMock, times(1)).set();
//        verifyNoMoreInteractions(requestMock);
//        assertSame(retrieved, operationResultMock);
//    }
//
//    @Test(enabled = false)
//    @SuppressWarnings("unchecked")
//    public void should_retrieve_users_wrapped_in_oparation_result_wrapper() throws Exception {
//
//        // Given
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock), eq(UsersListWrapper.class), eq(new String[]{"/organizations/MyCoolOrg/users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
//
//        BatchUsersRequestAdapter adapterSpy = PowerMockito.spy(new BatchUsersRequestAdapter(storageMock, "MyCoolOrg"));
//        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");
//
//        PowerMockito.doReturn(operationResultMock).when(requestMock).set();
//        PowerMockito.doReturn(requestBuilderMock).when(requestMock).addParams(params);
//        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);
//
//        // When
//        adapterSpy.asyncGet(callbackMock);
//
//        // Then
//        PowerMockito.verifyStatic(times(1));
//        JerseyRequest.buildRequest(eq(storageMock), eq(UsersListWrapper.class), eq(new String[]{"/organizations/MyCoolOrg/users"}), any(DefaultErrorHandler.class));
//
//        Mockito.verify(callbackMock, times(1)).execute(operationResultMock);
//        Mockito.verify(requestMock, times(1)).addParams(params);
//        Mockito.verify(requestMock, times(1)).set();
//        Mockito.verifyNoMoreInteractions(requestMock);
//    }
//
//    @Test
//    public void should_retrieve_wrapped_UsersListWrapper_asynchronously() throws Exception {
//
//        /* Given */
//        final AtomicInteger newThreadId = new AtomicInteger();
//        final int currentThreadId = (int) Thread.currentThread().getId();
//        final String uri = "/organizations/MyCoolOrg/users";
//
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(
//                eq(storageMock),
//                eq(UsersListWrapper.class),
//                eq(new String[]{uri}),
//                any(DefaultErrorHandler.class))).thenReturn(requestMock);
//
//        BatchUsersRequestAdapter adapterSpy = PowerMockito.spy(new BatchUsersRequestAdapter(storageMock, "MyCoolOrg"));
//        PowerMockito.doReturn(operationResultMock).when(requestMock).set();
//
//        final Callback<OperationResult<UsersListWrapper>, Void> callbackSpy =
//                PowerMockito.spy(new Callback<OperationResult<UsersListWrapper>, Void>() {
//                    @Override
//                    public Void execute(OperationResult<UsersListWrapper> data) {
//                        newThreadId.set((int) Thread.currentThread().getId());
//                        synchronized (this) {
//                            this.notify();
//                        }
//                        return null;
//                    }
//                });
//
//        PowerMockito.doReturn(null).when(callbackSpy).execute(operationResultMock);
//
//        /* When */
//        RequestExecution retrieved = adapterSpy.asyncGet(callbackSpy);
//
//        /* Wait */
//        synchronized (callbackSpy) {
//            callbackSpy.wait(1000);
//        }
//
//        /* Then */
//        assertNotNull(retrieved);
//        assertNotSame(currentThreadId, newThreadId.set());
//        verify(callbackSpy, times(1)).execute(operationResultMock);
//        verify(requestMock, times(1)).set();
//        Mockito.verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(storageMock, requestMock, callbackMock, resultMock, operationResultMock, requestBuilderMock);
//    }
//}
//
//
