package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.MultivaluedHashMap;
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

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;

/**
 * Unit tests for {@link SingleRoleRequestAdapter}
 */
@PrepareForTest({SingleRoleRequestAdapter.class, JerseyRequest.class})
public class SingleRoleRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ClientRole roleMock;

    @Mock
    private MultivaluedHashMap<String, String> mapMock;

    @Mock
    private JerseyRequest<ClientRole> jerseyRequestMock;
    @Mock
    private OperationResult<ClientRole> expectedOpResultMock;


    @Mock
    private OperationResult<ClientRole> roleOperationResult;
    @Mock
    private JerseyRequest<ClientRole> roleJerseyRequest;


    @Mock
    private Callback<OperationResult<ClientRole>, Object> callbackMock;
    @Mock
    private Callback<OperationResult<ClientRole>, Object> callback2;


    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(enabled = false)
    public void should_delete_role_in_separate_thread() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = PowerMockito.spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        PowerMockito.doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest", ClientRole.class);
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();
        PowerMockito.doReturn(expectedOpResultMock).when(callbackMock).execute(expectedOpResultMock);

        // When
        adapterSpy.asyncDelete(callbackMock);

        // Then
        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("buildRequest", eq(ClientRole.class));
        Mockito.verify(callbackMock, times(1)).execute(expectedOpResultMock);
    }

    @Test
    public void should_delete_role_asynchronously() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleRoleRequestAdapter adapterSpy = PowerMockito.spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));

        final Callback<OperationResult<ClientRole>, Void> callback = Mockito.spy(new Callback<OperationResult<ClientRole>, Void>() {
            @Override
            public Void execute(OperationResult<ClientRole> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest");
        Mockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();
        Mockito.doReturn(null).when(callback).execute(expectedOpResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncDelete(callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(expectedOpResultMock);
        verify(jerseyRequestMock, times(1)).delete();
    }

    @Test
    /**
     * for {@link SingleRoleRequestAdapter#asyncCreateOrUpdate(ClientRole, Callback)}
     */
    public void should_create_or_update_user_role_asynchronously() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleRoleRequestAdapter adapterSpy = PowerMockito.spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));

        final Callback<OperationResult<ClientRole>, Void> callback = spy(new Callback<OperationResult<ClientRole>, Void>() {
            @Override
            public Void execute(OperationResult<ClientRole> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(roleJerseyRequest).when(adapterSpy, "buildRequest");
        doReturn(roleOperationResult).when(roleJerseyRequest).put(roleMock);
        doReturn(null).when(callback).execute(roleOperationResult);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(roleMock, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(roleOperationResult);
        verify(roleJerseyRequest, times(1)).put(roleMock);
    }

    @Test(enabled = false)
    public void should_retrieve_role_asynchronously() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = PowerMockito.spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        PowerMockito.doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest");
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).get();
        PowerMockito.doReturn(expectedOpResultMock).when(callbackMock).execute(expectedOpResultMock);

        // When
        adapterSpy.asyncGet(callbackMock);

        // Then
        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        Mockito.verify(callbackMock, times(1)).execute(expectedOpResultMock);
    }

    @Test
    public void should_retrieve_role() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        PowerMockito.doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest");
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).get();

        // When
        adapterSpy.get();

        // Then
        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        Mockito.verify(jerseyRequestMock, times(1)).get();
        Mockito.verifyNoMoreInteractions(jerseyRequestMock);
    }

    @Test
    public void should_update_role() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        PowerMockito.doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest");
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).put(roleMock);

        // When
        adapterSpy.createOrUpdate(roleMock);

        // Then
        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        Mockito.verify(jerseyRequestMock, times(1)).put(roleMock);
        Mockito.verifyNoMoreInteractions(jerseyRequestMock);
    }

    @Test
    public void should_delete_role() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        PowerMockito.doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest");
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();

        // When
        adapterSpy.delete();

        // Then
        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        Mockito.verify(jerseyRequestMock, times(1)).delete();
        Mockito.verifyNoMoreInteractions(jerseyRequestMock);
    }

    @Test
    public void should_invoke_private_logic_while_invocation_of_delete() throws Exception {

        PowerMockito.mockStatic(JerseyRequest.class);
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock,
                "orgId", "roleName"));
        ArrayList<String> roleUri = (ArrayList<String>) Whitebox.getInternalState(adapterSpy, "roleUri");
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(ClientRole.class), eq(roleUri.toArray(new String[roleUri.size()])),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();

        // When
        OperationResult retrieved = adapterSpy.delete();

        // Then
        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientRole.class), eq(roleUri.toArray(new String[roleUri.size()])),
                any(DefaultErrorHandler.class));
        PowerMockito.verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        Assert.assertNotNull(retrieved);
    }

    @Test
    public void should_retrieve_role_asynchronously_() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                eq(new String[]{"organizations", "orgId", "roles", "roleName"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);

        SingleRoleRequestAdapter adapterSpy = PowerMockito.spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));

        final Callback<OperationResult<ClientRole>, Void> callback = spy(new Callback<OperationResult<ClientRole>, Void>() {
            @Override
            public Void execute(OperationResult<ClientRole> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).get();
        PowerMockito.doReturn(null).when(callback).execute(expectedOpResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(expectedOpResultMock);
        verify(jerseyRequestMock, times(1)).get();
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, roleMock, mapMock, jerseyRequestMock, expectedOpResultMock,
                callbackMock);
    }
}