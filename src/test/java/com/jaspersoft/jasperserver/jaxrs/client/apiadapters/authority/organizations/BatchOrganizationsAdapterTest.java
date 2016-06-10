package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.OrganizationsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.MultivaluedHashMap;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link BatchOrganizationsAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({BatchOrganizationsAdapter.class, JerseyRequest.class})
public class BatchOrganizationsAdapterTest extends PowerMockTestCase {
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<OrganizationsListWrapper> requestMock;
    @Mock
    private OperationResult<OrganizationsListWrapper> operationResultMock;
    @Mock
    public ClientTenant tenantMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        BatchOrganizationsAdapter adapter = spy(new BatchOrganizationsAdapter(sessionStorageMock));

        // Then
        Assert.assertNotNull(adapter);
        assertEquals(sessionStorageMock, getInternalState(adapter, "sessionStorage"));
    }


    @Test
    /**
     * for {@link BatchOrganizationsAdapter#asyncGet(Callback)}
     */
    public void should_run_get_method_asynchronously() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class), eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();
        BatchOrganizationsAdapter adapterSpy = spy(new BatchOrganizationsAdapter(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<OrganizationsListWrapper>, Void> callback = spy(new Callback<OperationResult<OrganizationsListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<OrganizationsListWrapper> data) {
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
        verify(requestMock).get();
        verify(callback).execute(operationResultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    /**
     * for {@link BatchOrganizationsAdapter#parameter(OrganizationParameter, String)}
     */
    public void should_add_parameter_to_map() throws Exception {

        // Given
        BatchOrganizationsAdapter adapter = new BatchOrganizationsAdapter(sessionStorageMock);

        // When
        BatchOrganizationsAdapter retrieved = adapter.parameter(OrganizationParameter.CREATE_DEFAULT_USERS, "true");
        MultivaluedHashMap<String, String> params = Whitebox.getInternalState(adapter, "params");

        // Then
        assertSame(retrieved, adapter);
        Assert.assertTrue(params.size() == 1);
        Assert.assertEquals(params.getFirst(OrganizationParameter.CREATE_DEFAULT_USERS.getParamName()), "true");
    }


    @Test
    public void should_get_resource() {

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class), eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();
        BatchOrganizationsAdapter adapterSpy = spy(new BatchOrganizationsAdapter(sessionStorageMock));

        OperationResult<OrganizationsListWrapper> retrievedResult = adapterSpy.get();

        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @Test
    public void should_get_resource_with_params() {

        BatchOrganizationsAdapter adapterSpy = spy(new BatchOrganizationsAdapter(sessionStorageMock));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class), eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(adapterSpy).when(adapterSpy).parameter(OrganizationParameter.INCLUDE_PARENTS, true);
        doReturn(operationResultMock).when(requestMock).get();

        OperationResult<OrganizationsListWrapper> retrievedResult = adapterSpy.get();

        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        requestMock = null;
        operationResultMock = null;
        tenantMock = null;
    }
}