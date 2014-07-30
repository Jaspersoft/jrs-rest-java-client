package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.UsersListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link BatchUsersRequestAdapter}
 */
@PrepareForTest({BatchUsersRequestAdapter.class, MultivaluedHashMap.class, JerseyRequest.class})
public class BatchUsersRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<UsersListWrapper> requestMock;

    @Mock
    private Callback<OperationResult<UsersListWrapper>, Object> callbackMock;

    @Mock
    private Object resultMock;

    @Mock
    private OperationResult<UsersListWrapper> operationResultMock;

    @Mock
    private RequestBuilder<UsersListWrapper> requestBuilderMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructor1() {

        // When
        BatchUsersRequestAdapter adapter = new BatchUsersRequestAdapter(sessionStorageMock, "MyCoolOrg");
        MultivaluedMap<String, String> params =
                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
        String uri = (String) Whitebox.getInternalState(adapter, "uri");

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertNotNull(params);
        assertEquals(uri, "/organizations/MyCoolOrg/users");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructor2() {

        // When
        BatchUsersRequestAdapter adapter = new BatchUsersRequestAdapter(sessionStorageMock, null);
        MultivaluedMap<String, String> params =
                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");
        String uri = (String) Whitebox.getInternalState(adapter, "uri");

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertNotNull(params);
        assertEquals(uri, "/users");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void param() throws Exception {

        // Given
        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, "MyCoolOrg"));

        MultivaluedHashMap<String, String> mapSpy = spy(new MultivaluedHashMap<String, String>());
        Whitebox.setInternalState(adapterSpy, "params", mapSpy);

        // When
        BatchUsersRequestAdapter retrieved = adapterSpy.param(UsersParameter.HAS_ALL_REQUIRED_ROLES, "true");

        // Than
        assertSame(retrieved, adapterSpy);
        verify(mapSpy, times(1)).add("hasAllRequiredRoles", "true");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void get() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                eq(new String[]{"/organizations/MyCoolOrg/users"}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);

        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, "MyCoolOrg"));
        MultivaluedMap<String, String> params =
                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");

        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
        PowerMockito.doReturn(requestBuilderMock).when(requestMock).addParams(params);
        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);

        // When
        OperationResult<UsersListWrapper> retrieved = adapterSpy.get();

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                eq(new String[]{"/organizations/MyCoolOrg/users"}), any(DefaultErrorHandler.class));

        verify(requestMock).addParams(params);
        verify(requestMock, times(1)).get();
        verifyNoMoreInteractions(requestMock);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void asyncGet() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                eq(new String[]{"/organizations/MyCoolOrg/users"}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);

        BatchUsersRequestAdapter adapterSpy = spy(new BatchUsersRequestAdapter(sessionStorageMock, "MyCoolOrg"));
        MultivaluedMap<String, String> params =
                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");

        PowerMockito.doReturn(operationResultMock).when(requestMock).delete();
        PowerMockito.doReturn(requestBuilderMock).when(requestMock).addParams(params);
        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);

        // When
        adapterSpy.asyncGet(callbackMock);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(UsersListWrapper.class),
                eq(new String[]{"/organizations/MyCoolOrg/users"}), any(DefaultErrorHandler.class));

        verify(callbackMock, times(1)).execute(operationResultMock);
        verify(requestMock, times(1)).addParams(params);
        verify(requestMock, times(1)).delete();
        verifyNoMoreInteractions(requestMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, callbackMock, resultMock, operationResultMock, requestBuilderMock);
    }
}


