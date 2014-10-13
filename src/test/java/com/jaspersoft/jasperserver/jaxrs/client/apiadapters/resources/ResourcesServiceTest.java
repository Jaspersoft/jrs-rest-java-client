package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceBuilderFactory;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder.DomainResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder.MondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder.ReportUnitResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link ResourcesService}
 */
@PrepareForTest({ResourcesService.class, SingleResourceAdapter.class, ResourceBuilderFactory.class})
public class ResourcesServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private SingleResourceAdapter resourceAdapterMock;
    @Mock
    private DomainResourceBuilder domainResourceBuilderMock;
    @Mock
    private ClientSemanticLayerDataSource clientSemanticLayerDataSourceMock;
    @Mock
    private ClientReportUnit reportUnitMock;
    @Mock
    private ReportUnitResourceBuilder reportUnitResourceBuilderMock;
    @Mock
    private ClientMondrianConnection mondrianConnectionMock;
    @Mock
    private MondrianConnectionResourceBuilder mondrianConnectionResourceBuilderMock;
    @Mock
    private SecureMondrianConnectionResourceBuilder secureMondrianConnectionResourceBuilderMock;
    @Mock
    private ClientSecureMondrianConnection secureMondrianConnectionMock;


    private ResourcesService service;

    @BeforeMethod
    public void before() {
        initMocks(this);
        service = new ResourcesService(sessionStorageMock);
    }

    @Test
    public void should_return_proper_instance_of_BatchResourcesAdapter_class() {
        Object retrieved = service.resources();

        assertNotNull(retrieved);
        assertTrue(instanceOf(BatchResourcesAdapter.class).matches(retrieved));
    }

    @Test
    public void should_return_proper_instance_of_SingleResourceAdapter_class() {
        Object retrieved = service.resource("uri");

        assertNotNull(retrieved);
        assertTrue(instanceOf(SingleResourceAdapter.class).matches(retrieved));
    }

    @Test
    public void should_check_signature_of_SingleResourceAdapter_constructor() throws Exception {

        PowerMockito.whenNew(SingleResourceAdapter.class).withArguments(sessionStorageMock, "uri").thenReturn(resourceAdapterMock);
        Object retrieved = service.resource("uri");

        assertNotNull(retrieved);
        assertTrue(instanceOf(SingleResourceAdapter.class).matches(retrieved));
        assertSame(retrieved, resourceAdapterMock);
    }

    @Test
    public void should_return_DomainResourceBuilder_with_resource_copy() throws Exception {

        /** Given **/
        mockStatic(ResourceBuilderFactory.class);
        when(ResourceBuilderFactory.getBuilder(
                any(ClientSemanticLayerDataSource.class),
                any(SessionStorage.class)))
                .thenReturn(domainResourceBuilderMock);

        whenNew(ClientSemanticLayerDataSource.class)
                .withArguments(any(ClientSemanticLayerDataSource.class))
                .thenReturn(clientSemanticLayerDataSourceMock);

        ClientSemanticLayerDataSource dataSource = new ClientSemanticLayerDataSource();


        /** When **/
        DomainResourceBuilder retrieved = service.resource(dataSource);


        /** Than **/
        assertNotNull(retrieved);
        verifyNew(ClientSemanticLayerDataSource.class, times(1)).withArguments(dataSource);
        verifyStatic(times(1));
        ResourceBuilderFactory.getBuilder(any(ClientSemanticLayerDataSource.class), any(SessionStorage.class));
    }

    @Test
    public void should_return_ReportUnitResourceBuilder_with_resource_copy() throws Exception {

        /** Given **/
        mockStatic(ResourceBuilderFactory.class);
        when(ResourceBuilderFactory.getBuilder(
                any(ClientReportUnit.class),
                any(SessionStorage.class)))
                .thenReturn(reportUnitResourceBuilderMock);

        whenNew(ClientReportUnit.class)
                .withArguments(any(ClientReportUnit.class))
                .thenReturn(reportUnitMock);

        ClientReportUnit reportUnit = new ClientReportUnit();


        /** When **/
        ReportUnitResourceBuilder retrieved = service.resource(reportUnit);


        /** Than **/
        assertNotNull(retrieved);
        verifyNew(ClientReportUnit.class, times(1)).withArguments(reportUnit);
        verifyStatic(times(1));
        ResourceBuilderFactory.getBuilder(any(ClientReportUnit.class), any(SessionStorage.class));
    }

    @Test
    public void should_return_MondrianConnectionResourceBuilder_with_resource_copy() throws Exception {

        /** Given **/
        mockStatic(ResourceBuilderFactory.class);
        when(ResourceBuilderFactory.getBuilder(
                any(ClientMondrianConnection.class),
                any(SessionStorage.class)))
                .thenReturn(mondrianConnectionResourceBuilderMock);

        whenNew(ClientMondrianConnection.class)
                .withArguments(any(ClientMondrianConnection.class))
                .thenReturn(mondrianConnectionMock);

        ClientMondrianConnection mondrianConnection = new ClientMondrianConnection();


        /** When **/
        MondrianConnectionResourceBuilder retrieved = service.resource(mondrianConnection);


        /** Than **/
        assertNotNull(retrieved);
        verifyNew(ClientMondrianConnection.class, times(1)).withArguments(mondrianConnection);
        verifyStatic(times(1));
        ResourceBuilderFactory.getBuilder(any(ClientMondrianConnection.class), any(SessionStorage.class));
    }

    @Test
    public void should_return_SecureMondrianConnectionResourceBuilder_with_resource_copy() throws Exception {

        /** Given **/
        mockStatic(ResourceBuilderFactory.class);
        when(ResourceBuilderFactory.getBuilder(
                any(ClientSecureMondrianConnection.class),
                any(SessionStorage.class)))
                .thenReturn(secureMondrianConnectionResourceBuilderMock);

        whenNew(ClientSecureMondrianConnection.class)
                .withArguments(any(ClientMondrianConnection.class))
                .thenReturn(secureMondrianConnectionMock);

        ClientSecureMondrianConnection mondrianConnection = new ClientSecureMondrianConnection();


        /** When **/
        SecureMondrianConnectionResourceBuilder retrieved = service.resource(mondrianConnection);


        /** Than **/
        assertNotNull(retrieved);
        verifyNew(ClientSecureMondrianConnection.class, times(1)).withArguments(mondrianConnection);
        verifyStatic(times(1));
        ResourceBuilderFactory.getBuilder(any(ClientSecureMondrianConnection.class), any(SessionStorage.class));
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, resourceAdapterMock, domainResourceBuilderMock,
                clientSemanticLayerDataSourceMock, reportUnitMock, reportUnitResourceBuilderMock,
                mondrianConnectionMock, mondrianConnectionResourceBuilderMock,
                secureMondrianConnectionResourceBuilderMock, secureMondrianConnectionMock);
        service = null;
    }
}