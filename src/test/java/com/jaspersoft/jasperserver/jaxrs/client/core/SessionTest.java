package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.adhoc.queryexecution.QueryExecutionService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.AlertsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes.AttributesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.RolesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery.DataDiscoveryService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic.DiagnosticService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.DomainService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.inputControls.InputControlsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportingService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails.ThumbnailsService;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.AuthenticationType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.RequestedRepresentationNotAvailableForResourceException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.StatusType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    @Mock
    public Client clientMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_logout_in_spring_authentication_mode() {
        // Given
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(AuthenticationType.SPRING).when(configurationMock).getAuthenticationType();
        doReturn(targetMock).when(storageMock).getRootTarget();
        doReturn(targetMock).when(targetMock).path(anyString());
        doReturn(builderMock).when(targetMock).request();
        doReturn(responseMock).when(builderMock).get();
        doReturn(200).when(responseMock).getStatus();
        // When
        Session session = new Session(storageMock);
        session.logout();
        // Then
        verify(storageMock).getConfiguration();
        verify(storageMock).getRootTarget();
        verify(targetMock).path(anyString());
        verify(targetMock).request();
        verify(builderMock).get();
        verify(responseMock).getStatus();
    }

    @Test
    public void should_logout_in_basic_authentication_mode() {
        // Given
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(AuthenticationType.BASIC).when(configurationMock).getAuthenticationType();
        doReturn(credentialsMock).when(storageMock).getCredentials();
        // When
        Session session = new Session(storageMock);
        session.logout();
        // Then
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
        // Given
        doReturn(configurationMock).when(storageMock).getConfiguration();
        doReturn(targetMock).when(storageMock).getRootTarget();
        doReturn(targetMock).when(targetMock).path(anyString());
        doReturn(builderMock).when(targetMock).request();
        doReturn(responseMock).when(builderMock).get();
        doReturn(statusTypeMock).when(responseMock).getStatusInfo();
        doReturn("phrase_").when(statusTypeMock).getReasonPhrase();
        doReturn(406).when(responseMock).getStatus();
        // When
        Session session = new Session(storageMock);
        session.logout();
        // Then
        // exception should be thrown

    }

    @Test(expectedExceptions = RuntimeException.class)
    public void should_throw_exception_when_cannot_instantiate_service_class() {
        // Given
        class CustomAdapter extends AbstractAdapter {
            public CustomAdapter(SessionStorage sessionStorage) {
                super(sessionStorage);
            }
        }
        //When
        Session session = new Session(storageMock);
        session.getService(CustomAdapter.class);
        // Then
        // exception should be thrown
    }

    @Test
    public void should_return_proper_storage() {
        // When
        Session session = new Session(storageMock);
        // Then
        assertSame(session.getStorage(), storageMock);
    }

    @Test
    public void should_return_proper_client() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        when(sessionSpy.rawClient()).thenReturn(clientMock);
        // Then
        assertSame(sessionSpy.rawClient(), clientMock);
    }

    @Test
    public void should_return_proper_configured_client() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        when(sessionSpy.configuredClient()).thenReturn(targetMock);
        // Then
        assertSame(sessionSpy.configuredClient(), targetMock);
    }

    @Test
    public void should_return_not_null_OrganizationsService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        OrganizationsService retrieved = sessionSpy.organizationsService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(OrganizationsService.class);
    }

    @Test
    public void should_return_not_null_UsersService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        UsersService retrieved = sessionSpy.usersService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(UsersService.class);
    }

    @Test
    public void should_return_not_null_RolesService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        RolesService retrieved = sessionSpy.rolesService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(RolesService.class);
    }

    @Test
    public void should_return_not_null_PermissionsService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        PermissionsService retrieved = sessionSpy.permissionsService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(PermissionsService.class);
    }

    @Test
    public void should_return_not_null_ExportService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ExportService retrieved = sessionSpy.exportService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(ExportService.class);
    }

    @Test
    public void should_return_not_null_ImportService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ImportService retrieved = sessionSpy.importService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(ImportService.class);
    }

    @Test
    public void should_return_not_null_ReportingService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ReportingService retrieved = sessionSpy.reportingService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(ReportingService.class);
    }

    @Test
    public void should_return_not_null_ResourcesService() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ResourcesService retrieved = sessionSpy.resourcesService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(ResourcesService.class);
    }

    @Test
    public void should_return_not_null_JobsService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        JobsService retrieved = sessionSpy.jobsService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(JobsService.class);
    }

    @Test
    public void should_return_not_null_AlertsService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        AlertsService retrieved = sessionSpy.alertsService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(AlertsService.class);
    }
    @Test
    public void should_return_not_null_DomainMetadataService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        DomainService retrieved = sessionSpy.domainService();
        // Then
        assertNotNull(retrieved);
        verify(sessionSpy, times(1)).getService(DomainService.class);
    }

    @Test
    public void should_return_not_null_ThumbnailsService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        ThumbnailsService service = sessionSpy.thumbnailsService();
        // Then
        assertNotNull(service);
        verify(sessionSpy, times(1)).getService(ThumbnailsService.class);
    }

    @Test
    public void should_return_not_null_ServerAttributesService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        AttributesService service = sessionSpy.attributesService();
        // Then
        assertNotNull(service);
        verify(sessionSpy, times(1)).getService(AttributesService.class);
    }

    @Test
    public void should_return_not_null_InputControlsService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        InputControlsService service = sessionSpy.inputControlsService();
        // Then
        assertNotNull(service);
        verify(sessionSpy, times(1)).getService(InputControlsService.class);
    }


    @Test
    public void should_return_not_null_DiagnosticService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        DiagnosticService service = sessionSpy.diagnosticService();
        // Then
        assertNotNull(service);
        verify(sessionSpy, times(1)).getService(DiagnosticService.class);
    }

    @Test
    public void should_return_not_null_DataDiscoveryService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        DataDiscoveryService service = sessionSpy.dataDiscoveryService();
        // Then
        assertNotNull(service);
        verify(sessionSpy, times(1)).getService(DataDiscoveryService.class);
    }

    @Test
    public void should_return_not_null_QueryExecutionService_instance() {
        // When
        Session sessionSpy = Mockito.spy(new Session(storageMock));
        QueryExecutionService service = sessionSpy.queryExecutionService();
        // Then
        assertNotNull(service);
        verify(sessionSpy, times(1)).getService(QueryExecutionService.class);
    }

    @AfterMethod
    public void after() {
        reset(storageMock, configurationMock, credentialsMock, targetMock, builderMock, responseMock, statusTypeMock, clientMock);
    }
}
