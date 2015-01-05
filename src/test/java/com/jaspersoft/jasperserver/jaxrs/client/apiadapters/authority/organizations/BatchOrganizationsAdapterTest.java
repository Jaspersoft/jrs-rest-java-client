package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.Organization;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.OrganizationsListWrapper;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link BatchOrganizationsAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({JerseyRequest.class})
public class BatchOrganizationsAdapterTest extends PowerMockTestCase {

    private SessionStorage sessionStorageMock;
    private JerseyRequest<OrganizationsListWrapper> requestMock;
    private OperationResult<OrganizationsListWrapper> resultMock;
    private JerseyRequest<Organization> tenantJerseyRequestMock;
    private OperationResult<Organization> tenantOperationResultMock;
    public Organization tenantMock;

    @BeforeMethod
    public void before() {
        sessionStorageMock = Mockito.mock(SessionStorage.class);
        requestMock = Mockito.mock(JerseyRequest.class);
        resultMock = Mockito.mock(OperationResult.class);
        tenantJerseyRequestMock = Mockito.mock(JerseyRequest.class);
        tenantOperationResultMock = Mockito.mock(OperationResult.class);
        tenantMock = Mockito.mock(Organization.class);
    }

    @Test
    /**
     * for {@link BatchOrganizationsAdapter#asyncGet(Callback)}
     */
    public void should_run_get_method_asynchronously() throws Exception {

        /** Given **/
        PowerMockito.mockStatic(JerseyRequest.class);
        /*Power*/Mockito.when(buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class), eq(new String[]{"/organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        /*Power*/Mockito.doReturn(resultMock).when(requestMock).get();
        BatchOrganizationsAdapter adapterSpy = /*Power*/Mockito.spy(new BatchOrganizationsAdapter(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<OrganizationsListWrapper>, Void> callback = /*Power*/Mockito.spy(new Callback<OperationResult<OrganizationsListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<OrganizationsListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notifyAll();
                }
                return null;
            }
        });

        /*Power*/Mockito.doReturn(null).when(callback).execute(resultMock);

        /** When **/
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /** Then **/
        Mockito.verify(requestMock).get();
        Mockito.verify(callback).execute(resultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    /**
     * for {@link BatchOrganizationsAdapter#parameter(OrganizationParameter, String)}
     */
    public void should_add_parameter_to_map() throws Exception {

        /** Given **/
        BatchOrganizationsAdapter adapterSpy = /*Power*/Mockito.spy(new BatchOrganizationsAdapter(sessionStorageMock));

        /** When **/
        BatchOrganizationsAdapter retrieved = adapterSpy.parameter(OrganizationParameter.CREATE_DEFAULT_USERS, "true");
        MultivaluedHashMap<String, String> params = (MultivaluedHashMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");

        /** Then **/
        assertSame(retrieved, adapterSpy);
        Assert.assertTrue(params.size() == 1);
        Assert.assertEquals(params.getFirst(OrganizationParameter.CREATE_DEFAULT_USERS.getParamName()), "true");
    }

    @Test
    public void should_execute_create_method_logic_asynchronously() throws InterruptedException {

        /** Given **/
        PowerMockito.mockStatic(JerseyRequest.class);
        /*Power*/Mockito.when(buildRequest(eq(sessionStorageMock), eq(Organization.class), eq(new String[]{"/organizations"}), any(DefaultErrorHandler.class))).thenReturn(tenantJerseyRequestMock);
        /*Power*/Mockito.doReturn(tenantOperationResultMock).when(tenantJerseyRequestMock).post(tenantMock);
        BatchOrganizationsAdapter adapterSpy = /*Power*/Mockito.spy(new BatchOrganizationsAdapter(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<Organization>, Void> callback = /*Power*/Mockito.spy(new Callback<OperationResult<Organization>, Void>() {
            @Override
            public Void execute(OperationResult<Organization> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notifyAll();
                }
                return null;
            }
        });

        /*Power*/Mockito.doReturn(null).when(callback).execute(tenantOperationResultMock);

        /** When **/
        RequestExecution retrieved = adapterSpy.asyncCreate(tenantMock, callback);

        /** Wait **/
        synchronized (callback) {
            callback.wait(500);
        }

        /** Then **/
        Mockito.verify(tenantJerseyRequestMock).post(tenantMock);
        Mockito.verify(callback).execute(tenantOperationResultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_execute_create_method_logic() {

        //create
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(Organization.class), eq(new String[]{"/organizations"}), any(DefaultErrorHandler.class))).thenReturn(tenantJerseyRequestMock);
        PowerMockito.doReturn(tenantOperationResultMock).when(tenantJerseyRequestMock).post(tenantMock);
        BatchOrganizationsAdapter adapterSpy = PowerMockito.spy(new BatchOrganizationsAdapter(sessionStorageMock));

        OperationResult<Organization> retrievedResult = adapterSpy.create(tenantMock);

        assertSame(retrievedResult, tenantOperationResultMock);
        Mockito.verify(tenantJerseyRequestMock, times(1)).post(tenantMock);
        Mockito.verify(tenantJerseyRequestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @Test
    public void should_retrieve_resource() {

        //create
        PowerMockito.mockStatic(JerseyRequest.class);
        /*Power*/Mockito.when(buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class), eq(new String[]{"/organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        /*Power*/Mockito.doReturn(resultMock).when(requestMock).get();
        BatchOrganizationsAdapter adapterSpy = /*Power*/Mockito.spy(new BatchOrganizationsAdapter(sessionStorageMock));

        OperationResult<OrganizationsListWrapper> retrievedResult = adapterSpy.get();

        assertSame(retrievedResult, resultMock);
        Mockito.verify(requestMock, times(1)).get();
        Mockito.verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        tenantJerseyRequestMock = null;
        tenantOperationResultMock = null;
        tenantMock = null;
    }
}