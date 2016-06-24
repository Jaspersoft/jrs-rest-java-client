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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;


/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.SingleUserRequestAdapter}
 */
@PrepareForTest({SingleUserRequestAdapter.class,JerseyRequest.class})
public class SingleUserRequestAdapterTest extends PowerMockTestCase {

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

    @Test(testName = "constructor")
    public void should_invoke_constructor_with_proper_three_params() {

        // When
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, userMock);

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(((ArrayList<String>)Whitebox.getInternalState(adapter, "uri")).get(0), "users");
        assertEquals(Whitebox.getInternalState(adapter, "user"), userMock);
    }


    @Test
    public void should_get_user() throws Exception {

        // Given
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));
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
    public void should_delete_user() throws Exception {

        // Given
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));
        doReturn(userJerseyRequestMock).when(adapterSpy, "buildRequest");
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.delete();

        // Then
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        verify(userJerseyRequestMock, times(1)).delete();
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_create_or_update_user() throws Exception {

        // Given
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));
        doReturn(userJerseyRequestMock).when(adapterSpy, "buildRequest");
        doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);

        // When
        OperationResult<ClientUser> retrieved = adapterSpy.createOrUpdate(userMock);

        // Then
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest");
        verify(userJerseyRequestMock, times(1)).put(userMock);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_invoke_private_method() {

        // Given
        SingleUserRequestAdapter adapter = spy(new SingleUserRequestAdapter(sessionStorageMock, new ClientUser().setTenantId("myOrg").setUsername("Simon")));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                eq(new String[]{"organizations", "myOrg", "users", "Simon"}), any(DefaultErrorHandler.class)))
                .thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).delete();

        // When
        OperationResult<ClientUser> retrieved = adapter.delete();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                eq(new String[]{"organizations", "myOrg", "users", "Simon"}), any(DefaultErrorHandler.class));
        verify(userJerseyRequestMock, times(1)).delete();
    }


    @Test
    public void should_refuse_wrong_organization_and_get_user() {

        // Given
        SingleUserRequestAdapter adapter = spy(new SingleUserRequestAdapter(sessionStorageMock, new ClientUser().setTenantId("").setUsername("Simon")));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                eq(new String[]{"users", "Simon"}), any(DefaultErrorHandler.class)))
                .thenReturn(userJerseyRequestMock);
        doReturn(operationResultMock).when(userJerseyRequestMock).get();

        // When
        OperationResult<ClientUser> retrieved = adapter.get();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                eq(new String[]{"users", "Simon"}), any(DefaultErrorHandler.class));
        verify(userJerseyRequestMock, times(1)).get();
    }

    @Test
    public void should_get_user_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));

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
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(operationResultMock);
        verify(userJerseyRequestMock, times(1)).get();
    }

    @Test
    public void should_create_or_update_user_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));

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
    public void should_delete_user_asynchronously() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();
        final SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));

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

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, userJerseyRequestMock, operationResultMock, userMock);
    }
}