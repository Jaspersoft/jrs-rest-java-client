package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.BatchOrganizationsAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({BatchUsersRequestAdapter.class, JerseyRequest.class})
public class BatchUsersRequestAdapterTest extends PowerMockTestCase {
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<UsersListWrapper> requestMock;
    @Mock
    private OperationResult<UsersListWrapper> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        BatchUsersRequestAdapter adapter = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));

        // Then
        Assert.assertNotNull(adapter);
        assertEquals(sessionStorageMock, getInternalState(adapter, "sessionStorage"));
    }


    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.BatchOrganizationsAdapter#asyncGet(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_run_get_method_asynchronously() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class), eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();
        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<UsersListWrapper>, Void> callback = spy(new Callback<OperationResult<UsersListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<UsersListWrapper> data) {
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
    public void should_get_resource() {
        // Given
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class), eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();
        doReturn(requestMock).when(requestMock).addParams(params);
        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));
        //When
        OperationResult<UsersListWrapper> retrievedResult = adapterSpy.get();
        // Then
        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(params);
    }
    @Test
    public void should_refuse_wrong_organization_and_get_resource() {
        // Given
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class), eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();
        doReturn(requestMock).when(requestMock).addParams(params);
        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, ""));
        //When
        OperationResult<UsersListWrapper> retrievedResult = adapterSpy.get();
        // Then
        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(params);
    }

    @Test
    public void should_get_resource_with_params() {
        // Given
        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class), eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addParams(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(requestMock).get();
        // When
        OperationResult<UsersListWrapper> retrievedResult = adapterSpy.param(UsersParameter.INCLUDE_SUB_ORGS, "true").get();
        // Then
        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        assertTrue(((MultivaluedHashMap<String, String>)getInternalState(adapterSpy, "params")).size()== 1);
        verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @Test
    public void should_get_resource_with_params_for_user_in_organization() {
        // Given
        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, "myOrg"));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                eq(new String[]{"organizations", "myOrg", "users"}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addParams(any(MultivaluedHashMap.class));
        doReturn(operationResultMock).when(requestMock).get();
        // When
        OperationResult<UsersListWrapper> retrievedResult = adapterSpy.param(UsersParameter.INCLUDE_SUB_ORGS, "true").get();
        // Then
        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        assertTrue(((MultivaluedHashMap<String, String>)getInternalState(adapterSpy, "params")).size()== 1);
        verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        requestMock = null;
        operationResultMock = null;
    }
}