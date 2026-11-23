package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
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
import org.testng.Assert;
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
import static org.testng.Assert.assertNotSame;

/**
 * Unit tests for {@link SingleRoleRequestAdapter}
 */
@SuppressWarnings("unchecked")
public class SingleRoleRequestAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ClientRole roleMock;

    @Mock
    private JerseyRequest<ClientRole> jerseyRequestMock;

    @Mock
    private OperationResult<ClientRole> expectedOpResultMock;

    @Mock
    private OperationResult<ClientRole> roleOperationResult;

    @Mock
    private JerseyRequest<ClientRole> roleJerseyRequest;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, roleMock, jerseyRequestMock, expectedOpResultMock);
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

    @Test(enabled = false)
    public void should_delete_role_in_separate_thread() {
        // disabled — original was enabled = false
    }

    @Test
    public void should_delete_role_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();

            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));

            final Callback<OperationResult<ClientRole>, Void> callback =
                    Mockito.spy(new Callback<OperationResult<ClientRole>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientRole> data) {
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
            verify(callback, times(1)).execute(expectedOpResultMock);
            verify(jerseyRequestMock, times(1)).delete();
        }
    }

    @Test
    public void should_create_or_update_user_role_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class))).thenReturn(roleJerseyRequest);
            doReturn(roleOperationResult).when(roleJerseyRequest).put(roleMock);

            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));

            final Callback<OperationResult<ClientRole>, Void> callback =
                    spy(new Callback<OperationResult<ClientRole>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientRole> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(roleMock, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
            verify(callback, times(1)).execute(roleOperationResult);
            verify(roleJerseyRequest, times(1)).put(roleMock);
        }
    }

    @Test(enabled = false)
    public void should_retrieve_role_asynchronously() {
        // disabled — original was enabled = false
    }

    @Test
    public void should_retrieve_role() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(expectedOpResultMock).when(jerseyRequestMock).get();

            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
            adapterSpy.get();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class)), times(1));
            Mockito.verify(jerseyRequestMock, times(1)).get();
            Mockito.verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    public void should_update_role() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(expectedOpResultMock).when(jerseyRequestMock).put(roleMock);

            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
            adapterSpy.createOrUpdate(roleMock);

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class)), times(1));
            Mockito.verify(jerseyRequestMock, times(1)).put(roleMock);
            Mockito.verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    public void should_delete_role() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();

            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
            adapterSpy.delete();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class)), times(1));
            Mockito.verify(jerseyRequestMock, times(1)).delete();
            Mockito.verifyNoMoreInteractions(jerseyRequestMock);
        }
    }

    @Test
    public void should_invoke_private_logic_while_invocation_of_delete() throws Exception {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
            ArrayList<String> roleUri = (ArrayList<String>) getInternalState(adapterSpy, "roleUri");

            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(roleUri.toArray(new String[roleUri.size()])),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();

            OperationResult retrieved = adapterSpy.delete();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(roleUri.toArray(new String[roleUri.size()])),
                    any(DefaultErrorHandler.class)), times(1));
            Assert.assertNotNull(retrieved);
        }
    }

    @Test
    public void should_retrieve_role_asynchronously_() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientRole.class),
                    eq(new String[]{"organizations", "orgId", "roles", "roleName"}),
                    any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
            doReturn(expectedOpResultMock).when(jerseyRequestMock).get();

            SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));

            final Callback<OperationResult<ClientRole>, Void> callback =
                    spy(new Callback<OperationResult<ClientRole>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientRole> data) {
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
            verify(callback, times(1)).execute(expectedOpResultMock);
            verify(jerseyRequestMock, times(1)).get();
        }
    }
}
