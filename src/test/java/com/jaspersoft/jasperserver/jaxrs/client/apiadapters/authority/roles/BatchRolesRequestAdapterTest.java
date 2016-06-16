package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
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
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link BatchRolesRequestAdapter}
 */
@SuppressWarnings("unchecked")
@PrepareForTest({BatchRolesRequestAdapter.class, JerseyRequest.class})
public class BatchRolesRequestAdapterTest extends PowerMockTestCase {
    public static final String SERVICE_URI = "roles";
    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<RolesListWrapper> requestMock;
    @Mock
    private OperationResult<RolesListWrapper> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        BatchRolesRequestAdapter adapter = spy(new BatchRolesRequestAdapter(sessionStorageMock, null));

        // Then
        Assert.assertNotNull(adapter);
        assertEquals(sessionStorageMock, getInternalState(adapter, "sessionStorage"));
    }

    @Test
    public void should_add_parameter_to_map() throws Exception {

        // Given
        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, null);

        // When
        BatchRolesRequestAdapter retrieved = adapter.param(RolesParameter.HAS_ALL_USERS, "true");
        MultivaluedHashMap<String, String> params = Whitebox.getInternalState(adapter, "params");

        // Then
        assertSame(retrieved, adapter);
        Assert.assertTrue(params.size() == 1);
        Assert.assertEquals(params.getFirst(RolesParameter.HAS_ALL_USERS.getParamName()), "true");
    }

    @Test
    public void should_get_role() {

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(RolesListWrapper.class),
                eq(new String[]{SERVICE_URI}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();
        BatchRolesRequestAdapter adapterSpy = spy(new BatchRolesRequestAdapter(sessionStorageMock, null));

        OperationResult<RolesListWrapper> retrievedResult = adapterSpy.get();

        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @Test
    public void should_get_resource_with_params_for_organization() {
        BatchRolesRequestAdapter adapterSpy = spy(new BatchRolesRequestAdapter(sessionStorageMock, "myOrg"));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(RolesListWrapper.class),
                eq(new String[]{"organizations", "myOrg", SERVICE_URI}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(adapterSpy).when(adapterSpy).param(RolesParameter.HAS_ALL_USERS, "true");
        doReturn(operationResultMock).when(requestMock).get();

        OperationResult<RolesListWrapper> retrievedResult = adapterSpy.get();

        assertSame(retrievedResult, operationResultMock);
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
    }

    @AfterMethod
    public void after() {
        sessionStorageMock = null;
        requestMock = null;
        operationResultMock = null;
    }
}