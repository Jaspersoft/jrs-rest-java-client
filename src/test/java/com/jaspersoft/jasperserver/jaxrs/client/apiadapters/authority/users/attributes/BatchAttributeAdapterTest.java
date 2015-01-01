//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;
//
//import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
//import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersAttributesParameter;
//import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
//import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
//import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
//import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
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
//import java.util.ArrayList;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.powermock.api.mockito.PowerMockito.spy;
//import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertNotSame;
//import static org.testng.Assert.assertSame;
//
///**
// * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeAdapter}
// */
//@PrepareForTest({JerseyRequest.class, ThreadPoolUtil.class, BatchAttributeAdapter.class,
//        StringBuilder.class, MultivaluedHashMap.class})
//public class BatchAttributeAdapterTest extends PowerMockTestCase {
//
//    @Mock
//    private SessionStorage sessionStorageMock;
//
//    @Mock
//    private JerseyRequest<UserAttributesListWrapper> requestMock;
//
//    @Mock
//    private Callback<OperationResult<UserAttributesListWrapper>, Object> callbackMock;
//
//    @Mock
//    private Object resultMock;
//
//    @Mock
//    private OperationResult<UserAttributesListWrapper> operationResultMock;
//
//    @Mock
//    private RequestBuilder<UserAttributesListWrapper> requestBuilderMock;
//
//    @Mock
//    private UserAttributesListWrapper userAttributesListWrapperMock;
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
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class); // PowerMockito is used for mocking
//        // final class StringBuilder
//        SessionStorage sessionStorageMock = PowerMockito.mock(SessionStorage.class);
//        BatchAttributeAdapter adapter = spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//
//        // Than
//        StringBuilder retrievedUri = (StringBuilder) Whitebox.getInternalState(adapter, "uri");
//        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
//
//        assertSame(adapter.getSessionStorage(), sessionStorageMock);
//        assertSame(retrievedUri, builderMock);
//        assertNotNull(retrievedParams);
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void constructor2() {
//        new BatchAttributeAdapter(null, null);
//    }
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void param() throws Exception {
//
//        // Given
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
//        BatchAttributeAdapter adapterSpy = spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//
//        MultivaluedHashMap<String, String> mapSpy = spy(new MultivaluedHashMap<String, String>());
//        Whitebox.setInternalState(adapterSpy, "params", mapSpy);
//
//        // When
//        BatchAttributeAdapter retrieved = adapterSpy.param(UsersAttributesParameter.NAME, "someParameter");
//
//        // Than
//        assertSame(retrieved, adapterSpy);
//        verify(mapSpy, times(1)).add("name", "someParameter");
//    }
//
//
//    @Test
//    @SuppressWarnings("unchecked")
//    public void get() throws Exception {
//
//        // Given
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
//        BatchAttributeAdapter adapterSpy = spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");
//        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
//        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
//
//        // When
//        OperationResult<UserAttributesListWrapper> retrieved = adapterSpy.get();
//
//        // Than
//        verifyPrivate(adapterSpy, times(1)).invoke("request");
//        verify(requestMock).addParams(params);
//        verify(requestMock, times(1)).get();
//        verifyNoMoreInteractions(requestMock);
//        assertSame(retrieved, operationResultMock);
//    }
//
//    @Test(enabled = false)
//    @SuppressWarnings("unchecked")
//    public void asyncGet() throws Exception {
//
//        // Given
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
//        BatchAttributeAdapter adapterSpy = PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");
//
//        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
//        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
//        PowerMockito.doReturn(requestBuilderMock).when(requestMock).addParams(params);
//        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);
//
//        // When
//        adapterSpy.asyncGet(callbackMock);
//
//        // Than
//        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("request");
//        Mockito.verify(callbackMock, times(1)).execute(operationResultMock);
//        Mockito.verifyNoMoreInteractions(callbackMock);
//    }
//
//    @Test
//    public void should_invoke_logic_of_get_method_asynchronously() throws InterruptedException {
//
//        /* Given */
//        final AtomicInteger newThreadId = new AtomicInteger();
//        final int currentThreadId = (int) Thread.currentThread().getId();
//
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(
//                eq(sessionStorageMock),
//                eq(UserAttributesListWrapper.class),
//                eq(new String[]{"uri", "/attributes"}),
//                any(DefaultErrorHandler.class))).thenReturn(requestMock);
//
//        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
//        BatchAttributeAdapter adapterSpy =
//                PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, new StringBuilder("uri")));
//
//        final Callback<OperationResult<UserAttributesListWrapper>, Void> callbackSpy =
//                PowerMockito.spy(new Callback<OperationResult<UserAttributesListWrapper>, Void>() {
//                    @Override
//                    public Void execute(OperationResult<UserAttributesListWrapper> data) {
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
//        /* Than */
//        assertNotNull(retrieved);
//        assertNotSame(currentThreadId, newThreadId.get());
//        verify(callbackSpy, times(1)).execute(operationResultMock);
//        verify(requestMock, times(1)).get();
//    }
//
//    @Test(suiteName = "refactored length")
//    public void updateOrCreate() throws Exception {
//
//        // Attributes to add
//
//        final ClientUserAttribute age = spy(new ClientUserAttribute()
//                .setName("Age")
//                .setValue("26"));
//
//        final ClientUserAttribute cardAttribute = spy(new ClientUserAttribute()
//                .setName("CreditCard")
//                .setValue("4527475209487934"));
//
//        UserAttributesListWrapper additionalAttributes = spy(new UserAttributesListWrapper());
//        ArrayList<ClientUserAttribute> list1 = spy(new ArrayList<ClientUserAttribute>() {{
//            add(age);
//            add(cardAttribute);
//        }});
//        additionalAttributes.setProfileAttributes(list1);
//
//        // Expected attributes
//
//        final ClientUserAttribute ageAttribute = spy(new ClientUserAttribute()
//                .setName("Age")
//                .setValue("27"));
//
//        final ClientUserAttribute accountAttribute = spy(new ClientUserAttribute()
//                .setName("Account")
//                .setValue("2587652395792469214724"));
//
//        UserAttributesListWrapper expected = spy(new UserAttributesListWrapper());
//        ArrayList<ClientUserAttribute> list2 = spy(new ArrayList<ClientUserAttribute>() {{
//            add(ageAttribute);
//            add(accountAttribute);
//        }});
//        expected.setProfileAttributes(list2);
//
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
//        BatchAttributeAdapter adapterSpy = spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
//        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
//        PowerMockito.doReturn(operationResultMock).when(requestMock).put(additionalAttributes);
//        PowerMockito.doReturn(userAttributesListWrapperMock).when(operationResultMock).getEntity();
//        PowerMockito.doReturn(expected.getProfileAttributes()).when(userAttributesListWrapperMock)
//                .getProfileAttributes();
//
//        // When
//        adapterSpy.updateOrCreate(additionalAttributes);
//
//        // Than
//        verifyPrivate(adapterSpy, times(2)).invoke("request");
//        verify(ageAttribute, times(1)).setValue(anyString());
//    }
//
//    @Test(enabled = false)
//    public void asyncCreateOrUpdate() throws Exception {
//
//        // Given
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
//        BatchAttributeAdapter adapterSpy = PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//
//        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
//        PowerMockito.doReturn(operationResultMock).when(requestMock).put(userAttributesListWrapperMock);
//        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);
//
//        // When
//        adapterSpy.asyncCreateOrUpdate(userAttributesListWrapperMock, callbackMock);
//
//        Thread.sleep(500);
//
//        // Than
//        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("request");
//        Mockito.verify(callbackMock, times(1)).execute(operationResultMock);
//
//    }
//
//    @Test(enabled = false)
//    public void asyncDelete() throws Exception {
//
//        // Given
//        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
//        BatchAttributeAdapter adapterSpy = PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, builderMock));
//
//        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
//        PowerMockito.doReturn(operationResultMock).when(requestMock).delete();
//        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);
//
//        // When
//        adapterSpy.asyncDelete(callbackMock);
//
//        // Than
//        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("request");
//        Mockito.verify(callbackMock, times(1)).execute(operationResultMock);
//    }
//
//    @Test(testName = "private")
//    public void request() throws Exception {
//
//        /* Given */
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UserAttributesListWrapper.class), eq(new String[]{"uri", "/attributes"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
//        PowerMockito.doReturn(operationResultMock).when(requestMock).delete();
//
//        /* When */
//        BatchAttributeAdapter adapterSpy = PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, new StringBuilder("uri")));
//        adapterSpy.delete();
//
//        /* Than */
//        assertNotNull(adapterSpy);
//        PowerMockito.verifyStatic(times(1));
//        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UserAttributesListWrapper.class), eq(new String[]{"uri", "/attributes"}), any(DefaultErrorHandler.class));
//    }
//
//    @Test
//    /**
//     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeAdapter#asyncDelete(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
//     */
//    public void should_delete_user_attributes_asynchronously() throws InterruptedException {
//
//        /* Given */
//        final AtomicInteger newThreadId = new AtomicInteger();
//        final int currentThreadId = (int) Thread.currentThread().getId();
//
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UserAttributesListWrapper.class), eq(new String[]{"uri", "/attributes"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
//        PowerMockito.doReturn(operationResultMock).when(requestMock).delete();
//
//        BatchAttributeAdapter adapterSpy = PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, new StringBuilder("uri")));
//        final Callback<OperationResult<UserAttributesListWrapper>, Void> callbackSpy =
//                PowerMockito.spy(new Callback<OperationResult<UserAttributesListWrapper>, Void>() {
//                    @Override
//                    public Void execute(OperationResult<UserAttributesListWrapper> data) {
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
//        RequestExecution retrieved = adapterSpy.asyncDelete(callbackSpy);
//
//        /* Wait */
//        synchronized (callbackSpy) {
//            callbackSpy.wait(1000);
//        }
//
//         /* Than */
//        assertNotNull(retrieved);
//        assertNotSame(currentThreadId, newThreadId.get());
//        verify(callbackSpy, times(1)).execute(operationResultMock);
//        verify(requestMock, times(1)).delete();
//    }
//
//    @Test
//    /**
//     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeAdapter#asyncCreateOrUpdate(com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
//     */
//    public void should_update_user_attributes_asynchronously() throws InterruptedException {
//
//        /* Given */
//        final AtomicInteger newThreadId = new AtomicInteger();
//        final int currentThreadId = (int) Thread.currentThread().getId();
//
//        PowerMockito.mockStatic(JerseyRequest.class);
//        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UserAttributesListWrapper.class), eq(new String[]{"uri", "/attributes"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
//        PowerMockito.doReturn(operationResultMock).when(requestMock).put(userAttributesListWrapperMock);
//
//        BatchAttributeAdapter adapterSpy = PowerMockito.spy(new BatchAttributeAdapter(sessionStorageMock, new StringBuilder("uri")));
//        final Callback<OperationResult<UserAttributesListWrapper>, Void> callbackSpy =
//                PowerMockito.spy(new Callback<OperationResult<UserAttributesListWrapper>, Void>() {
//                    @Override
//                    public Void execute(OperationResult<UserAttributesListWrapper> data) {
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
//        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(userAttributesListWrapperMock, callbackSpy);
//
//        /* Wait */
//        synchronized (callbackSpy) {
//            callbackSpy.wait(1000);
//        }
//
//         /* Than */
//        assertNotNull(retrieved);
//        assertNotSame(currentThreadId, newThreadId.get());
//        verify(callbackSpy, times(1)).execute(operationResultMock);
//        verify(requestMock, times(1)).put(userAttributesListWrapperMock);
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(sessionStorageMock, requestMock, callbackMock, resultMock, operationResultMock,
//                requestBuilderMock, userAttributesListWrapperMock);
//    }
//}