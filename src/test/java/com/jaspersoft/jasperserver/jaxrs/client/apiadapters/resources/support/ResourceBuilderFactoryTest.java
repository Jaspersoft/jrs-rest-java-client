package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support;

import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientReportUnit;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.ResourceBuilderFactory;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SemanticLayerResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.MondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.ReportUnitResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertSame;

@PrepareForTest({ResourceBuilderFactory.class})
public class ResourceBuilderFactoryTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private SemanticLayerResourceBuilder domainBuilderMock;

    @Mock
    private MondrianConnectionResourceBuilder mondrianConnectionBuilderMock;

    @Mock
    private SecureMondrianConnectionResourceBuilder secureMondrianConnectionBuilderMock;

    @Mock
    private ReportUnitResourceBuilder reportUnitBuilderMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_invoke_proper_constructor_and_return_DomainResourceBuilder_instance() throws Exception {

        /* Given */
        ClientSemanticLayerDataSource dataSource = new ClientSemanticLayerDataSource();
        whenNew(SemanticLayerResourceBuilder.class)
                .withParameterTypes(ClientSemanticLayerDataSource.class, SessionStorage.class)
                .withArguments(dataSource, sessionStorageMock).thenReturn(domainBuilderMock);

        /* When */
        SemanticLayerResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(dataSource, sessionStorageMock);

        /* Then */
        assertSame(retrieved, domainBuilderMock);
        verifyNew(SemanticLayerResourceBuilder.class, times(1))
                .withArguments(dataSource, sessionStorageMock);
    }

    @Test
    public void should_invoke_proper_constructor_and_return_ReportUnitResourceBuilder_instance() throws Exception {

        /* Given */
        ClientReportUnit reportUnit = new ClientReportUnit();
        whenNew(ReportUnitResourceBuilder.class)
                .withParameterTypes(ClientReportUnit.class, SessionStorage.class)
                .withArguments(reportUnit, sessionStorageMock).thenReturn(reportUnitBuilderMock);

        /* When */
        ReportUnitResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(reportUnit, sessionStorageMock);

        /* Then */
        assertSame(retrieved, reportUnitBuilderMock);
        verifyNew(ReportUnitResourceBuilder.class, times(1))
                .withArguments(reportUnit, sessionStorageMock);
    }


    @Test
    public void should_invoke_proper_constructor_and_return_SecureMondrianConnectionResourceBuilder_instance() throws Exception {

        /* Given */
        ClientSecureMondrianConnection connection = new ClientSecureMondrianConnection();
        whenNew(SecureMondrianConnectionResourceBuilder.class)
                .withParameterTypes(ClientSecureMondrianConnection.class, SessionStorage.class)
                .withArguments(connection, sessionStorageMock).thenReturn(secureMondrianConnectionBuilderMock);

        /* When */
        SecureMondrianConnectionResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(connection, sessionStorageMock);

        /* Then */
        assertSame(retrieved, secureMondrianConnectionBuilderMock);
        verifyNew(SecureMondrianConnectionResourceBuilder.class, times(1))
                .withArguments(connection, sessionStorageMock);
    }


    @Test
    public void should_invoke_proper_constructor_and_return_MondrianConnectionResourceBuilder_instance() throws Exception {

        /* Given */
        ClientMondrianConnection mondrianConnection = new ClientMondrianConnection();
        whenNew(MondrianConnectionResourceBuilder.class)
                .withParameterTypes(ClientMondrianConnection.class, SessionStorage.class)
                .withArguments(mondrianConnection, sessionStorageMock).thenReturn(mondrianConnectionBuilderMock);

        /* When */
        MondrianConnectionResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(mondrianConnection, sessionStorageMock);

        /* Then */
        assertSame(retrieved, mondrianConnectionBuilderMock);
        verifyNew(MondrianConnectionResourceBuilder.class, times(1))
                .withArguments(mondrianConnection, sessionStorageMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, domainBuilderMock, mondrianConnectionBuilderMock,
                secureMondrianConnectionBuilderMock, reportUnitBuilderMock);
    }
}