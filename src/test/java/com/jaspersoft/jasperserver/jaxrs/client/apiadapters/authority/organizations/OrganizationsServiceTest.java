package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
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

@PrepareForTest({OrganizationsService.class})
public class OrganizationsServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private BatchOrganizationsAdapter batchOrganizationsAdapter;

    @Mock
    private SingleOrganizationAdapter singleOrganizationAdapter;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void organizations() throws Exception {

        // Given
        whenNew(BatchOrganizationsAdapter.class).withArguments(sessionStorageMock).thenReturn(batchOrganizationsAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        BatchOrganizationsAdapter retrieved = service.organizations();

        // Than
        assertSame(retrieved, batchOrganizationsAdapter);
    }

    @Test
    public void organization() throws Exception {

        // Given
        whenNew(SingleOrganizationAdapter.class).withArguments(sessionStorageMock, "orgId").thenReturn(singleOrganizationAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        SingleOrganizationAdapter retrieved = service.organization("orgId");

        // Than
        assertSame(retrieved, singleOrganizationAdapter);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void organization2() throws Exception {

        // Given
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        service.organization("");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, batchOrganizationsAdapter, singleOrganizationAdapter);
    }
}