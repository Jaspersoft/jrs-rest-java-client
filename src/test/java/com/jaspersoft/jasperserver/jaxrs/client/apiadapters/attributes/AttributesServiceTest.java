package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import jakarta.ws.rs.core.MultivaluedMap;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link AttributesService}
 */
@SuppressWarnings("unchecked")
public class AttributesServiceTest {

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
    public void should_pass_session_storage_to_parent_adapter() {
        AttributesService attributesService = new AttributesService(sessionStorageMock);
        SessionStorage retrieved = attributesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        new AttributesService(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization_as_string_is_null() {
        new AttributesService(sessionStorageMock).forOrganization((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization_as_object_is_null() {
        new AttributesService(sessionStorageMock).forOrganization((ClientTenant) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization__id_is_null() {
        new AttributesService(sessionStorageMock).forOrganization(new ClientTenant().setId(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization__id_is_empty() {
        new AttributesService(sessionStorageMock).forOrganization(new ClientTenant().setId(""));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_organization__name_is_empty() {
        new AttributesService(sessionStorageMock).forOrganization("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_as_string_is_null() {
        new AttributesService(sessionStorageMock).forUser((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_as_object_is_null() {
        new AttributesService(sessionStorageMock).forUser((ClientUser) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_name_is_null() {
        new AttributesService(sessionStorageMock).forUser(new ClientUser().setUsername(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user_name_is_empty() {
        new AttributesService(sessionStorageMock).forUser(new ClientUser().setUsername(""));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_user__name_is_empty() {
        new AttributesService(sessionStorageMock).forUser("");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance() throws Exception {
        AttributesService attributesService = new AttributesService(sessionStorageMock);

        SingleAttributeAdapter retrieved = attributesService.attribute("attrName");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 2);
        assertEquals(path.get(0), "attributes");
        assertEquals(path.get(1), "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_organization() throws Exception {
        SingleAttributeAdapter retrieved = new AttributesService(sessionStorageMock)
                .forOrganization("orgName")
                .attribute("attrName");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 4);
        assertEquals(path.get(0), "organizations");
        assertEquals(path.get(1), "orgName");
        assertEquals(path.get(2), "attributes");
        assertEquals(path.get(3), "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_organization_as_object() throws Exception {
        SingleAttributeAdapter retrieved = new AttributesService(sessionStorageMock)
                .forOrganization(new ClientTenant().setId("orgName"))
                .attribute("attrName");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 4);
        assertEquals(path.get(0), "organizations");
        assertEquals(path.get(1), "orgName");
        assertEquals(path.get(2), "attributes");
        assertEquals(path.get(3), "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_user() throws Exception {
        SingleAttributeAdapter retrieved = new AttributesService(sessionStorageMock)
                .forUser("userName")
                .attribute("attrName");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 4);
        assertEquals(path.get(0), "users");
        assertEquals(path.get(1), "userName");
        assertEquals(path.get(2), "attributes");
        assertEquals(path.get(3), "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_user_as_object() throws Exception {
        SingleAttributeAdapter retrieved = new AttributesService(sessionStorageMock)
                .forUser(new ClientUser().setUsername("userName"))
                .attribute("attrName");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 4);
        assertEquals(path.get(0), "users");
        assertEquals(path.get(1), "userName");
        assertEquals(path.get(2), "attributes");
        assertEquals(path.get(3), "attrName");
    }

    @Test
    public void should_return_proper_SingleAttributeAdapter_instance_for_organization_for_user() throws Exception {
        SingleAttributeAdapter retrieved = new AttributesService(sessionStorageMock)
                .forOrganization("orgName")
                .forUser("userName")
                .attribute("attrName");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 6);
        assertEquals(path.get(0), "organizations");
        assertEquals(path.get(1), "orgName");
        assertEquals(path.get(2), "users");
        assertEquals(path.get(3), "userName");
        assertEquals(path.get(4), "attributes");
        assertEquals(path.get(5), "attrName");
    }

    @Test
    public void should_construct_an_object_with_proper_params() throws Exception {
        AttributesService attributesService = new AttributesService(sessionStorageMock);

        SingleAttributeAdapter retrieved = attributesService.attribute("status");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ArrayList<String> path = (ArrayList<String>) getInternalState(retrieved, "path");
        assertEquals(path.size(), 2);
        assertEquals(path.get(0), "attributes");
        assertEquals(path.get(1), "status");
    }

    @Test
    public void should_return_proper_BatchAttributeAdapter_instance() throws Exception {
        BatchAttributeAdapter retrieved = new AttributesService(sessionStorageMock).allAttributes();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertNull(getInternalState(retrieved, "organizationId"));
        assertNull(getInternalState(retrieved, "userName"));
    }

    @Test
    public void should_instantiate_proper_BatchAttributeAdapter_instance() throws Exception {
        List<String> list = asList("attr1", "attr2", "attr3");

        BatchAttributeAdapter retrieved = new AttributesService(sessionStorageMock).attributes(list);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertNull(getInternalState(retrieved, "organizationId"));
        assertNull(getInternalState(retrieved, "userName"));
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) getInternalState(retrieved, "params");
        assertEquals(params.get("name"), list);
    }

    @Test
    public void should_instantiate_proper_BatchAttributeAdapter_instance_when_pass_vararg() throws Exception {
        BatchAttributeAdapter retrieved = new AttributesService(sessionStorageMock)
                .attributes("attr1", "attr2", "attr3");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchAttributeAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertNull(getInternalState(retrieved, "organizationId"));
        assertNull(getInternalState(retrieved, "userName"));
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) getInternalState(retrieved, "params");
        assertEquals(params.get("name"), asList("attr1", "attr2", "attr3"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exceptopn_when_attributes_list_is_empty() {
        new AttributesService(sessionStorageMock).attributes(Collections.EMPTY_LIST);
    }
}
