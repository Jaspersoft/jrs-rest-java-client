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
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails.ThumbnailsService;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.RequestedRepresentationNotAvailableForResourceException;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import junit.framework.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;


/**
 * Unit tests for {@link Session}
 */
public class SessionTest {
    @Mock
    public SessionStorage storageMock;
    @Mock
    public RestClientConfiguration configurationMock;
    @Mock
    public AuthenticationCredentials credentialsMock;
    @Mock
    public WebTarget targetMock;
    @Mock
    public Builder builderMock;
    @Mock
    public Response responseMock;
    @Mock
    public StatusType statusTypeMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_logout_in_spring_authentication_mode() {

        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
        doReturn(targetMock).when(storageMock).getRootTarget();
        doReturn(targetMock).when(targetMock).path(anyString());
        doReturn(builderMock).when(targetMock).request();
        doReturn(responseMock).when(builderMock).get();
        doReturn(200).when(responseMock).getStatus();

        Session session = new Session(storageMock);
        session.logout();

        verify(storageMock).getConfiguration();
        verify(storageMock).getRootTarget();
        verify(targetMock).path(anyString());
        verify(targetMock).request();
        verify(builderMock).get();
        verify(responseMock).getStatus();
    }

    @Test
    public void should_logout_in_basic_authentication_mode() {

        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(AuthenticationType.BASIC).when(configurationMock).getAuthenticationType();
        doReturn(credentialsMock).when(storageMock).getCredentials();
        Session session = new Session(storageMock);
        session.logout();

        verify(storageMock).getConfiguration();
        verify(configurationMock).getAuthenticationType();
        verify(storageMock, times(2)).getCredentials();
        verify(credentialsMock).setPassword(null);
        verify(credentialsMock).setUsername(null);

        verify(storageMock, never()).getRootTarget();
        verify(targetMock, never()).path(anyString());
        verify(targetMock, never()).request();
        verify(builderMock, never()).get();
        verify(responseMock, never()).getStatus();
    }

    @Test(expectedExceptions = RequestedRepresentationNotAvailableForResourceException.class)
    public void should_throw_exception_when_response_status_is_greater_then_400() {
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(targetMock).when(storageMock).getRootTarget();
        doReturn(targetMock).when(targetMock).path(anyString());
        doReturn(builderMock).when(targetMock).request();
        doReturn(responseMock).when(builderMock).get();
        doReturn(statusTypeMock).when(responseMock).getStatusInfo();
        doReturn("phrase_").when(statusTypeMock).getReasonPhrase();
        doReturn(406).when(responseMock).getStatus();

        Session session = new Session(storageMock);
        session.logout();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void should_throw_exception_when_cannot_instantiate_service_class() {
        Session session = new Session(storageMock);
        class CustomAdapter extends AbstractAdapter {
            public CustomAdapter(SessionStorage sessionStorage) {
                super(sessionStorage);
            }
        }
        session.getService(CustomAdapter.class);
    }

    @Test
    public void should_return_proper_storage() {
        Session session = new Session(storageMock);
        assertSame(session.getStorage(), storageMock);
    }

    @Test
    public void should_return_not_null_OrganizationsService() {
        Session session = new Session(storageMock);
        OrganizationsService retrieved = session.organizationsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_UsersService() {
        Session session = new Session(storageMock);
        UsersService retrieved = session.usersService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_RolesService() {
        Session session = new Session(storageMock);
        RolesService retrieved = session.rolesService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_PermissionsService() {
        Session session = new Session(storageMock);
        PermissionsService retrieved = session.permissionsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ExportService() {
        Session session = new Session(storageMock);
        ExportService retrieved = session.exportService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ImportService() {
        Session session = new Session(storageMock);
        ImportService retrieved = session.importService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ReportingService() {
        Session session = new Session(storageMock);
        ReportingService retrieved = session.reportingService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_ResourcesService() {
        Session session = new Session(storageMock);
        ResourcesService retrieved = session.resourcesService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_JobsService() {
        Session session = new Session(storageMock);
        JobsService retrieved = session.jobsService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_DomainMetadataService() {
        Session session = new Session(storageMock);
        DomainMetadataService retrieved = session.domainService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_not_null_QueryExecutorService() {
        Session session = new Session(storageMock);
        QueryExecutorService retrieved = session.queryExecutorService();
        assertNotNull(retrieved);
    }

    @Test
    public void should_return_proper_ThumbnailsService_instance() {
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ThumbnailsService service = sessionSpy.thumbnailsService();
        Assert.assertNotNull(service);
        verify(sessionSpy, times(1)).getService(ThumbnailsService.class);
    }

    @Test
    public void should_return_proper_ServerAttributesService_instance() {
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ServerAttributesService service = sessionSpy.serverAttributesService();
        Assert.assertNotNull(service);
        verify(sessionSpy, times(1)).getService(ServerAttributesService.class);
    }

    @AfterMethod
    public void after() {
        storageMock = null;
        targetMock = null;
        builderMock = null;
        responseMock = null;
        statusTypeMock = null;
    }
}
