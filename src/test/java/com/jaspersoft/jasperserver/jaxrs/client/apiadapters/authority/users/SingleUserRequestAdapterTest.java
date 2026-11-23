package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link SingleUserRequestAdapter}
 */
@SuppressWarnings("unchecked")
public class SingleUserRequestAdapterTest {

    @Mock
    public SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientUser> userJerseyRequestMock;

    @Mock
    private OperationResult<ClientUser> operationResultMock;

    @Mock
    private ClientUser userMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
        doReturn("testUser").when(userMock).getUsername();
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, userJerseyRequestMock, operationResultMock, userMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test(testName = "constructor")
    public void should_invoke_constructor_with_proper_three_params() throws Exception {
        SingleUserRequestAdapter adapter = new SingleUserRequestAdapter(sessionStorageMock, userMock);

        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(((ArrayList<String>) getInternalState(adapter, "uri")).get(0), "users");
        assertSame(getInternalState(adapter, "user"), userMock);
    }

    @Test
    public void should_get_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).get();

            SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));
            OperationResult<ClientUser> retrieved = adapterSpy.get();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)), times(1));
            verify(userJerseyRequestMock, times(1)).get();
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_delete_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).delete();

            SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));
            OperationResult<ClientUser> retrieved = adapterSpy.delete();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)), times(1));
            verify(userJerseyRequestMock, times(1)).delete();
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_create_or_update_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);

            SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));
            OperationResult<ClientUser> retrieved = adapterSpy.createOrUpdate(userMock);

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)), times(1));
            verify(userJerseyRequestMock, times(1)).put(userMock);
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void should_invoke_private_method() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"organizations", "myOrg", "users", "Simon"}),
                    any(DefaultErrorHandler.class))).thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).delete();

            SingleUserRequestAdapter adapter = spy(new SingleUserRequestAdapter(sessionStorageMock,
                    new ClientUser().setTenantId("myOrg").setUsername("Simon")));
            OperationResult<ClientUser> retrieved = adapter.delete();

            assertNotNull(retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"organizations", "myOrg", "users", "Simon"}),
                    any(DefaultErrorHandler.class)), times(1));
            verify(userJerseyRequestMock, times(1)).delete();
        }
    }

    @Test
    public void should_refuse_wrong_organization_and_get_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "Simon"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).get();

            SingleUserRequestAdapter adapter = spy(new SingleUserRequestAdapter(sessionStorageMock,
                    new ClientUser().setTenantId("").setUsername("Simon")));
            OperationResult<ClientUser> retrieved = adapter.get();

            assertNotNull(retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "Simon"}), any(DefaultErrorHandler.class)), times(1));
            verify(userJerseyRequestMock, times(1)).get();
        }
    }

    @Test
    public void should_get_user_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).get();

            SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));

            final Callback<OperationResult<ClientUser>, Void> callback =
                    spy(new Callback<OperationResult<ClientUser>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientUser> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            RequestExecution retrieved = adapterSpy.asyncGet(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(callback, times(1)).execute(operationResultMock);
            verify(userJerseyRequestMock, times(1)).get();
        }
    }

    @Test
    public void should_create_or_update_user_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).put(userMock);

            SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));

            final Callback<OperationResult<ClientUser>, Void> callback =
                    spy(new Callback<OperationResult<ClientUser>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientUser> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(userMock, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(callback, times(1)).execute(operationResultMock);
            verify(userJerseyRequestMock, times(1)).put(userMock);
        }
    }

    @Test
    public void should_delete_user_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientUser.class),
                    eq(new String[]{"users", "testUser"}), any(DefaultErrorHandler.class)))
                    .thenReturn(userJerseyRequestMock);
            doReturn(operationResultMock).when(userJerseyRequestMock).delete();

            final SingleUserRequestAdapter adapterSpy = spy(new SingleUserRequestAdapter(sessionStorageMock, userMock));

            final Callback<OperationResult<ClientUser>, Void> callback =
                    spy(new Callback<OperationResult<ClientUser>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientUser> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            RequestExecution retrieved = adapterSpy.asyncDelete(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(callback, times(1)).execute(operationResultMock);
            verify(userJerseyRequestMock, times(1)).delete();
        }
    }
}
