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
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class ResourceBuilderFactoryTest {

    @Mock
    private SessionStorage sessionStorageMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_invoke_proper_constructor_and_return_DomainResourceBuilder_instance() throws Exception {

        /* Given */
        ClientSemanticLayerDataSource dataSource = new ClientSemanticLayerDataSource();

        try (MockedConstruction<SemanticLayerResourceBuilder> mockedBuilder =
                     Mockito.mockConstruction(SemanticLayerResourceBuilder.class)) {

            /* When */
            SemanticLayerResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(dataSource, sessionStorageMock);

            /* Then */
            assertNotNull(retrieved);
            assertSame(retrieved, mockedBuilder.constructed().get(0));
        }
    }

    @Test
    public void should_invoke_proper_constructor_and_return_ReportUnitResourceBuilder_instance() throws Exception {

        /* Given */
        ClientReportUnit reportUnit = new ClientReportUnit();

        try (MockedConstruction<ReportUnitResourceBuilder> mockedBuilder =
                     Mockito.mockConstruction(ReportUnitResourceBuilder.class)) {

            /* When */
            ReportUnitResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(reportUnit, sessionStorageMock);

            /* Then */
            assertNotNull(retrieved);
            assertSame(retrieved, mockedBuilder.constructed().get(0));
        }
    }


    @Test
    public void should_invoke_proper_constructor_and_return_SecureMondrianConnectionResourceBuilder_instance() throws Exception {

        /* Given */
        ClientSecureMondrianConnection connection = new ClientSecureMondrianConnection();

        try (MockedConstruction<SecureMondrianConnectionResourceBuilder> mockedBuilder =
                     Mockito.mockConstruction(SecureMondrianConnectionResourceBuilder.class)) {

            /* When */
            SecureMondrianConnectionResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(connection, sessionStorageMock);

            /* Then */
            assertNotNull(retrieved);
            assertSame(retrieved, mockedBuilder.constructed().get(0));
        }
    }


    @Test
    public void should_invoke_proper_constructor_and_return_MondrianConnectionResourceBuilder_instance() throws Exception {

        /* Given */
        ClientMondrianConnection mondrianConnection = new ClientMondrianConnection();

        try (MockedConstruction<MondrianConnectionResourceBuilder> mockedBuilder =
                     Mockito.mockConstruction(MondrianConnectionResourceBuilder.class)) {

            /* When */
            MondrianConnectionResourceBuilder retrieved = ResourceBuilderFactory.getBuilder(mondrianConnection, sessionStorageMock);

            /* Then */
            assertNotNull(retrieved);
            assertSame(retrieved, mockedBuilder.constructed().get(0));
        }
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock);
        mockitoSession.close();
    }
}
