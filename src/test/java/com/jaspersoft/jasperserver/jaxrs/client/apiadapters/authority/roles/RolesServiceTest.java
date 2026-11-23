package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link RolesService}
 */
public class RolesServiceTest {

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
    public void should_pass_storage_to_parent_class() {
        RolesService rolesService = new RolesService(sessionStorageMock);
        SessionStorage retrieved = rolesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    public void should_return_proper_SingleRoleRequestAdapter_instance() throws Exception {
        RolesService rolesService = new RolesService(sessionStorageMock);

        SingleRoleRequestAdapter retrieved = rolesService.roleName("Admin");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleRoleRequestAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> roleUri = (ArrayList<String>) getInternalState(retrieved, "roleUri");
        assertEquals(roleUri.get(0), "roles");
        assertEquals(roleUri.get(1), "Admin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_role_name_is_empty() {
        RolesService rolesService = new RolesService(sessionStorageMock);
        rolesService.roleName("");
    }

    @Test
    public void should_return_proper_instance_of_BatchRolesRequestAdapter() throws Exception {
        RolesService rolesService = new RolesService(sessionStorageMock).organization("MyCoolOrg");

        BatchRolesRequestAdapter retrieved = rolesService.allRoles();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchRolesRequestAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> uri = (ArrayList<String>) getInternalState(retrieved, "uri");
        assertEquals(uri.get(0), "organizations");
        assertEquals(uri.get(1), "MyCoolOrg");
    }

    @Test
    public void should_set_proper_field_value_to_instance() throws Exception {
        RolesService rolesService = new RolesService(sessionStorageMock);

        RolesService retrieved = rolesService.organization("MyCoolOrg");

        assertSame(retrieved, rolesService);
        assertEquals(getInternalState(rolesService, "organizationId"), "MyCoolOrg");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_arg_is_empty() {
        RolesService rolesService = new RolesService(sessionStorageMock);
        rolesService.organization("");
    }
}
