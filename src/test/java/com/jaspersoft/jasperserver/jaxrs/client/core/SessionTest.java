package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.ServerAttributesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.DomainMetadataService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query.QueryExecutorService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportingService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails.ThumbnailsService;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.RequestedRepresentationNotAvailableForResourceException;
import junit.framework.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;


/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.core.Session}
 */
public class SessionTest {

    @Mock
    public SessionStorage sessionStorageMock;

    @Mock
    public WebTarget targetMock;

    @Mock
    public Invocation.Builder builderMock;

    @Mock
    public Response responseMock;

    @Mock
    public Response.StatusType statusTypeMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_logout() {
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(builderMock).get();
        Mockito.doReturn(200).when(responseMock).getStatus();

        Session session = new Session(sessionStorageMock);
        session.logout();

        Mockito.verify(sessionStorageMock).getRootTarget();
        Mockito.verify(targetMock).path(anyString());
        Mockito.verify(targetMock).request();
        Mockito.verify(builderMock).get();
        Mockito.verify(responseMock).getStatus();
    }

    @Test(expectedExceptions = RequestedRepresentationNotAvailableForResourceException.class)
    public void should_throw_exception_when_response_status_is_greater_than_400() {
        Mockito.doReturn(targetMock).when(sessionStorageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(builderMock).get();
        Mockito.doReturn(statusTypeMock).when(responseMock).getStatusInfo();
        Mockito.doReturn("phrase_").when(statusTypeMock).getReasonPhrase();
        Mockito.doReturn(406).when(responseMock).getStatus();

        Session session = new Session(sessionStorageMock);
        session.logout();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void should_throw_exception_when_cannot_instantiate_service_class() {
        Session session = new Session(sessionStorageMock);
        class CustomAdapter extends AbstractAdapter {
            public CustomAdapter(SessionStorage sessionStorage) {
                super(sessionStorage);
            }
        }
        session.getService(CustomAdapter.class);
    }

    @Test
    public void should_return_not_null_JobsService() {
        Session session = new Session(sessionStorageMock);
        JobsService retrieved = session.jobsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_proper_storage() {
        Session session = new Session(sessionStorageMock);
        assertSame(session.getStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_not_null_OrganizationsService() {
        Session session = new Session(sessionStorageMock);
        OrganizationsService retrieved = session.organizationsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ServerInfoService() {
        Session session = new Session(sessionStorageMock);
        ServerInfoService retrieved = session.serverInfoService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_UsersService() {
        Session session = new Session(sessionStorageMock);
        UsersService retrieved = session.usersService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_RolesService() {
        Session session = new Session(sessionStorageMock);
        RolesService retrieved = session.rolesService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_PermissionsService() {
        Session session = new Session(sessionStorageMock);
        PermissionsService retrieved = session.permissionsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ExportService() {
        Session session = new Session(sessionStorageMock);
        ExportService retrieved = session.exportService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ImportService() {
        Session session = new Session(sessionStorageMock);
        ImportService retrieved = session.importService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ReportingService() {
        Session session = new Session(sessionStorageMock);
        ReportingService retrieved = session.reportingService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ResourcesService() {
        Session session = new Session(sessionStorageMock);
        ResourcesService retrieved = session.resourcesService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_DomainMetadataService() {
        Session session = new Session(sessionStorageMock);
        DomainMetadataService retrieved = session.domainService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_QueryExecutorService() {
        Session session = new Session(sessionStorageMock);
        QueryExecutorService retrieved = session.queryExecutorService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_proper_ThumbnailsService_instance() {
        Session sessionSpy = Mockito.spy(new Session(sessionStorageMock));
        ThumbnailsService service = sessionSpy.thumbnailsService();
        Assert.assertNotNull(service);
        Mockito.verify(sessionSpy, times(1)).getService(ThumbnailsService.class);
    }

    @Test
    public void should_return_proper_ServerAttributesService_instance() {
        Session sessionSpy = Mockito.spy(new Session(sessionStorageMock));
        ServerAttributesService service = sessionSpy.serverAttributesService();
        Assert.assertNotNull(service);
        Mockito.verify(sessionSpy, times(1)).getService(ServerAttributesService.class);
    }


    @AfterMethod
    public void after() {
        reset(sessionStorageMock, targetMock, builderMock);
    }
}
