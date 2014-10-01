package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeInterfaceAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter}
 */
@PrepareForTest({
        SingleUserRequestAdapter.class,
        SingleAttributeAdapter.class,
        JerseyRequest.class,
        StringBuilder.class})
public class SingleUserRequestAdapterTest extends PowerMockTestCase {

    @Mock
    public SessionStorage sessionStorageMock;

    @Mock
    public SingleAttributeAdapter expectedSingleAttributeAdapterMock;

    @Mock
    private BatchAttributeAdapter expectedBatchAttributeAdapterMock;

    @Mock
    private JerseyRequest<ClientUser> userJerseyRequestMock;

    @Mock
    private OperationResult<ClientUser> operationResultMock;

    @Mock
    private ClientUser userMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "constructor_with_String")
    public void should_invoke_constructor_with_proper_three_params() {

        // When
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon");

        final String retrieved = (String) Whitebox.getInternalState(adapter, "userUriPrefix");
        final String expected = "/organizations/MyCoolOrg/users/Simon";

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved, expected);
    }

    @Test(testName = "constructor_with_String")
    public void should_invoke_constructor_with_null_organizationId() {

        // When
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, null, "Simon");

        final String retrieved = (String) Whitebox.getInternalState(adapter, "userUriPrefix");
        final String expected = "/users/Simon";

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved, expected);
    }

    @Test(testName = "constructor_with_StringBuilder")
    public void should_invoke_constructor_with_proper_two_params() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg");
        final StringBuilder retrieved = (StringBuilder) Whitebox.getInternalState(adapter, "uri");
        final String expected = "/organizations/MyCoolOrg/users/";

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved.toString(), expected);
    }

    @Test(testName = "constructor_with_StringBuilder")
    public void should_invoke_non_deprecated_constructor_with_null_organizationId() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, null);
        final StringBuilder retrieved = (StringBuilder) Whitebox.getInternalState(adapter, "uri");
        final String expected = "/users/";

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved.toString(), expected);
    }

    @Test
    public void test1() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", "MyCoolOrg", sessionStorageMock);
        final StringBuilder retrieved = (StringBuilder) Whitebox.getInternalState(adapter, "uri");
        final String expected = "/organizations/MyCoolOrg/users/Simon";

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(retrieved.toString(), expected);
    }

    @Test
    public void test2() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", null, sessionStorageMock);
        final StringBuilder retrieved = (StringBuilder) Whitebox.getInternalState(adapter, "uri");
        final String expected = "/users/Simon";

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(retrieved.toString(), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(null, null, sessionStorageMock);

        // Than
        // throw exception
    }

    @Test
    public void attribute() throws Exception {

        // Given
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", "MyCoolOrg", sessionStorageMock);
        whenNew(SingleAttributeAdapter.class)
                .withArguments(eq(sessionStorageMock), any(StringBuilder.class))
                .thenReturn(expectedSingleAttributeAdapterMock);

        // When
        final SingleAttributeAdapter retrieved = adapter.attribute();

        // Than
        verifyNew(SingleAttributeAdapter.class)
                .withArguments(eq(sessionStorageMock), any(StringBuilder.class));
        assertSame(retrieved, expectedSingleAttributeAdapterMock);
    }

    @Test
    public void multipleAttributes() throws Exception {

        // Given
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", "MyCoolOrg", sessionStorageMock);
        whenNew(BatchAttributeAdapter.class)
                .withArguments(eq(sessionStorageMock), any(StringBuilder.class))
                .thenReturn(expectedBatchAttributeAdapterMock);

        // When
        BatchAttributeAdapter retrieved = adapter.multipleAttributes();

        // Than
        verifyNew(BatchAttributeAdapter.class)
                .withArguments(eq(sessionStorageMock), any(StringBuilder.class));
        assertSame(retrieved, expectedBatchAttributeAdapterMock);
    }

    @Test
    public void should_return_proper_SingleAttributeInterfaceAdapter_object() throws Exception {

        // Given
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon");
        SingleAttributeInterfaceAdapter expected = mock(SingleAttributeInterfaceAdapter.class);

        whenNew(SingleAttributeInterfaceAdapter.class)
                .withArguments(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State")
                .thenReturn(expected);

        // When
        SingleAttributeInterfaceAdapter retrieved = adapter.attribute("State");

        // Than
        verifyNew(SingleAttributeInterfaceAdapter.class).withArguments(eq(sessionStorageMock), eq("/organizations/MyCoolOrg/users/Simon"), eq("State"));
        assertSame(retrieved, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_if_invalid_param() {

        // Given
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", "MyCoolOrg", sessionStorageMock);

        // When
        adapter.attribute("");

        // Than
        // throw an exception
    }

    @Test
    public void should_return_proper_adapter_class() throws Exception {

        // Given
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon");
        BatchAttributeInterfaceAdapter expected = mock(BatchAttributeInterfaceAdapter.class);
        whenNew(BatchAttributeInterfaceAdapter.class).withArguments(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon").thenReturn(expected);

        // When
        BatchAttributeInterfaceAdapter retrieved = adapter.attributes();

        // Than
        verifyNew(BatchAttributeInterfaceAdapter.class).withArguments(eq(sessionStorageMock), eq("/organizations/MyCoolOrg/users/Simon"));
        assertSame(expected, retrieved);
    }

    @Test
    public void should_return_OperationResult_object() throws Exception {

        // Given
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter("Simon", "MyCoolOrg",
                sessionStorageMock));
        doReturn(userJerseyRequestMock).when(adapterSpy, "buildRequest");
        doReturn(operationResultMock).when(userJerseyRequestMock).get();

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.get();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        verify(userJerseyRequestMock, times(1)).get();
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void test8() throws Exception {

        // Given
        final String userId = "Simon";

        mockStatic(JerseyRequest.class);
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter("Simon", "MyCoolOrg", sessionStorageMock));

        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/" + userId}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).get();

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.get(userId);

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/" + userId}), any(DefaultErrorHandler.class));
    }

    @Test
    public void test9() throws Exception {

        // Given
        final String userId = "Simon";

        mockStatic(JerseyRequest.class);
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));

        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/" + userId}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).get();

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.get(userId);

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/" + userId}), any(DefaultErrorHandler.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void asyncGet() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, null, "Simon"));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "buildRequest");
        doReturn(operationResultMock).when(userJerseyRequestMock).get();
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        adapterSpy.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Than
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).get();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_run_method_get_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(/*sessionStorageMock, null, "Simon"*/ "Simon", null, sessionStorageMock));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(userJerseyRequestMock).get();
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGet(callback, "Simon");

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        StringBuilder uri = (StringBuilder) Whitebox.getInternalState(adapterSpy, "uri");

        // Than
        assertNotNull(retrieved);
        assertEquals(uri.toString(), "/users/Simon");
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).get();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_run_method_asynchronously_when_use_deprecated_constructor() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, null));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(userJerseyRequestMock).get();
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGet(callback, "Simon");

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        StringBuilder uri = (StringBuilder) Whitebox.getInternalState(adapterSpy, "uri");

        // Than
        assertNotNull(retrieved);
        assertEquals(uri.toString(), "/users/Simon");
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).get();
    }

    @Test
    public void asyncCreateOrUpdate_deprecated() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, null, "Simon"));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "buildRequest");
        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(userMock, callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Than
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).put(userMock);
    }

    @Test
    public void asyncCreateOrUpdate() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, null));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(userMock, callback, "Simon");

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        StringBuilder uri = (StringBuilder) Whitebox.getInternalState(adapterSpy, "uri");

        // Than
        assertNotNull(retrieved);
        assertEquals(uri.toString(), "/users/Simon");
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).put(userMock);
    }

    @Test
    public void test10() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        final SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon"));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "buildRequest");
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncDelete(callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Than
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).delete();
    }

    @Test
    public void test11() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        final SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));

        final Callback<OperationResult<ClientUser>, Void> callback = spy(new Callback<OperationResult<ClientUser>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUser> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(userJerseyRequestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();
        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncDelete(callback, "Simon");

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        StringBuilder uri = (StringBuilder) Whitebox.getInternalState(adapterSpy, "uri");

        // Than
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        assertEquals(uri.toString(), "/organizations/MyCoolOrg/users/Simon");
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).delete();
    }

    /**
     * Test for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#delete()} and for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#buildRequest}
     */
    @Test
    public void test12() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon"));

        // When
        OperationResult retrieved = adapterSpy.delete();

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}), any(DefaultErrorHandler.class));
        verify(userJerseyRequestMock, times(1)).delete();
    }

    @Test
    /**
     * Test for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#createOrUpdate(com.jaspersoft.jasperserver.dto.authority.ClientUser)} and for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#buildRequest}
     */
    public void test__() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon"));

        // When
        OperationResult retrieved = adapterSpy.createOrUpdate(userMock);

        // Than
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}), any(DefaultErrorHandler.class));
        verify(userJerseyRequestMock, times(1)).put(userMock);
    }

    @Test
    public void should_update_user() throws Exception {

        /* Given */
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(ClientUser.class),
                eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));

        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);
        doReturn("Simon").when(userMock).getUsername();
        doReturn("MyCoolOrg").when(userMock).getTenantId();

        /* Then */
        OperationResult<ClientUser> retrieved = adapterSpy.updateOrCreate(userMock);

        /* Than */
        assertNotNull(retrieved);
        verify(userMock, times(1)).getUsername();
    }

    @Test
    public void should_return_operation_result_when_there_is_no_org() throws Exception {

        /* Given */
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(ClientUser.class),
                eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, null));

        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);
        doReturn("Simon").when(userMock).getUsername();
        doReturn("MyCoolOrg").when(userMock).getTenantId();

        /* When */
        OperationResult<ClientUser> retrieved = adapterSpy.updateOrCreate(userMock);

        /* Than */
        assertNotNull(retrieved);
        verify(userMock, times(1)).getUsername();
        verify(userMock, times(2)).getTenantId();
    }

    @Test
    public void should_delete_user() {

        /* Given */
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(ClientUser.class),
                eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();
        doReturn("Simon").when(userMock).getUsername();

        /* When */
        OperationResult retrieved = adapterSpy.delete(userMock);

        /* Than */
        assertNotNull(retrieved);
        verify(userMock, times(1)).getUsername();
    }

    @Test
    public void should_delete_user_by_string_user_name() {

        /* Given */
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(ClientUser.class),
                eq(new String[]{"/organizations/MyCoolOrg/users/Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();

        /* When */
        OperationResult retrieved = adapterSpy.delete("Simon");

        /* Than */
        assertNotNull(retrieved);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, expectedSingleAttributeAdapterMock, expectedBatchAttributeAdapterMock,
                userJerseyRequestMock, operationResultMock, userMock);
    }
}