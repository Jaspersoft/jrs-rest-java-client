package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.dto.authority.ClientRole;
import com.jaspersoft.jasperserver.dto.authority.RolesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.SingleRoleRequestAdapter}
 */
@PrepareForTest({SingleRoleRequestAdapter.class, JerseyRequest.class})
public class SingleRoleRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private ClientRole roleMock;

    @Mock
    private MultivaluedHashMap<String, String> mapMock;

    @Mock
    private JerseyRequest<ClientRole> jerseyRequestMock;

    @Mock
    private OperationResult<ClientRole> expectedOpResultMock;

    @Mock
    private OperationResult<RolesListWrapper> result2;

    @Mock
    private Callback<OperationResult<ClientRole>, Object> callbackMock;

    @Mock
    private Callback<OperationResult<RolesListWrapper>, Object> callback2;

    @Mock
    private JerseyRequest<RolesListWrapper> request2;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void asyncDelete() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest", ClientRole.class);
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).delete();
        doReturn(expectedOpResultMock).when(callbackMock).execute(expectedOpResultMock);

        // When
        adapterSpy.asyncDelete(callbackMock);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest", eq(ClientRole.class));
        verify(callbackMock, times(1)).execute(expectedOpResultMock);
    }

    @Test
    public void asyncCreateOrUpdate() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        doReturn(request2).when(adapterSpy, "buildRequest", RolesListWrapper.class);
        PowerMockito.doReturn(result2).when(request2).put(roleMock);
        doReturn(result2).when(callback2).execute(result2);

        // When
        adapterSpy.asyncCreateOrUpdate(roleMock, callback2);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest", eq(RolesListWrapper.class));
        verify(callback2, times(1)).execute(result2);
    }

    @Test
    public void asyncGet() throws Exception {

        // Given
        SingleRoleRequestAdapter adapterSpy = spy(new SingleRoleRequestAdapter(sessionStorageMock, "orgId", "roleName"));
        doReturn(jerseyRequestMock).when(adapterSpy, "buildRequest", ClientRole.class);
        PowerMockito.doReturn(expectedOpResultMock).when(jerseyRequestMock).get();
        doReturn(expectedOpResultMock).when(callbackMock).execute(expectedOpResultMock);

        // When
        adapterSpy.asyncGet(callbackMock);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("buildRequest", eq(ClientRole.class));
        verify(callbackMock, times(1)).execute(expectedOpResultMock);
    }

    @Test
    public void get() {

    }

    @Test
    public void createOrUpdate() {

    }

    @Test
    public void delete() {

    }

    @Test
    public void buildRequest() {

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, roleMock, mapMock, jerseyRequestMock, expectedOpResultMock,
                callbackMock);
    }
}