//package com.jaspersoft.jasperserver.jaxrs.client.core;
//
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.organizations.OrganizationsService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.roles.RolesService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.DomainMetadataService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.importservice.ImportService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.JobsService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionsService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query.QueryExecutorService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportingService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourcesService;
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
//import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
//import com.jaspersoft.jasperserver.jaxrs.client.core.support.TestServiceClass;
//import org.glassfish.jersey.client.JerseyInvocation;
//import org.glassfish.jersey.client.JerseyWebTarget;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mock;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import javax.ws.rs.core.Response;
//
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.powermock.api.mockito.PowerMockito.doNothing;
//import static org.powermock.api.mockito.PowerMockito.spy;
//import static org.powermock.api.mockito.PowerMockito.whenNew;
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertTrue;
//
///**
// * Unit tests for {@link Session}.
// */
//@PrepareForTest(Session.class)
//public class SessionTest extends PowerMockTestCase {
//
//    @Mock
//    private SessionStorage expectedSessionStorage;
//
//    @Mock
//    private JerseyWebTarget webTargetMock;
//
//    @Mock
//    private Response responseMock;
//
//    @Mock
//    private JerseyInvocation.Builder builderMock;
//
//    @Mock
//    private DefaultErrorHandler errorHandlerMock;
//
//    @Captor
//    private ArgumentCaptor<String> arg;
//
//    private Session session;
//    private Session sessionSpy;
//
//    // Services mocks
//
//    @Mock
//    private OrganizationsService organizationsServiceMock;
//
//    @Mock
//    private ServerInfoService serverInfoServiceMock;
//
//    @Mock
//    private UsersService usersServiceMock;
//
//    @Mock
//    private RolesService rolesServiceMock;
//
//    @Mock
//    private PermissionsService permissionsServiceMock;
//
//    @Mock
//    private ExportService exportServiceMock;
//
//    @Mock
//    private ImportService importServiceMock;
//
//    @Mock
//    private ReportingService reportingServiceMock;
//
//    @Mock
//    private ResourcesService resourcesServiceMock;
//
//    @Mock
//    private JobsService jobsServiceMock;
//
//    @Mock
//    private DomainMetadataService domainServiceMock;
//
//    @Mock
//    private QueryExecutorService queryExecutorServiceMock;
//
//    @BeforeMethod
//    public void setUp() {
//        initMocks(this);
//        session = new Session(expectedSessionStorage);
//        sessionSpy = spy(new Session(expectedSessionStorage));
//    }
//
//    @Test(testName = "Session_constructor")
//    public void should_create_a_new_object_with_proper_field() {
//
//        // When
//        SessionStorage retrieved = session.getStorage();/*sessionSpy.getStorage();*/
//
//        // Than
//        assertNotNull(session);
//        assertEquals(retrieved, expectedSessionStorage);
//        //verify(sessionSpy, times(1)).getStorage();
//    }
//
//    @Test(testName = "logout")
//    public void should_invoke_a_bunch_of_builder_methods_with_proper_params() throws Exception {
//
//        // Given
//        final String PATH = "/exituser.html";
//
//        when(expectedSessionStorage.getRootTarget()).thenReturn(webTargetMock);
//        when(webTargetMock.path(arg.capture())).thenReturn(webTargetMock);
//        when(webTargetMock.request()).thenReturn(builderMock);
//        when(builderMock.get()).thenReturn(responseMock);
//        when(responseMock.getStatus()).thenReturn(404);
//        whenNew(DefaultErrorHandler.class).withNoArguments().thenReturn(errorHandlerMock);
//        doNothing().when(errorHandlerMock).handleError(responseMock);
//
//        // When
//        session.logout();
//
//        // Than
//        assertEquals(arg.getValue(), PATH);
//        verify(expectedSessionStorage).getRootTarget();
//        verify(webTargetMock).path(arg.getValue());
//        verify(webTargetMock).request();
//        verify(builderMock).get();
//        verify(responseMock).getStatus();
//        verify(errorHandlerMock).handleError(responseMock);
//    }
//
//    @Test(testName = "getService")
//    public void should_return_new_instance_of_AbstractAdapter_child() {
//        TestServiceClass service = session.getService(TestServiceClass.class);
//        assertNotNull(service);
//        assertTrue(service.isConstructorActivityFlag()); // checks if the proper constructor was invoked
//    }
//
//    @Test(testName = "organizationsService")
//    public void should_return_referenced_OrganizationsService_instance() {
//        when(sessionSpy.getService(OrganizationsService.class)).thenReturn(organizationsServiceMock);
//        OrganizationsService retrieved = sessionSpy.organizationsService();
//        assertEquals(retrieved, organizationsServiceMock);
//    }
//
//    @Test(testName = "serverInfoService")
//    public void should_return_referenced_ServerInfoService_instance() {
//        when(sessionSpy.getService(ServerInfoService.class)).thenReturn(serverInfoServiceMock);
//        ServerInfoService retrieved = sessionSpy.serverInfoService();
//        assertEquals(retrieved, serverInfoServiceMock);
//    }
//
//    @Test(testName = "usersService")
//    public void should_return_referenced_UsersService_instance() {
//        when(sessionSpy.getService(UsersService.class)).thenReturn(usersServiceMock);
//        UsersService retrieved = sessionSpy.usersService();
//        assertEquals(retrieved, usersServiceMock);
//    }
//
//    @Test(testName = "rolesService")
//    public void should_return_referenced_RolesService_instance() {
//        when(sessionSpy.getService(RolesService.class)).thenReturn(rolesServiceMock);
//        RolesService retrieved = sessionSpy.rolesService();
//        assertEquals(retrieved, rolesServiceMock);
//    }
//
//    @Test(testName = "permissionsService")
//    public void should_return_referenced_PermissionsService_instance() {
//        when(sessionSpy.getService(PermissionsService.class)).thenReturn(permissionsServiceMock);
//        PermissionsService retrieved = sessionSpy.permissionsService();
//        assertEquals(retrieved, permissionsServiceMock);
//    }
//
//    @Test(testName = "exportService")
//    public void should_return_referenced_ExportService_instance() {
//        when(sessionSpy.getService(ExportService.class)).thenReturn(exportServiceMock);
//        ExportService retrieved = sessionSpy.exportService();
//        assertEquals(retrieved, exportServiceMock);
//    }
//
//    @Test(testName = "importService")
//    public void should_return_referenced_ImportService_instance() {
//        when(sessionSpy.getService(ImportService.class)).thenReturn(importServiceMock);
//        ImportService retrieved = sessionSpy.importService();
//        assertEquals(retrieved, importServiceMock);
//    }
//
//    @Test(testName = "reportingService")
//    public void should_return_referenced_ReportingService_instance() {
//        when(sessionSpy.getService(ReportingService.class)).thenReturn(reportingServiceMock);
//        ReportingService retrieved = sessionSpy.reportingService();
//        assertEquals(retrieved, reportingServiceMock);
//    }
//
//    @Test(testName = "resourcesService")
//    public void should_return_referenced_ResourcesService_instance() {
//        when(sessionSpy.getService(ResourcesService.class)).thenReturn(resourcesServiceMock);
//        ResourcesService retrieved = sessionSpy.resourcesService();
//        assertEquals(retrieved, resourcesServiceMock);
//    }
//
//    @Test(testName = "jobsService")
//    public void should_return_referenced_JobsService_instance() {
//        when(sessionSpy.getService(JobsService.class)).thenReturn(jobsServiceMock);
//        JobsService retrieved = sessionSpy.jobsService();
//        assertEquals(retrieved, jobsServiceMock);
//    }
//
//    @Test(testName = "domainService")
//    public void should_return_referenced_DomainMetadataService_instance() {
//        when(sessionSpy.getService(DomainMetadataService.class)).thenReturn(domainServiceMock);
//        DomainMetadataService retrieved = sessionSpy.domainService();
//        assertEquals(retrieved, domainServiceMock);
//    }
//
//    @Test(testName = "queryExecutorService")
//    public void should_return_referenced_QueryExecutorService_instance() {
//        when(sessionSpy.getService(QueryExecutorService.class)).thenReturn(queryExecutorServiceMock);
//        QueryExecutorService retrieved = sessionSpy.queryExecutorService();
//        assertEquals(retrieved, queryExecutorServiceMock);
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        reset(expectedSessionStorage, webTargetMock, responseMock, builderMock, errorHandlerMock,
//                organizationsServiceMock, serverInfoServiceMock, usersServiceMock,
//                rolesServiceMock, permissionsServiceMock, exportServiceMock,
//                importServiceMock, reportingServiceMock, resourcesServiceMock,
//                jobsServiceMock, domainServiceMock, queryExecutorServiceMock,
//                sessionSpy
//        );
//    }
//}