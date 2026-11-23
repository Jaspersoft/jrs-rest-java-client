package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.OrganizationsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link BatchOrganizationsAdapter}
 */
@SuppressWarnings("unchecked")
public class BatchOrganizationsAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<OrganizationsListWrapper> requestMock;
    @Mock
    private OperationResult<OrganizationsListWrapper> operationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
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
        BatchOrganizationsAdapter adapter = spy(new BatchOrganizationsAdapter(sessionStorageMock));

        Assert.assertNotNull(adapter);
        assertEquals(sessionStorageMock, getInternalState(adapter, "sessionStorage"));
    }

    @Test
    public void should_run_get_method_asynchronously() throws Exception {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class),
                    eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();
            BatchOrganizationsAdapter adapterSpy = spy(new BatchOrganizationsAdapter(sessionStorageMock));

            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            final Callback<OperationResult<OrganizationsListWrapper>, Void> callback =
                    spy(new Callback<OperationResult<OrganizationsListWrapper>, Void>() {
                        @Override
                        public Void execute(OperationResult<OrganizationsListWrapper> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notifyAll();
                            }
                            return null;
                        }
                    });

            RequestExecution retrieved = adapterSpy.asyncGet(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            verify(requestMock).get();
            verify(callback).execute(operationResultMock);
            assertNotNull(retrieved);
            Assert.assertNotEquals(newThreadId.get(), currentThreadId);
        }
    }

    @Test
    public void should_add_parameter_to_map() throws Exception {
        BatchOrganizationsAdapter adapter = new BatchOrganizationsAdapter(sessionStorageMock);

        BatchOrganizationsAdapter retrieved = adapter.parameter(OrganizationParameter.CREATE_DEFAULT_USERS, "true");
        MultivaluedHashMap<String, String> params =
                (MultivaluedHashMap<String, String>) getInternalState(adapter, "params");

        assertSame(retrieved, adapter);
        Assert.assertTrue(params.size() == 1);
        Assert.assertEquals(params.getFirst(OrganizationParameter.CREATE_DEFAULT_USERS.getParamName()), "true");
    }

    @Test
    public void should_get_resource() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class),
                    eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();
            BatchOrganizationsAdapter adapterSpy = spy(new BatchOrganizationsAdapter(sessionStorageMock));

            OperationResult<OrganizationsListWrapper> retrievedResult = adapterSpy.get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }

    @Test
    public void should_get_resource_with_params() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            BatchOrganizationsAdapter adapterSpy = spy(new BatchOrganizationsAdapter(sessionStorageMock));
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(OrganizationsListWrapper.class),
                    eq(new String[]{"organizations"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<OrganizationsListWrapper> retrievedResult = adapterSpy.get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }
}
