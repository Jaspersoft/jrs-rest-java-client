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
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotSame;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({OrganizationsService.class, JerseyRequest.class})
public class SingleOrganizationAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private SingleOrganizationAdapter singleOrganizationAdapterMock;
    @Mock
    private JerseyRequest<ClientTenant> jerseyRequestMock;
    @Mock
    private OperationResult<ClientTenant> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_proper_instance_of_SingleOrganizationAdapter() throws Exception {

        // When
        SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, "orgId");

        // Then
        assertEquals(Whitebox.getInternalState(adapter, "sessionStorage"), sessionStorageMock);
        assertEquals(Whitebox.getInternalState(adapter, "organizationId"), "orgId");
    }

    @Test
    public void should_run_get_method_asynchronously() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).get();
        SingleOrganizationAdapter adapterSpy = spy(new SingleOrganizationAdapter(sessionStorageMock, "orgId"));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientTenant>, Void> callback = spy(new Callback<OperationResult<ClientTenant>, Void>() {
            @Override
            public Void execute(OperationResult<ClientTenant> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notifyAll();
                }
                return null;
            }
        });

        doReturn(null).when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(jerseyRequestMock).get();
        verify(callback).execute(operationResultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_run_get_method_synchronously() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).get();
        SingleOrganizationAdapter adapterSpy = spy(new SingleOrganizationAdapter(sessionStorageMock, "orgId"));
        // When
        OperationResult<ClientTenant> retrieved = adapterSpy.get();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class));
        verify(jerseyRequestMock).get();
        verifyPrivate(adapterSpy).invoke("buildRequest");
        assertNotNull(retrieved);

    }


    @Test
    public void should_run_delete_method() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).delete();
        SingleOrganizationAdapter adapterSpy = spy(new SingleOrganizationAdapter(sessionStorageMock, "orgId"));
        // When
        OperationResult<ClientTenant> retrieved = adapterSpy.delete();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class));
        verify(jerseyRequestMock).delete();
        verifyPrivate(adapterSpy).invoke("buildRequest");
        assertNotNull(retrieved);

    }

    @Test
    public void should_run_createOrUpdate_method() throws Exception {

        // Given
        ClientTenant clientTenantMock = new ClientTenant();
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        doReturn(operationResultMock).when(jerseyRequestMock).put(anyString());
        SingleOrganizationAdapter adapterSpy = spy(new SingleOrganizationAdapter(sessionStorageMock, "orgId"));
        // When
        OperationResult<ClientTenant> retrieved = adapterSpy.createOrUpdate(clientTenantMock);

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientTenant.class), eq(new String[]{"/organizations", "orgId"}), any(DefaultErrorHandler.class));
        verify(jerseyRequestMock).put(anyString());
        verifyPrivate(adapterSpy).invoke("buildRequest");
        assertNotNull(retrieved);

    }
}