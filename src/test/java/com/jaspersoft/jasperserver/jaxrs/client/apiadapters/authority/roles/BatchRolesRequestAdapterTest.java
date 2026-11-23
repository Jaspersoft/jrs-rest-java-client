package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
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

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link BatchRolesRequestAdapter}
 */
@SuppressWarnings("unchecked")
public class BatchRolesRequestAdapterTest {

    public static final String SERVICE_URI = "roles";

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<RolesListWrapper> requestMock;
    @Mock
    private OperationResult<RolesListWrapper> operationResultMock;

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
        BatchRolesRequestAdapter adapter = spy(new BatchRolesRequestAdapter(sessionStorageMock, null));

        Assert.assertNotNull(adapter);
        assertEquals(sessionStorageMock, getInternalState(adapter, "sessionStorage"));
    }

    @Test
    public void should_add_parameter_to_map() throws Exception {
        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, null);

        BatchRolesRequestAdapter retrieved = adapter.param(RolesParameter.HAS_ALL_USERS, "true");
        MultivaluedHashMap<String, String> params =
                (MultivaluedHashMap<String, String>) getInternalState(adapter, "params");

        assertSame(retrieved, adapter);
        Assert.assertTrue(params.size() == 1);
        Assert.assertEquals(params.getFirst(RolesParameter.HAS_ALL_USERS.getParamName()), "true");
    }

    @Test
    public void should_get_role() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(RolesListWrapper.class),
                    eq(new String[]{SERVICE_URI}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();
            BatchRolesRequestAdapter adapterSpy = spy(new BatchRolesRequestAdapter(sessionStorageMock, null));

            OperationResult<RolesListWrapper> retrievedResult = adapterSpy.get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }

    @Test
    public void should_get_resource_with_params_for_organization() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            BatchRolesRequestAdapter adapterSpy = spy(new BatchRolesRequestAdapter(sessionStorageMock, "myOrg"));
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(RolesListWrapper.class),
                    eq(new String[]{"organizations", "myOrg", SERVICE_URI}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            OperationResult<RolesListWrapper> retrievedResult = adapterSpy.get();

            assertSame(retrievedResult, operationResultMock);
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        }
    }
}
