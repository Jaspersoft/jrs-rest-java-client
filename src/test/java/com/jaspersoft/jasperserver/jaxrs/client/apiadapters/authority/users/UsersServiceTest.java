package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link UsersService}
 */
public class UsersServiceTest {

    private static final String ORGANIZATION_ID = "MyCoolOrg";

    @Mock
    private SessionStorage sessionStorageMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock);
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
    public void should_set_organization_id() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.forOrganization(ORGANIZATION_ID);

        assertSame(retrieved, service);
        assertEquals(getInternalState(service, "organizationId"), ORGANIZATION_ID);
    }

    @Test
    public void should_set_organization_id_as_object() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        UsersService retrieved = service.forOrganization(new ClientTenant().setId(ORGANIZATION_ID));

        assertSame(retrieved, service);
        assertEquals(getInternalState(service, "organizationId"), ORGANIZATION_ID);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_name_is_empty() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_name_is_null() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_users_organization_name_is_empty() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization(new ClientTenant().setId(""));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_is_null() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization((ClientTenant) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_user_name_is_empty() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("come org").user("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_user_name_is_null() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("come org").user((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_users_is_null() {
        UsersService service = new UsersService(sessionStorageMock);
        service.forOrganization("come org").user((ClientUser) null);
    }

    @Test
    public void should_return_proper_user_adapter_when_invoke_user_method_with_string() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);

        SingleUserRequestAdapter retrieved = service.user("Simon");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleUserRequestAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ClientUser internalUser = (ClientUser) getInternalState(retrieved, "user");
        assertEquals(internalUser.getUsername(), "Simon");
    }

    @Test
    public void should_return_proper_user_adapter_when_invoke_user_method_with_object() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        ClientUser clientUser = new ClientUser().setUsername("Simon");

        SingleUserRequestAdapter retrieved = service.user(clientUser);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleUserRequestAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ClientUser internalUser = (ClientUser) getInternalState(retrieved, "user");
        assertSame(internalUser, clientUser);
    }

    @Test
    public void should_return_proper_user_adapter_with_organization_id_source_obj_not_modified() throws Exception {
        UsersService service = new UsersService(sessionStorageMock);
        ClientUser clientUser = new ClientUser().setUsername("Simon");

        SingleUserRequestAdapter retrieved = service.forOrganization(ORGANIZATION_ID).user(clientUser);

        assertNotNull(retrieved);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertNull(clientUser.getTenantId());
        ClientUser internalUser = (ClientUser) getInternalState(retrieved, "user");
        assertEquals(internalUser.getTenantId(), ORGANIZATION_ID);
    }

    @Test
    public void should_return_BatchUsersRequestAdapter() {
        UsersService service = new UsersService(sessionStorageMock);

        BatchUsersRequestAdapter retrieved = service.forOrganization(ORGANIZATION_ID).allUsers();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchUsersRequestAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
    }
}
