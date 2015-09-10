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
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.RequestedRepresentationNotAvailableForResourceException;
import junit.framework.Assert;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;


/**
 * Unit tests for {@link Session}
 */
public class SessionTest {

    public SessionStorage storageMock;
    public RestClientConfiguration configurationMock;
    public AuthenticationCredentials credentialsMock;
    public WebTarget targetMock;
    public Builder builderMock;
    public Response responseMock;
    public StatusType statusTypeMock;

    @BeforeMethod
    public void before() {
        storageMock = Mockito.mock(SessionStorage.class);
        configurationMock = Mockito.mock(RestClientConfiguration.class);
        credentialsMock = Mockito.mock(AuthenticationCredentials.class);
        targetMock = Mockito.mock(WebTarget.class);
        builderMock = Mockito.mock(Builder.class);
        responseMock = Mockito.mock(Response.class);
        statusTypeMock = Mockito.mock(StatusType.class);
    }

    @Test
    public void should_logout_in_rest_authentication_mode() {

        Mockito.doReturn(configurationMock).when(storageMock).getConfiguration();
        Mockito.doReturn(targetMock).when(storageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(builderMock).get();
        Mockito.doReturn(200).when(responseMock).getStatus();

        Session session = new Session(storageMock);
        session.logout();

        Mockito.verify(storageMock).getConfiguration();
        Mockito.verify(storageMock).getRootTarget();
        Mockito.verify(targetMock).path(anyString());
        Mockito.verify(targetMock).request();
        Mockito.verify(builderMock).get();
        Mockito.verify(responseMock).getStatus();
    }

    @Test
    public void should_logout_in_spring_authentication_mode() {

        Mockito.doReturn(configurationMock).when(storageMock).getConfiguration();
        Mockito.doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
        Mockito.doReturn(targetMock).when(storageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(builderMock).get();
        Mockito.doReturn(200).when(responseMock).getStatus();

        Session session = new Session(storageMock);
        session.logout();

        Mockito.verify(storageMock).getConfiguration();
        Mockito.verify(storageMock).getRootTarget();
        Mockito.verify(targetMock).path(anyString());
        Mockito.verify(targetMock).request();
        Mockito.verify(builderMock).get();
        Mockito.verify(responseMock).getStatus();
    }

    @Test
    public void should_logout_in_basic_authentication_mode() {

        Mockito.doReturn(configurationMock).when(storageMock).getConfiguration();
        Mockito.doReturn(AuthenticationType.BASIC).when(configurationMock).getAuthenticationType();
        Mockito.doReturn(credentialsMock).when(storageMock).getCredentials();
        Session session = new Session(storageMock);
        session.logout();

        Mockito.verify(storageMock).getConfiguration();
        Mockito.verify(configurationMock).getAuthenticationType();
        Mockito.verify(storageMock, times(2)).getCredentials();
        Mockito.verify(credentialsMock).setPassword(null);
        Mockito.verify(credentialsMock).setUsername(null);

        Mockito.verify(storageMock, never()).getRootTarget();
        Mockito.verify(targetMock, never()).path(anyString());
        Mockito.verify(targetMock, never()).request();
        Mockito.verify(builderMock, never()).get();
        Mockito.verify(responseMock, never()).getStatus();
    }

    @Test(expectedExceptions = RequestedRepresentationNotAvailableForResourceException.class)
    public void should_throw_exception_when_response_status_is_greater_Then_400() {
        Mockito.doReturn(configurationMock).when(storageMock).getConfiguration();
        Mockito.doReturn(targetMock).when(storageMock).getRootTarget();
        Mockito.doReturn(targetMock).when(targetMock).path(anyString());
        Mockito.doReturn(builderMock).when(targetMock).request();
        Mockito.doReturn(responseMock).when(builderMock).get();
        Mockito.doReturn(statusTypeMock).when(responseMock).getStatusInfo();
        Mockito.doReturn("phrase_").when(statusTypeMock).getReasonPhrase();
        Mockito.doReturn(406).when(responseMock).getStatus();

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
    public void should_return_not_null_JobsService() {
        Session session = new Session(storageMock);
        JobsService retrieved = session.jobsService();
        assertNotNull(retrieved);
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
    public void should_return_not_null_ServerInfoService() {
        Session session = new Session(storageMock);
        ServerInfoService retrieved = session.serverInfoService();
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
        Mockito.verify(sessionSpy, times(1)).getService(ThumbnailsService.class);
    }

    @Test
    public void should_return_proper_ServerAttributesService_instance() {
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ServerAttributesService service = sessionSpy.serverAttributesService();
        Assert.assertNotNull(service);
        Mockito.verify(sessionSpy, times(1)).getService(ServerAttributesService.class);
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
