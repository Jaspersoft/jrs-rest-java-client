package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService}
 */
@PrepareForTest({SingleUserRequestAdapter.class, BatchUsersRequestAdapter.class, UsersService.class})
public class UsersServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private SingleUserRequestAdapter singleUserRequestAdapterMock;

    @Mock
    private BatchUsersRequestAdapter batchUsersRequestAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_set_organization_id() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.forOrganization("MyCoolOrg");
        // Then
        assertSame(retrieved, service);
        assertEquals(Whitebox.getInternalState(service, "organizationId"), "MyCoolOrg");
    }

    @Test
    public void should_set_organization_id_as_object() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.forOrganization(new ClientTenant().setId("MyCoolOrg"));
        // Then
        assertSame(retrieved, service);
        assertEquals(Whitebox.getInternalState(service, "organizationId"), "MyCoolOrg");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_name_is_empty() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("");
        // Then
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_name_is_null() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization((String)null);
        // Then
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_users_organization_name_is_empty() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization(new ClientTenant().setId(""));
        // Then
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_is_null() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization((ClientTenant)null);
        // Then
    }

    @Test
    public void should_return_proper_user_adapter_when_invoke_user_method_with_string() throws Exception {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        whenNew(SingleUserRequestAdapter.class).withArguments(sessionStorageMock, new ClientUser().setUsername("Simon"))
                .thenReturn(singleUserRequestAdapterMock);

        SingleUserRequestAdapter retrieved = service.user("Simon");
        // Then
        assertSame(retrieved, singleUserRequestAdapterMock);
    }

    @Test
    public void should_return_proper_user_adapter_when_invoke_user_method_with_object() throws Exception {
        // Given
        UsersService service = new UsersService(sessionStorageMock);
        ClientUser clientUser = new ClientUser().setUsername("Simon");
        whenNew(SingleUserRequestAdapter.class).withArguments(sessionStorageMock, clientUser)
                .thenReturn(singleUserRequestAdapterMock);
        // When
        SingleUserRequestAdapter retrieved = service.user(clientUser);
        // Then
        assertSame(retrieved, singleUserRequestAdapterMock);
    }

    @Test
    public void should_return_BatchUsersRequestAdapter() throws Exception {
        // Given
        UsersService service = new UsersService(sessionStorageMock);
        whenNew(BatchUsersRequestAdapter.class).withArguments(eq(sessionStorageMock),
                anyString()).thenReturn(batchUsersRequestAdapterMock);

        // When
        BatchUsersRequestAdapter retrieved = service.allUsers();
        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, batchUsersRequestAdapterMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleUserRequestAdapterMock, batchUsersRequestAdapterMock);
    }
}