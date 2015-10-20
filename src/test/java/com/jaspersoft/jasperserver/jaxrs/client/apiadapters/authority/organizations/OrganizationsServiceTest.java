package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.dto.authority.ClientTenant;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.AssertJUnit.assertSame;

@PrepareForTest({OrganizationsService.class,JerseyRequest.class})
public class OrganizationsServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private BatchOrganizationsAdapter batchOrganizationsAdapter;
    @Mock
    private SingleOrganizationAdapter singleOrganizationAdapter;
    @Mock
    private JerseyRequest<ClientTenant> jerseyRequestMock;
    @Mock
    private OperationResult<ClientTenant> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_proper_instance_of_BatchOrganizationsAdapter() throws Exception {

        // Given
        whenNew(BatchOrganizationsAdapter.class).withArguments(sessionStorageMock).thenReturn(batchOrganizationsAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        BatchOrganizationsAdapter retrieved = service.allOrganizations();

        // Then
        assertSame(retrieved, batchOrganizationsAdapter);
    }

    @Test
    public void should_return_proper_instance_of_SingleOrganizationAdapter_by_organization_name() throws Exception {

        // Given
        ClientTenant clientTenant = new ClientTenant();
        clientTenant.setId("orgId");
        whenNew(SingleOrganizationAdapter.class).withArguments(sessionStorageMock, clientTenant).thenReturn(singleOrganizationAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        SingleOrganizationAdapter retrieved = service.organization("orgId");

        // Then
        assertSame(retrieved, singleOrganizationAdapter);
    }

    @Test
    public void should_return_proper_instance_of_SingleOrganizationAdapter_by_object() throws Exception {

        // Given
        ClientTenant clientTenant = new ClientTenant();
        clientTenant.setId("orgId");
        whenNew(SingleOrganizationAdapter.class).withArguments(sessionStorageMock, clientTenant).thenReturn(singleOrganizationAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        SingleOrganizationAdapter retrieved = service.organization(clientTenant);

        // Then
        assertSame(retrieved, singleOrganizationAdapter);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_is_null() throws Exception {

        // Given
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        service.organization((ClientTenant) null);
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_id_is_null() throws Exception {

        // Given
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        service.organization((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_organization_id_is_empty() throws Exception {

        // Given
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        service.organization("");
    }

    /**
     * Test should be deleted after deleting appropriate method
     * @deprecated Replaced by {@link OrganizationsServiceTest#should_return_proper_instance_of_BatchOrganizationsAdapter_deprecated}.
     */
    @Test
    public void should_return_proper_instance_of_BatchOrganizationsAdapter_deprecated() throws Exception {

        // Given
        whenNew(BatchOrganizationsAdapter.class).withArguments(sessionStorageMock).thenReturn(batchOrganizationsAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        BatchOrganizationsAdapter retrieved = service.organizations();

        // Then
        assertSame(retrieved, batchOrganizationsAdapter);
    }


    @AfterMethod
    public void after() {
        reset(sessionStorageMock, batchOrganizationsAdapter,operationResultMock, jerseyRequestMock, singleOrganizationAdapter);
    }
}