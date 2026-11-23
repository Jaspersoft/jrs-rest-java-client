package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class OrganizationsServiceTest {

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
    public void should_return_proper_instance_of_BatchOrganizationsAdapter() {
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        BatchOrganizationsAdapter retrieved = service.allOrganizations();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchOrganizationsAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_instance_of_SingleOrganizationAdapter_by_organization_name() throws Exception {
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        SingleOrganizationAdapter retrieved = service.organization("orgId");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleOrganizationAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ClientTenant internalTenant = (ClientTenant) getInternalState(retrieved, "clientTenant");
        assertSame(internalTenant.getId(), "orgId");
    }

    @Test
    public void should_return_proper_instance_of_SingleOrganizationAdapter_by_object() throws Exception {
        ClientTenant clientTenant = new ClientTenant();
        clientTenant.setId("orgId");
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        SingleOrganizationAdapter retrieved = service.organization(clientTenant);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleOrganizationAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        ClientTenant internalTenant = (ClientTenant) getInternalState(retrieved, "clientTenant");
        assertSame(internalTenant, clientTenant);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_is_null() {
        OrganizationsService service = new OrganizationsService(sessionStorageMock);
        service.organization((ClientTenant) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_id_is_null() {
        OrganizationsService service = new OrganizationsService(sessionStorageMock);
        service.organization((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_id_is_empty() {
        OrganizationsService service = new OrganizationsService(sessionStorageMock);
        service.organization("");
    }
}
