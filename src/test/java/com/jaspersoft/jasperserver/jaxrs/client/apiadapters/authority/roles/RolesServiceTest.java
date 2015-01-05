package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link RolesService}
 */
@PrepareForTest({SingleRoleRequestAdapter.class, RolesService.class})
public class RolesServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private SingleRoleRequestAdapter singleRoleRequestAdapterMock;

    @Mock
    private BatchRolesRequestAdapter batchRolesRequestAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_storage_to_parent_class() {
        RolesService rolesService = new RolesService(sessionStorageMock);
        SessionStorage retrieved = rolesService.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test
    public void should_return_proper_SingleRoleRequestAdapter_instance() throws Exception {

        // Given
        PowerMockito.whenNew(SingleRoleRequestAdapter.class)
                .withArguments(sessionStorageMock, null, "Admin").thenReturn(singleRoleRequestAdapterMock);
        RolesService rolesService = new RolesService(sessionStorageMock);

        // When
        SingleRoleRequestAdapter retrieved = rolesService.roleName("Admin");

        // Then
        assertSame(retrieved, singleRoleRequestAdapterMock);
        PowerMockito.verifyNew(SingleRoleRequestAdapter.class, never()).withNoArguments();
        PowerMockito.verifyNew(SingleRoleRequestAdapter.class, times(1))
                .withArguments(sessionStorageMock, null, "Admin");
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_role_name_is_empty() throws Exception {

        // Given
        RolesService rolesService = new RolesService(sessionStorageMock);

        // When
        rolesService.roleName("");

        // Then throw an exception
    }

    @Test
    public void should_return_proper_instance_of_BatchRolesRequestAdapter() throws Exception {

        // Given
        PowerMockito.whenNew(BatchRolesRequestAdapter.class)
                .withArguments(sessionStorageMock, "MyCoolOrg").thenReturn(batchRolesRequestAdapterMock);
        RolesService rolesService = new RolesService(sessionStorageMock).organization("MyCoolOrg");

        // When
        BatchRolesRequestAdapter retrieved = rolesService.allRoles();

        // Then
        assertSame(retrieved, batchRolesRequestAdapterMock);
        PowerMockito.verifyNew(BatchRolesRequestAdapter.class, never()).withNoArguments();
        PowerMockito.verifyNew(BatchRolesRequestAdapter.class, times(1))
                .withArguments(sessionStorageMock, "MyCoolOrg");
    }

    @Test
    public void should_set_proper_field_value_to_instance() {

        // Given
        RolesService rolesService = new RolesService(sessionStorageMock);

        // When
        RolesService retrieved = rolesService.organization("MyCoolOrg");

        // Then
        assertSame(retrieved, rolesService);
        assertEquals(Whitebox.getInternalState(rolesService, "organizationId"), "MyCoolOrg");
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_arg_is_empty() {

        // Given
        RolesService rolesService = new RolesService(sessionStorageMock);

        // When
        rolesService.organization("");

        // Then throw an exception
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, singleRoleRequestAdapterMock, batchRolesRequestAdapterMock);
    }
}