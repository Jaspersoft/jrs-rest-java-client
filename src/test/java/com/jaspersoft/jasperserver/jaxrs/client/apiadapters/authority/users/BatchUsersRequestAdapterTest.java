package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
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
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNotSame;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Unit tests for {@link BatchUsersRequestAdapter}
 */
@SuppressWarnings("unchecked")
public class BatchUsersRequestAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<UsersListWrapper> requestMock;
    @Mock
    private OperationResult<UsersListWrapper> operationResultMock;

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
        BatchUsersRequestAdapter adapter = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));

        Assert.assertNotNull(adapter);
        assertEquals(sessionStorageMock, getInternalState(adapter, "sessionStorage"));
    }

    @Test
    public void should_run_get_method_asynchronously() throws Exception {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                    eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();
            BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));

            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            final Callback<OperationResult<UsersListWrapper>, Void> callback =
                    spy(new Callback<OperationResult<UsersListWrapper>, Void>() {
                        @Override
                        public Void execute(OperationResult<UsersListWrapper> data) {
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
            assertNotSame(currentThreadId, newThreadId.get());
        }
    }

    @Test
    public void should_get_resource() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                    eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();
            BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));

            OperationResult<UsersListWrapper> retrievedResult = adapterSpy.get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }

    @Test
    public void should_refuse_wrong_organization_and_get_resource() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                    eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();
            BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, ""));

            OperationResult<UsersListWrapper> retrievedResult = adapterSpy.get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }

    @Test
    public void should_get_resource_with_params() throws Exception {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, null));
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                    eq(new String[]{"users"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).addParams(any(MultivaluedHashMap.class));
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<UsersListWrapper> retrievedResult = adapterSpy.param(UsersParameter.INCLUDE_SUB_ORGS, "true").get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            assertTrue(((MultivaluedHashMap<String, String>) getInternalState(adapterSpy, "params")).size() == 1);
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }

    @Test
    public void should_get_resource_with_params_for_user_in_organization() throws Exception {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, "myOrg"));
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                    eq(new String[]{"organizations", "myOrg", "users"}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).addParams(any(MultivaluedHashMap.class));
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<UsersListWrapper> retrievedResult = adapterSpy.param(UsersParameter.INCLUDE_SUB_ORGS, "true").get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            assertTrue(((MultivaluedHashMap<String, String>) getInternalState(adapterSpy, "params")).size() == 1);
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }
}
