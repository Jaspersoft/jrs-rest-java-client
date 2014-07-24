package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertSame;

/**
 * Unit tests for {@link BatchRolesRequestAdapter}
 */
@PrepareForTest({BatchRolesRequestAdapter.class, MultivaluedHashMap.class, JerseyRequest.class})
public class BatchRolesRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private MultivaluedHashMap<String, String> mapMock;

    @Mock
    private JerseyRequest<RolesListWrapper> jerseyRequestMock;

    @Mock
    private OperationResult<RolesListWrapper> expectedOpResultMock;

    @Mock
    private Callback<OperationResult<RolesListWrapper>, Object> callbackMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructor() {

        // When
        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, "9454");
        MultivaluedMap<String, String> retrievedParams =
                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");

        String retrievedUri = (String) Whitebox.getInternalState(adapter, "uri");

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(retrievedUri, "/organizations/9454/roles");
        assertNotNull(retrievedParams);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void constructor2() {

        // When
        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, null);
        MultivaluedMap<String, String> retrievedParams =
                (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");

        String retrievedUri = (String) Whitebox.getInternalState(adapter, "uri");

        // Than
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertEquals(retrievedUri, "/roles");
        assertNotNull(retrievedParams);
    }

    @Test
    public void param() throws Exception {

        // Given
        MultivaluedHashMap<String, String> mapMock = PowerMockito.mock(MultivaluedHashMap.class);
        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(mapMock);
        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, "9454");

        // When
        BatchRolesRequestAdapter retrieved = adapter.param(RolesParameter.HAS_ALL_USERS, "value");

        // Than
        assertSame(retrieved, adapter);
        Mockito.verify(mapMock).add(RolesParameter.HAS_ALL_USERS.getParamName(), "value");
        Mockito.verifyNoMoreInteractions(mapMock);
    }

    @Test
    public void get() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(RolesListWrapper.class), eq(new String[]{"/organizations/9454/roles"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(mapMock);

        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).get();
        InOrder inOrder = Mockito.inOrder(jerseyRequestMock);
        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, "9454");

        // When
        OperationResult<RolesListWrapper> retrievedResult = adapter.get();

        // Than
        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(RolesListWrapper.class), eq(new String[]{"/organizations/9454/roles"}), any(DefaultErrorHandler.class));

        assertSame(retrievedResult, expectedOpResultMock);
        inOrder.verify(jerseyRequestMock, times(1)).addParams(mapMock);
        inOrder.verify(jerseyRequestMock, times(1)).get();
    }

    @Test
    public void asyncGet() throws Exception {

        // ?
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock), eq(RolesListWrapper.class), eq(new String[]{"/organizations/9454/roles"}), any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(mapMock);
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).get();
        PowerMockito.doReturn(expectedOpResultMock).when(callbackMock).execute(expectedOpResultMock);

        BatchRolesRequestAdapter adapter = new BatchRolesRequestAdapter(sessionStorageMock, "9454");

        RequestExecution requestExecution = adapter.asyncGet(callbackMock);

        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(RolesListWrapper.class), eq(new String[]{"/organizations/9454/roles"}), any(DefaultErrorHandler.class));
        Mockito.verify(jerseyRequestMock, times(1)).addParams(mapMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, mapMock, jerseyRequestMock, expectedOpResultMock, callbackMock);
    }
}