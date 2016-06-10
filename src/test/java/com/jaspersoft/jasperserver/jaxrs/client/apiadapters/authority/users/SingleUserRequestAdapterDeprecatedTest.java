package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
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

/**
 * Class should be deleted after deleting appropriate deprecated methods in SingleUserRequestAdapter class
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationsServiceTest}.
 */
@Deprecated
@PrepareForTest({
        SingleUserRequestAdapter.class,
        JerseyRequest.class,
        StringBuilder.class})
public class SingleUserRequestAdapterDeprecatedTest extends PowerMockTestCase {

    @Mock
    public SessionStorage sessionStorageMock;

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

        final ArrayList<String> retrieved = (ArrayList<String>) Whitebox.getInternalState(adapter, "uri");
        ArrayList<String> expected = new ArrayList<String>() {{
            add("organizations");
            add("MyCoolOrg");
            add("users");
        }};

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved, expected);
    }

    @Test(testName = "constructor_with_String")
    public void should_invoke_constructor_with_null_organizationId() {

        // When
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, null, "Simon");

        final ArrayList<String> retrieved = (ArrayList<String>) Whitebox.getInternalState(adapter, "uri");
        ArrayList<String> expected = new ArrayList<String>() {{
            add("users");
        }};

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved, expected);
    }

    @Test(testName = "constructor_with_StringBuilder")
    public void should_invoke_constructor_with_proper_two_params() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg");
        final ArrayList<String> retrieved = (ArrayList<String>) Whitebox.getInternalState(adapter, "uri");
        ArrayList<String> expected = new ArrayList<String>() {{
            add("organizations");
            add("MyCoolOrg");
            add("users");
        }};



        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved, expected);
    }

    @Test(testName = "constructor_with_StringBuilder")
    public void should_invoke_non_deprecated_constructor_with_null_organizationId() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, (String)null);
        final ArrayList<String> retrieved = (ArrayList<String>) Whitebox.getInternalState(adapter, "uri");
        ArrayList<String> expected = new ArrayList<String>() {{
            add("users");
        }};

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Assert.assertEquals(retrieved, expected);
    }

    @Test
    public void should_set_uri() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", "MyCoolOrg", sessionStorageMock);
        final ArrayList<String> retrieved = (ArrayList<String>) Whitebox.getInternalState(adapter, "uri");
        ArrayList<String> expected = new ArrayList<String>() {{
            add("organizations");
            add("MyCoolOrg");
            add("users");
            add("Simon");
        }};

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(retrieved, expected);
    }

    @Test
    public void should_set_user_name() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter("Simon", null, sessionStorageMock);
        final ArrayList<String> retrieved = (ArrayList<String>) Whitebox.getInternalState(adapter, "uri");
        ArrayList<String> expected = new ArrayList<String>() {{
            add("users");
            add("Simon");
        }};

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(retrieved, expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_params_are_wrong() {

        // When
        final SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(null, null, sessionStorageMock);

        // Then
        // throw exception
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

        // Then
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        verify(userJerseyRequestMock, times(1)).get();
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_retrieve_user() throws Exception {

        // Given
        final String userId = "Simon";

        mockStatic(JerseyRequest.class);
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(userId, "MyCoolOrg", sessionStorageMock));

        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations",
                "MyCoolOrg", "users", userId}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).get();

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.get(userId);

        // Then
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations",
                "MyCoolOrg", "users", userId}), any(DefaultErrorHandler.class));
        assertNotNull(retrieved);
    }

    @Test
    public void should_retrieve_user_by_id() throws Exception {

        // Given
        final String userId = "Simon";

        mockStatic(JerseyRequest.class);
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));

        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations",
                "MyCoolOrg", "users", userId}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).get();

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.get(userId);

        // Then
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations",
                "MyCoolOrg", "users", userId}), any(DefaultErrorHandler.class));
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

        // Then
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

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter("Simon", null, sessionStorageMock));

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

        ArrayList<String> uri = (ArrayList<String>) Whitebox.getInternalState(adapterSpy, "uri");

        // Then
        assertNotNull(retrieved);
        assertEquals(uri.toString(), "[users, Simon]");
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
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, (String)null));

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

        ArrayList<String> uri = (ArrayList<String>) Whitebox.getInternalState(adapterSpy, "uri");

        // Then
        assertNotNull(retrieved);
        assertEquals(uri.toString(), "[users, Simon]");
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

        // Then
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
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, (String)null));

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

        ArrayList<String> uri = (ArrayList<String>) Whitebox.getInternalState(adapterSpy, "uri");

        // Then
        assertNotNull(retrieved);
        assertEquals(uri.toString(), "[users, Simon]");
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).put(userMock);
    }

    @Test
    public void should_delete_user_asynchronously_() throws Exception {

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

        // Then
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).delete();
    }

    @Test
    public void should_delete_user_asynchronously() throws Exception {

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

        ArrayList<String> uri = (ArrayList<String>) Whitebox.getInternalState(adapterSpy, "uri");

        // Then
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        assertEquals(uri.toString(), "[organizations, MyCoolOrg, users, Simon]");
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).delete();
    }

    /**
     * Test for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#delete()} and for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#buildRequest}
     */
    @Test
    public void should_delete_user_() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon"));

        // When
        OperationResult retrieved = adapterSpy.delete();

        // Then
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}), any(DefaultErrorHandler.class));
        verify(userJerseyRequestMock, times(1)).delete();
        assertNotNull(retrieved);
    }

    @Test
    /**
     * Test for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#createOrUpdate(com.jaspersoft.jasperserver.dto.authority.ClientUser)} and for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter#buildRequest}
     */
    public void test_update_user() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}), any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg", "Simon"));

        // When
        OperationResult retrieved = adapterSpy.createOrUpdate(userMock);

        // Then
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class), eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}), any(DefaultErrorHandler.class));
        verify(userJerseyRequestMock, times(1)).put(userMock);
        assertNotNull(retrieved);
    }

    @Test
    public void should_update_user() throws Exception {

        /* Given */
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                eq(sessionStorageMock),
                eq(ClientUser.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));

        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);
        doReturn("Simon").when(userMock).getUsername();
        doReturn("MyCoolOrg").when(userMock).getTenantId();

        /* Then */
        OperationResult<ClientUser> retrieved = adapterSpy.updateOrCreate(userMock);

        /* Then */
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
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, (String)null));

        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);
        doReturn("Simon").when(userMock).getUsername();
        doReturn("MyCoolOrg").when(userMock).getTenantId();

        /* When */
        OperationResult<ClientUser> retrieved = adapterSpy.updateOrCreate(userMock);

        /* Then */
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
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();
        doReturn("Simon").when(userMock).getUsername();

        /* When */
        OperationResult retrieved = adapterSpy.delete(userMock);

        /* Then */
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
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon"}),
                any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, "MyCoolOrg"));
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();

        /* When */
        OperationResult retrieved = adapterSpy.delete("Simon");

        /* Then */
        assertNotNull(retrieved);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, userJerseyRequestMock, operationResultMock, userMock);
    }
}