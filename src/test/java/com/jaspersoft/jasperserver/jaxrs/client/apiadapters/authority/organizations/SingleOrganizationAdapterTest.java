package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.concurrent.atomic.AtomicInteger;
import org.mockito.Mock;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
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
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.SingleOrganizationAdapter}
 */
@PrepareForTest({SingleOrganizationAdapter.class, JerseyRequest.class})

public class SingleOrganizationAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;


    @Mock
    private JerseyRequest<ClientTenant> requestMock;

    @Mock
    private OperationResult<ClientTenant> resultMock;

    @Mock
    private ClientTenant clientTenantMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        ClientTenant clientTenant = (ClientTenant) getInternalState(adapter, "clientTenant");

        // Then
        assertNotNull(adapter);
        assertEquals(clientTenant, clientTenantMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        // When
        adapter.get();

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).get();
        verifyNoMoreInteractions(requestMock);
    }


    @Test
    public void should_invoke_private_method() {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientTenant.class),
                eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        doReturn("myOrg").when(clientTenantMock).getId();
        doReturn(resultMock).when(requestMock).put(clientTenantMock);

        // When
        OperationResult<ClientTenant> retrieved = adapter.createOrUpdate(clientTenantMock);

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"organizations", "myOrg"}), any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).put(clientTenantMock);
    }


    @Test
    public void should_delete_organization() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        // When
        OperationResult retrieved = adapter.delete();

        // Then
        assertSame(retrieved, resultMock);
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
    }


    @Test
    public void should_create_or_update_attribute_for_user() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).put(clientTenantMock);

        // When
        OperationResult retrieved = adapter.createOrUpdate(clientTenantMock);

        // Then
        assertSame(retrieved, resultMock);
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
    }

    @Test
    public void should_create_attribute_for_user() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "request");
        doReturn(resultMock).when(requestMock).post(clientTenantMock);

        // When
        OperationResult retrieved = adapter.create();

        // Then
        assertSame(retrieved, resultMock);
        verifyPrivate(adapter, times(1)).invoke("request");
    }

    @Test
    public void should_return_organization_asynchronously() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientTenant>, Void> callback = spy(new Callback<OperationResult<ClientTenant>, Void>() {
            @Override
            public Void execute(OperationResult<ClientTenant> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {

                    this.notify();

                return null;
            }
        }});


        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = adapter.asyncGet(callback);


        synchronized (callback) {
            callback.wait(1000);
        }

        // Then

        verify(requestMock).get();
        verify(callback).execute(resultMock);

        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test

    public void should_delete_organization_asynchronously() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientTenant>, Void> callback = spy(new Callback<OperationResult<ClientTenant>, Void>() {
            @Override
            public Void execute(OperationResult<ClientTenant> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = adapter.asyncDelete(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(requestMock).delete();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_create_or_update_attribute_for_user_asynchronously() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientTenant>, Void> callback = spy(new Callback<OperationResult<ClientTenant>, Void>() {
            @Override
            public Void execute(OperationResult<ClientTenant> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).put(clientTenantMock);
        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = adapter.asyncCreateOrUpdate(clientTenantMock, callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(requestMock).put(clientTenantMock);
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_set_parameter_and_invoke_private_method() throws Exception {

        // Given
        SingleOrganizationAdapter adapter = spy(new SingleOrganizationAdapter(sessionStorageMock, clientTenantMock));
        doReturn(requestMock).when(adapter, "request");
        doReturn(adapter).when(adapter).parameter(OrganizationParameter.CREATE_DEFAULT_USERS, true);
        doReturn(resultMock).when(requestMock).post(clientTenantMock);

        // When
        OperationResult<ClientTenant> retrieved = adapter.parameter(OrganizationParameter.CREATE_DEFAULT_USERS,true).create();

        // Then
        assertNotNull(retrieved);
        verify(requestMock, times(1)).post(clientTenantMock);
        verify(adapter, times(1)).parameter(OrganizationParameter.CREATE_DEFAULT_USERS, true);

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock);

    }
}