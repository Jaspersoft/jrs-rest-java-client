package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.authority.Organization;
import junit.framework.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
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
    private JerseyRequest<Organization> jerseyRequestMock;
    @Mock
    private OperationResult<Organization> operationResultMock;

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
        BatchOrganizationsAdapter retrieved = service.organizations();

        // Then
        assertSame(retrieved, batchOrganizationsAdapter);
    }

    @Test
    public void should_return_proper_instance_of_SingleOrganizationAdapter() throws Exception {

        // Given
        whenNew(SingleOrganizationAdapter.class).withArguments(sessionStorageMock, "orgId").thenReturn(singleOrganizationAdapter);
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        SingleOrganizationAdapter retrieved = service.organization("orgId");

        // Then
        assertSame(retrieved, singleOrganizationAdapter);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception() throws Exception {

        // Given
        OrganizationsService service = new OrganizationsService(sessionStorageMock);

        // When
        service.organization("");
    }


    @Test
    public void should_delete_organization() {

        /** Given **/
        PowerMockito.mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(
                eq(sessionStorageMock),
                eq(Organization.class),
                eq(new String[]{"/organizations", "MyOrg"}),
                any(DefaultErrorHandler.class))).thenReturn(jerseyRequestMock);
        Mockito.when(jerseyRequestMock.delete()).thenReturn(operationResultMock);


        /** When **/
        SingleOrganizationAdapter adapter = new SingleOrganizationAdapter(sessionStorageMock, "MyOrg");
        OperationResult retrieved = adapter.delete();



        /** Then **/
        Assert.assertNotNull(retrieved);
        Assert.assertSame(retrieved, operationResultMock);
        PowerMockito.verifyStatic(times(1));
        buildRequest(
                eq(sessionStorageMock),
                eq(Organization.class),
                eq(new String[]{"/organizations", "MyOrg"}),
                any(DefaultErrorHandler.class));
        Mockito.verify(jerseyRequestMock, times(1)).delete();
        Mockito.verifyNoMoreInteractions(jerseyRequestMock);

    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, batchOrganizationsAdapter,operationResultMock, jerseyRequestMock, singleOrganizationAdapter);
    }
}