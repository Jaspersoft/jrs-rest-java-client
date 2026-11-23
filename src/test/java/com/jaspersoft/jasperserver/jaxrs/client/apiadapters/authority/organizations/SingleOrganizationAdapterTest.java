package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link SingleOrganizationAdapter}
 */
@SuppressWarnings("unchecked")
public class SingleOrganizationAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientTenant> requestMock;

    @Mock
    private OperationResult<ClientTenant> resultMock;

    @Mock
    private ClientTenant clientTenantMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
        doReturn("myOrg").when(clientTenantMock).getId();
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, requestMock, resultMock);
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

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() throws Exception {
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        ClientTenant clientTenant = (ClientTenant) getInternalState(adapter, "clientTenant");

        assertNotNull(adapter);
        assertEquals(clientTenant, clientTenantMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            adapter.get();

            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class)), times(1));
            verify(requestMock, times(1)).get();
            verifyNoMoreInteractions(requestMock);
        }
    }

    @Test
    public void should_invoke_private_method() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(clientTenantMock);

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            OperationResult<ClientTenant> retrieved = adapter.createOrUpdate(clientTenantMock);

            assertNotNull(retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class)), times(1));
            verify(requestMock, times(1)).put(clientTenantMock);
        }
    }

    @Test
    public void should_delete_organization() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).delete();

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            OperationResult retrieved = adapter.delete();

            assertSame(retrieved, resultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_create_or_update_attribute_for_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(clientTenantMock);

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            OperationResult retrieved = adapter.createOrUpdate(clientTenantMock);

            assertSame(retrieved, resultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_create_attribute_for_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).post(clientTenantMock);

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            OperationResult retrieved = adapter.create();

            assertSame(retrieved, resultMock);
            verify(requestMock, times(1)).post(clientTenantMock);
        }
    }

    @Test
    public void should_return_organization_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).get();

            final Callback<OperationResult<ClientTenant>, Void> callback =
                    spy(new Callback<OperationResult<ClientTenant>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientTenant> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            RequestExecution retrieved = adapter.asyncGet(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            verify(requestMock).get();
            verify(callback).execute(resultMock);
            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
        }
    }

    @Test
    public void should_delete_organization_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).delete();

            final Callback<OperationResult<ClientTenant>, Void> callback =
                    spy(new Callback<OperationResult<ClientTenant>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientTenant> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            RequestExecution retrieved = adapter.asyncDelete(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            verify(requestMock).delete();
            verify(callback).execute(resultMock);
            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
        }
    }

    @Test
    public void should_create_or_update_attribute_for_user_asynchronously() throws Exception {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(resultMock).when(requestMock).put(clientTenantMock);

            final Callback<OperationResult<ClientTenant>, Void> callback =
                    spy(new Callback<OperationResult<ClientTenant>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientTenant> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock);
            RequestExecution retrieved = adapter.asyncCreateOrUpdate(clientTenantMock, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            verify(requestMock).put(clientTenantMock);
            verify(callback).execute(resultMock);
            assertNotNull(retrieved);
            assertNotSame(currentThreadId, newThreadId.get());
        }
    }

    @Test
    public void should_set_parameter_and_invoke_private_method() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                    eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).addParams(any());
            doReturn(resultMock).when(requestMock).post(clientTenantMock);

            SingleOrganizationAdapter adapterSpy = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
            OperationResult<ClientTenant> retrieved = adapterSpy.parameter(OrganizationParameter.CREATE_DEFAULT_USERS, true).create();

            assertNotNull(retrieved);
            verify(requestMock, times(1)).post(clientTenantMock);
            verify(adapterSpy, times(1)).parameter(OrganizationParameter.CREATE_DEFAULT_USERS, true);
        }
    }
}
