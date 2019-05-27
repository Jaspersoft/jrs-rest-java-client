package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
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
        service.forOrganization((String) null);
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
        service.forOrganization((ClientTenant) null);
        // Then
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_user_name_is_empty() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("come org").user("");
        // Then
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_user_name_is_null() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("come org").user((String)null);
        // Then
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_users_is_null() {
        // When
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("come org").user((ClientUser) null);
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

    /**
     * @deprecated Replaced by {@link UsersServiceTest#should_set_organization_id()}.
     */
    @Test
    public void should_set_org_id (){
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.organization("MyCoolOrg");

        assertSame(retrieved, service);
        assertEquals(Whitebox.getInternalState(service, "organizationId"), "MyCoolOrg");
    }

    /**
     * @deprecated Replaced by {@link UsersServiceTest#should_throw_exception_when_organization_name_is_empty()}.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_not_specified_ord_name (){
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.organization("");
    }

/**
 * @deprecated Replaced by {@link UsersServiceTest#should_return_proper_user_adapter_when_invoke_user_method_with_string()}.
 */
    @Test
    public void should_return_proper_user_adapter_when_invoke_username_method() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        PowerMockito.whenNew(SingleUserRequestAdapter.class).withArguments(sessionStorageMock, null, "Simon")
                .thenReturn(singleUserRequestAdapterMock);

        SingleUserRequestAdapter retrieved = service.username("Simon");
        assertSame(retrieved, singleUserRequestAdapterMock);
        //verifyNew(SingleUserRequestAdapter.class);
    }

    /**
     * @deprecated Replaced by {@link UsersServiceTest#should_throw_exception_when_user_name_is_empty()}.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_username_not_specified() {
        UsersService service = new UsersService(sessionStorageMock);
        service.username("");
    }

    /**
     * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersServiceTest#should_return_proper_user_adapter_when_invoke_user_method_with_string()}.
     */
    @Test
    public void should_return_proper_user_adapter_() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        PowerMockito.whenNew(SingleUserRequestAdapter.class).withArguments(eq(sessionStorageMock),
                anyString()).thenReturn(singleUserRequestAdapterMock);
        SingleUserRequestAdapter retrieved = service.user();
        assertSame(retrieved, singleUserRequestAdapterMock);
        //verifyNew(SingleUserRequestAdapter.class);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleUserRequestAdapterMock, batchUsersRequestAdapterMock);
    }
}